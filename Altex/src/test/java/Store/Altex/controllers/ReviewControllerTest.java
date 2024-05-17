package Store.Altex.controllers;

import Store.Altex.models.Product;
import Store.Altex.models.Review;
import Store.Altex.models.User;
import Store.Altex.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    void addToReviewsShouldReturnReview() throws Exception {

        Long userId = 1L;
        Long productId = 1L;
        int star = 5;
        String message = "Great product";
        Review review = new Review();
        review.setUser(new User());
        review.setProduct(new Product());
        review.setStar(star);
        review.setMessage(message);

        when(reviewService.addToReview(userId, productId, star, message)).thenReturn(review);

        mockMvc.perform(post("/api/v1/reviews")
                        .param("userId", String.valueOf(userId))
                        .param("productId", String.valueOf(productId))
                        .param("star", String.valueOf(star))
                        .param("message", message))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(message));
        verify(reviewService).addToReview(userId, productId, star, message);
    }

    @Test
    void calculateAverageRatingShouldReturnAverageRating() throws Exception {
        Long productId = 1L;
        Double averageRating = 4.5;
        when(reviewService.calculateAverageRating(productId)).thenReturn(averageRating);

        mockMvc.perform(get("/api/v1/reviews/average/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(averageRating)));
    }
}
