package Store.Altex.services;

import Store.Altex.enums.UserRole;
import Store.Altex.models.User;
import Store.Altex.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void loginSuccessful() {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setId(1L);
        user.setPassword("encryptedPassword");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(password, "encryptedPassword")).thenReturn(true);
        Optional<Long> result = loginService.login(email, password);
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(1L);
    }

    @Test
    void loginFailureIncorrectPassword() {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setPassword("encryptedPassword");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(password, "encryptedPassword")).thenReturn(false)
        Optional<Long> result = loginService.login(email, password);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    void loginFailureUserNotFound() {
        String email = "nonexistent@example.com";
        String password = "password";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        Optional<Long> result = loginService.login(email, password);
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    void getUserByIdShouldReturnUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        User result = loginService.getUserById(userId);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void getUserByIdShouldThrowIfNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> loginService.getUserById(userId))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found with id: " + userId);
    }
    @Test
    void getUserRoleByEmailShouldReturnRole() {

        String email = "user@example.com";
        User user = new User();
        user.setAppUserRole(UserRole.CUSTOMER);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        String role = loginService.getUserRoleByEmail(email);
        assertThat(role).isEqualTo("CUSTOMER");
    }

    @Test
    void getIdByEmailShouldReturnId() {
        String email = "user@example.com";
        User user = new User();
        user.setId(1L);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Long id = loginService.getIdByEmail(email);
        assertThat(id).isEqualTo(1L);
    }

    @Test
    void getEnabledByEmailShouldReturnEnabledStatus() {
        String email = "user@example.com";
        User user = new User();
        user.setEnabled(true);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Boolean enabled = loginService.getEnabledByEmail(email);

        assertThat(enabled).isTrue();
    }

}
