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
        // Given
        ConfirmationToken token = new ConfirmationToken();

        // When
        confirmationTokenService.saveConfirmationToken(token);

        // Then
        verify(confirmationTokenRepository).save(token);
    }

    @Test
    void getTokenShouldReturnToken() {
        // Given
        String tokenValue = "token123";
        ConfirmationToken token = new ConfirmationToken();
        token.setToken(tokenValue);
        when(confirmationTokenRepository.findByToken(tokenValue)).thenReturn(Optional.of(token));

        // When
        Optional<ConfirmationToken> retrievedToken = confirmationTokenService.getToken(tokenValue);

        // Then
        assertThat(retrievedToken).isPresent();
        assertThat(retrievedToken.get()).isEqualTo(token);
    }

    @Test
    void getTokenShouldReturnEmptyIfTokenNotFound() {
        // Given
        String tokenValue = "nonexistentToken";
        when(confirmationTokenRepository.findByToken(tokenValue)).thenReturn(Optional.empty());

        // When
        Optional<ConfirmationToken> retrievedToken = confirmationTokenService.getToken(tokenValue);

        // Then
        assertThat(retrievedToken).isNotPresent();
    }

    @Test
    void setConfirmedAtShouldUpdateToken() {
        // Given
        String tokenValue = "token123";
        when(confirmationTokenRepository.updateConfirmedAt(eq(tokenValue), any(LocalDateTime.class))).thenReturn(1);

        // When
        int updatedRows = confirmationTokenService.setConfirmedAt(tokenValue);

        // Then
        assertThat(updatedRows).isEqualTo(1);
        verify(confirmationTokenRepository).updateConfirmedAt(eq(tokenValue), localDateTimeCaptor.capture());

        // Optionally check if the captured time is reasonable (e.g., not too far in the past or future)
        LocalDateTime capturedTime = localDateTimeCaptor.getValue();
        assertThat(capturedTime).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(capturedTime).isAfter(LocalDateTime.now().minusSeconds(1));  // Allow some leeway for test execution delays
    }
}

