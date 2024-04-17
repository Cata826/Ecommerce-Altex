package Store.Altex.controllers;

import Store.Altex.models.User;
import Store.Altex.requests.LoginRequest;
import Store.Altex.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController

@CrossOrigin(origins = {"http://localhost:3000/","http://127.0.0.1:1080/"})
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {


    private final LoginService loginService;
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

//    @GetMapping("/update-last-logged")
//    public ResponseEntity<String> updateLastLogged() {
//        LocalDateTime now = LocalDateTime.now();
//        loginService.updateLastLogged(now); // Call the service method to update lastLogged
//        return ResponseEntity.ok("Last logged updated successfully.");
//    }
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
}
