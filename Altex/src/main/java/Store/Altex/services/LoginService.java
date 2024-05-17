package Store.Altex.services;

import Store.Altex.models.User;
import Store.Altex.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<Long> login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent() && bCryptPasswordEncoder.matches(password, optionalUser.get().getPassword())) {
            return Optional.of(optionalUser.get().getId());
        } else {
            return Optional.empty();
        }
    }



    public void updateLastLogged(Long userId, LocalDateTime lastLogged) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        user.setLastLogged(lastLogged);
        user.setCounter(user.getCounter()+1);
        userRepository.save(user);
    }
    public List<User> getUserByLastLogged(LocalDateTime lastLogged) {
        return userRepository.findByLastLogged(lastLogged);
    }
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }
    public String getUserRoleByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return  user.getAppUserRole().name();
    }
    public Long getIdByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return  user.getId();
    }
    public Boolean getEnabledByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return  user.getEnabled();
    }
}