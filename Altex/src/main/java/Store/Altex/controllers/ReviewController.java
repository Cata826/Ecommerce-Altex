package Store.Altex.controllers;
import Store.Altex.models.Review;
import Store.Altex.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = {"http://localhost:3000/","http://127.0.0.1:1080/"})
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping()
    public ResponseEntity<Review> addToReviews(@RequestParam Long userId, @RequestParam Long productId,
                                               @RequestParam int star, @RequestParam String message)  {
        Review review = reviewService.addToReview(userId, productId, star, message);
        System.out.println("Received request: userId=" + userId + ", productId=" + productId + ", star=" + star + ", message=" + message);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/average/{productId}")
    public ResponseEntity<Double> calculateAverageRating(@PathVariable Long productId) {
        Double averageRating = reviewService.calculateAverageRating(productId);
        return ResponseEntity.ok(averageRating);
    }



}
