package Store.Altex.services;

import Store.Altex.models.Product;
import Store.Altex.models.Review;
import Store.Altex.models.User;
import Store.Altex.repositories.ProductRepository;
import Store.Altex.repositories.ReviewRepository;
import Store.Altex.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void getAllReviewsForProductReturnsReviews() {
        // given
        Long productId = 1L;
        Review review = new Review();
        when(reviewRepository.findAll()).thenReturn(Arrays.asList(review));

        // when
        List<Review> reviews = reviewService.getAllReviewsForProduct(productId);

        // then
        assertThat(reviews).contains(review);
        verify(reviewRepository).findAll();
    }

    @Test
    void getReviewByIdReturnsReview() {
        // given
        Long reviewId = 1L;
        Optional<Review> review = Optional.of(new Review());
        when(reviewRepository.findById(reviewId)).thenReturn(review);

        // when
        Optional<Review> foundReview = reviewService.getReviewById(reviewId);

        // then
        assertThat(foundReview).isEqualTo(review);
        verify(reviewRepository).findById(reviewId);
    }

    @Test
    void saveReviewSavesAndReturnsReview() {
        // given
        Review review = new Review();
        when(reviewRepository.save(review)).thenReturn(review);

        // when
        Review savedReview = reviewService.saveReview(review);

        // then
        assertThat(savedReview).isEqualTo(review);
        verify(reviewRepository).save(review);
    }

    @Test
    void deleteReviewDeletesReview() {
        // given
        Long reviewId = 1L;
        doNothing().when(reviewRepository).deleteById(reviewId);

        // when
        reviewService.deleteReview(reviewId);

        // then
        verify(reviewRepository).deleteById(reviewId);
    }

    @Test
    void calculateAverageRatingCalculatesCorrectAverage() {
        // given
        Long productId = 1L;
        Review review1 = new Review();
        review1.setId(1L);
        review1.setUser(new User());
        review1.setProduct(new Product());
        review1.setStar(5);
        review1.setMessage("Great!");

        Review review2 = new Review();
        review2.setId(2L);
        review2.setUser(new User());
        review2.setProduct(new Product());
        review2.setStar(3);
        review2.setMessage("Good");

        Review review3 = new Review();
        review3.setId(3L);
        review3.setUser(new User());
        review3.setProduct(new Product());
        review3.setStar(1);
        review3.setMessage("Bad");

        List<Review> reviews = Arrays.asList(review1, review2, review3);
        when(reviewRepository.findByProductId(productId)).thenReturn(reviews);

        // when
        double averageRating = reviewService.calculateAverageRating(productId);

        // then
        assertThat(averageRating).isEqualTo((5 + 3 + 1) / 3.0);
        verify(reviewRepository).findByProductId(productId);
    }

    @Test
    void addToReviewAddsOrUpdatesReview() {
        // given
        Long userId = 1L;
        Long productId = 1L;
        User user = new User();
        Product product = new Product();

//        Long productId = 1L;
        Review review = new Review();
        review.setId(1L);
        review.setUser(new User());
        review.setProduct(new Product());
        review.setStar(4);
        review.setMessage("Nice product");


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(reviewRepository.findByUserIdAndProductId(userId, productId)).thenReturn(Optional.empty());
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // when
        Review addedReview = reviewService.addToReview(userId, productId, 4, "Nice product");

        // then
        assertThat(addedReview.getStar()).isEqualTo(4);
        assertThat(addedReview.getMessage()).isEqualTo("Nice product");
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void addToReviewThrowsIfUserNotFound() {
        // given
        Long userId = 1L;
        Long productId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> reviewService.addToReview(userId, productId, 4, "Nice product"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void addToReviewThrowsIfProductNotFound() {
        // given
        Long userId = 1L;
        Long productId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> reviewService.addToReview(userId, productId, 4, "Nice product"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Product not found");
    }

    // Add more tests to cover all scenarios, edge cases, and exception handling
}
