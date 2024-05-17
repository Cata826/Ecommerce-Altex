package Store.Altex.services;
import Store.Altex.email.EmailSender;
import Store.Altex.models.Card;
import Store.Altex.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private EmailSender emailSender;
    @Autowired
    public CardService(CardRepository cardRepository,EmailSender emailSender) {
        this.cardRepository = cardRepository;
        this.emailSender = emailSender;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }

    private void sendEmailNotification() {
        emailSender.send("vladhodis@gmail.com", "PAYMENT SUCCESSFUL");
    }
    public Card saveCard(Card card) {
        sendEmailNotification();
        return cardRepository.save(card);

    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }

}
