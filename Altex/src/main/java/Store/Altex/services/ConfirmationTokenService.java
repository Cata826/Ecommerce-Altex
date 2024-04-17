package Store.Altex.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import Store.Altex.models.*;
import Store.Altex.repositories.ConfirmationTokenRepository;
import Store.Altex.services.ConfirmationTokenService;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}