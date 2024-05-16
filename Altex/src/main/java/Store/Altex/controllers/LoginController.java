package Store.Altex.controllers;

import Store.Altex.models.User;
import Store.Altex.repositories.UserRepository;
import Store.Altex.requests.LoginRequest;
import Store.Altex.services.LoginService;
import Store.Altex.services.*;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController

@CrossOrigin(origins = {"http://localhost:3000/","http://127.0.0.1:1080/"})
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {
    private final UserRepository userRepository;

    private final LoginService loginService;
    private final XmlService xmlService;

//    @GetMapping(value = "/{id}/xml", produces = "application/xml")
//    public String getUserXml(@PathVariable Long id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//        return xmlService.userToXml(user);
//    }

    @GetMapping(value = "/{id}/xml")
    public ResponseEntity<String> getUserXml(@PathVariable Long id, HttpServletResponse response) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        String xmlContent = xmlService.userToXml(user);

        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename("user_" + id + ".xml")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/xml");

        return ResponseEntity.ok()
                .headers(headers)
                .body(xmlContent);
    }
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return loginService.login(request.getEmail(), request.getPassword())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).body(Long.valueOf("Invalid email or password")));
    }
    @GetMapping("/role")
    public ResponseEntity<String> getUserRoleByEmail(@RequestParam String email) {
        try {
            String role = loginService.getUserRoleByEmail(email);
            return ResponseEntity.ok(role);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/id")
    public Long getIdByEmail(@RequestParam String email) {

            return loginService.getIdByEmail(email);
    }
    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable Long id) {
        User user = loginService.getUserById(id);

        return user.getFirstName();
    }
    @GetMapping("/by-date")
    public List<User> getUsersByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return userRepository.findByLastLoggedDate(date);
    }
    @GetMapping("/last-logged")
    public ResponseEntity<List<User>> getUsersByLastLogged() {
        // Get users based on the LocalDateTime of their last login
        LocalDateTime lastLogged = LocalDateTime.now().minusMonths(1); // Example: Users logged in within the last month
        List<User> users = loginService.getUserByLastLogged(lastLogged);

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }



    @GetMapping("/by-last-logged-date")
    public List<User> getUsersByLastLoggedDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return userRepository.findByLastLoggedDate(date);
    }
    @GetMapping("/update-last-logged/{userId}")
    public ResponseEntity<String> updateLastLogged(@PathVariable Long userId) {
        LocalDateTime now = LocalDateTime.now();
        loginService.updateLastLogged(userId, now); // Call the service method with the user's ID
        return ResponseEntity.ok("Last logged updated successfully.");
    }

    @GetMapping("/enable")
    public ResponseEntity<Boolean> getEnabledByEmail(@RequestParam String email) {
        try {
            Boolean enabled = loginService.getEnabledByEmail(email);
            return ResponseEntity.ok(enabled);
        } catch (UsernameNotFoundException e) {
            return (ResponseEntity<Boolean>) ResponseEntity.notFound();
        }
    }
    @GetMapping()
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    @GetMapping("/{id}")
//    public Optional<User> gets(@PathVariable Long id) {
//        return userRepository.findById(id);
//    }
}
