package Store.Altex.controllers;


import Store.Altex.models.Card;
import Store.Altex.services.CardService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/","http://127.0.0.1:1080/"})
@RequestMapping("/api/v1/cards")
@AllArgsConstructor
public class CardController {

    @Autowired
    private final CardService cardService;

//    @Autowired
//    public CardController(CardService cardService) {
//        this.cardService = cardService;
//    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCards() {
        List<Card> cards = cardService.getAllCards();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable Long id) {
        return cardService.getCardById(id)
                .map(card -> new ResponseEntity<>(card, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
//    @PostMapping
//    public ResponseEntity<Card> createCard(@RequestBody Card card) {
////        cardService.sen
////        cardService.senderofemail();
//        Card createdCard = cardService.saveCard(card);
//        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        Card savedCard = cardService.saveCard(card);

        return new ResponseEntity<>(savedCard, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
