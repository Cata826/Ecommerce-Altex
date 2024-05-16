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
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }

    public Card saveCard(Card card) {

        return cardRepository.save(card);

    }
    public void senderofemail()
    {
        emailSender.send( "PAYMENT SUCCESSFULLY","vladhodis@gmail.com");
    }
    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }

}
