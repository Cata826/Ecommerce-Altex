package Store.Altex.services;

import Store.Altex.email.EmailSender;
import Store.Altex.email.EmailValidator;
import Store.Altex.models.ConfirmationToken;
import Store.Altex.models.User;
import Store.Altex.requests.RegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.assertj.core.api.Assertions.*;

class RegistrationServiceTest {

    @Mock
    private EmailValidator emailValidator;
    @Mock
    private ConfirmationTokenService confirmationTokenService;
    @Mock
    private EmailSender emailSender;
    @Mock
    private UserService userService;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

//    @Test
//    void registerValidEmail() {
//        // given
//        RegistrationRequest request = new RegistrationRequest("John", "Doe", "john.doe@example.com", "password");
//        when(emailValidator.test(request.getEmail())).thenReturn(true);
//        when(userService.signUpUser(any(User.class))).thenReturn("token123");
//        doNothing().when(emailSender).send(eq(request.getEmail()), anyString());
//
//        // when
//        String token = registrationService.register(request);
//
//        // then
//        assertThat(token).isEqualTo("token123");
//        verify(userService).signUpUser(any(User.class));
//        verify(emailSender).send(eq(request.getEmail()), anyString());
//    }

    @Test
    void registerInvalidEmailThrowsException() {
        // given
        RegistrationRequest request = new RegistrationRequest("John", "Doe", "john.doe@example.com", "password");
        when(emailValidator.test(request.getEmail())).thenReturn(false);

        // when
        // then
        assertThatThrownBy(() -> registrationService.register(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("email not valid");
    }


    @Test
    void confirmTokenNotFoundThrowsException() {
        // given
        String token = "invalidToken";
        when(confirmationTokenService.getToken(token)).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> registrationService.confirmToken(token))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("token not found");
    }

    @Test
    void confirmTokenExpiredThrowsException() {
        // given
        String token = "token123";
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().minusMinutes(1), new User());
        when(confirmationTokenService.getToken(token)).thenReturn(Optional.of(confirmationToken));

        // when
        // then
        assertThatThrownBy(() -> registrationService.confirmToken(token))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("token expired");
    }
}
