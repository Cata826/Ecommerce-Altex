package Store.Altex.services;

import Store.Altex.models.Product;
import Store.Altex.models.Review;
import Store.Altex.models.User;
import Store.Altex.models.Wishlist;
import Store.Altex.repositories.ProductRepository;
import Store.Altex.repositories.ReviewRepository;
import Store.Altex.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Review> getAllReviewsForProduct(Long productId) {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Transactional
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }


    public double calculateAverageRating(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .mapToInt(Review::getStar)
                .average()
                .orElse(0.0);
    }

    @Transactional
    public Review addToReview(Long userId, Long productId, int star, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<Review> existingReview = reviewRepository.findByUserIdAndProductId(userId, productId);

        if (existingReview.isPresent()) {
            Review review = existingReview.get();
            review.setStar(star);
            review.setMessage(message);
            return reviewRepository.save(review);
        } else {
            Review newReview = new Review();
            newReview.setUser(user);
            newReview.setProduct(product);
            newReview.setStar(star);
            newReview.setMessage(message);
            return reviewRepository.save(newReview);
        }
    }


}
