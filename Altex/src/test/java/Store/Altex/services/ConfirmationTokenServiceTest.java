package Store.Altex.services;

import Store.Altex.models.ConfirmationToken;
import Store.Altex.repositories.ConfirmationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class ConfirmationTokenServiceTest {

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @InjectMocks
    private ConfirmationTokenService confirmationTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Captor
    private ArgumentCaptor<LocalDateTime> localDateTimeCaptor;
    @Test
    void saveConfirmationToken() {

        ConfirmationToken token = new ConfirmationToken();
        confirmationTokenService.saveConfirmationToken(token);
        verify(confirmationTokenRepository).save(token);
    }

    @Test
    void getTokenShouldReturnToken() {

        String tokenValue = "token123";
        ConfirmationToken token = new ConfirmationToken();
        token.setToken(tokenValue);
        when(confirmationTokenRepository.findByToken(tokenValue)).thenReturn(Optional.of(token));
        Optional<ConfirmationToken> retrievedToken = confirmationTokenService.getToken(tokenValue);
        assertThat(retrievedToken).isPresent();
        assertThat(retrievedToken.get()).isEqualTo(token);
    }

    @Test
    void getTokenShouldReturnEmptyIfTokenNotFound() {

        String tokenValue = "nonexistentToken";
        when(confirmationTokenRepository.findByToken(tokenValue)).thenReturn(Optional.empty());
        Optional<ConfirmationToken> retrievedToken = confirmationTokenService.getToken(tokenValue);
        assertThat(retrievedToken).isNotPresent();
    }

    @Test
    void setConfirmedAtShouldUpdateToken() {

        String tokenValue = "token123";
        when(confirmationTokenRepository.updateConfirmedAt(eq(tokenValue), any(LocalDateTime.class))).thenReturn(1);
        int updatedRows = confirmationTokenService.setConfirmedAt(tokenValue);
        assertThat(updatedRows).isEqualTo(1);
        verify(confirmationTokenRepository).updateConfirmedAt(eq(tokenValue), localDateTimeCaptor.capture());
        LocalDateTime capturedTime = localDateTimeCaptor.getValue();
        assertThat(capturedTime).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(capturedTime).isAfter(LocalDateTime.now().minusSeconds(1));  // Allow some leeway for test execution delays
    }
}

