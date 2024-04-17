package Store.Altex.services;

import Store.Altex.models.User;
import Store.Altex.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void canLoadUserByUsername() {
        // given
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // when
        UserDetails foundUser = userService.loadUserByUsername(email);

        // then
        assertThat(foundUser).isNotNull();
        verify(userRepository).findByEmail(email);
    }

    @Test
    void willThrowWhenEmailNotFound() {
        // given
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> userService.loadUserByUsername(email))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining(String.format(UserService.USER_NOT_FOUND_MSG, email));
    }

    @Test
    void canSignUpUser() {
        // given
        String email = "test@example.com";
        String password = "password123";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(password)).thenReturn("encodedPassword");

        // when
        String token = userService.signUpUser(user);

        // then
        assertThat(token).isNotNull();
        assertThat(UUID.fromString(token)).isInstanceOf(UUID.class);
        verify(userRepository).save(any(User.class));
        verify(confirmationTokenService).saveConfirmationToken(any());
    }

    @Test
    void signUpUserWillThrowIfEmailIsTaken() {
        // given
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // when
        // then
        assertThatThrownBy(() -> userService.signUpUser(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("email already taken");
    }
}
