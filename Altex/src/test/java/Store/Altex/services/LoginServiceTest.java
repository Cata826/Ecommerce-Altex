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
        // Given
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setId(1L);
        user.setPassword("encryptedPassword");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(password, "encryptedPassword")).thenReturn(true);

        // When
        Optional<Long> result = loginService.login(email, password);

        // Then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(1L);
    }

    @Test
    void loginFailureIncorrectPassword() {
        // Given
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setPassword("encryptedPassword");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(password, "encryptedPassword")).thenReturn(false);

        // When
        Optional<Long> result = loginService.login(email, password);

        // Then
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    void loginFailureUserNotFound() {
        // Given
        String email = "nonexistent@example.com";
        String password = "password";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When
        Optional<Long> result = loginService.login(email, password);

        // Then
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    void getUserByIdShouldReturnUser() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        User result = loginService.getUserById(userId);

        // Then
        assertThat(result).isEqualTo(user);
    }

    @Test
    void getUserByIdShouldThrowIfNotFound() {
        // Given
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> loginService.getUserById(userId))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found with id: " + userId);
    }
    @Test
    void getUserRoleByEmailShouldReturnRole() {
        // Given
        String email = "user@example.com";
        User user = new User();
        user.setAppUserRole(UserRole.CUSTOMER);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // When
        String role = loginService.getUserRoleByEmail(email);

        // Then
        assertThat(role).isEqualTo("CUSTOMER");
    }

    @Test
    void getIdByEmailShouldReturnId() {
        // Given
        String email = "user@example.com";
        User user = new User();
        user.setId(1L);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // When
        Long id = loginService.getIdByEmail(email);

        // Then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    void getEnabledByEmailShouldReturnEnabledStatus() {
        // Given
        String email = "user@example.com";
        User user = new User();
        user.setEnabled(true);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // When
        Boolean enabled = loginService.getEnabledByEmail(email);

        // Then
        assertThat(enabled).isTrue();
    }

}
