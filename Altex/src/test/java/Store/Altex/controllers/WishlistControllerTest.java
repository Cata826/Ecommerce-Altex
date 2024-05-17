package Store.Altex.controllers;

import Store.Altex.models.Product;
import Store.Altex.models.Wishlist;
import Store.Altex.services.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WishlistControllerTest {

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private WishlistController wishlistController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(wishlistController).build();
    }

    @Test
    void addToWishlistShouldReturnWishlistItem() throws Exception {
        Long userId = 1L;
        Long productId = 1L;
        Wishlist wishlist = new Wishlist();
        when(wishlistService.addToWishlist(userId, productId)).thenReturn(wishlist);

        mockMvc.perform(post("/api/v1/wishlist")
                        .param("userId", String.valueOf(userId))
                        .param("productId", String.valueOf(productId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
        verify(wishlistService).addToWishlist(userId, productId);
    }

    @Test
    void removeFromWishlistShouldReturnOkStatus() throws Exception {
        Long wishlistItemId = 1L;

        mockMvc.perform(delete("/api/v1/wishlist/{wishlistItemId}", wishlistItemId))
                .andExpect(status().isOk());

        verify(wishlistService).removeFromWishlist(wishlistItemId);
    }

    @Test
    void getAllWishlistItemsShouldReturnListOfWishlistItems() throws Exception {
        List<Wishlist> wishlists = Arrays.asList(new Wishlist(), new Wishlist());

        when(wishlistService.getAllWishlistItems()).thenReturn(wishlists);

        mockMvc.perform(get("/api/v1/wishlist/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());
        verify(wishlistService).getAllWishlistItems();
    }

    @Test
    void getWishlistsByUserIdShouldReturnListOfWishlistItems() throws Exception {
        Long userId = 1L;
        List<Wishlist> wishlists = Arrays.asList(new Wishlist(), new Wishlist());

        when(wishlistService.getWishlistsByUserId(userId)).thenReturn(wishlists);

        mockMvc.perform(get("/api/v1/wishlist/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());
        verify(wishlistService).getWishlistsByUserId(userId);
    }

    @Test
    void getProductsForUserWishlistShouldReturnListOfProducts() throws Exception {
        Long userId = 1L;
        List<Product> products = Arrays.asList(new Product(), new Product());

        when(wishlistService.getProductsForUserWishlist(userId)).thenReturn(products);

        mockMvc.perform(get("/api/v1/wishlist/user/{userId}/products", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());
        verify(wishlistService).getProductsForUserWishlist(userId);
    }

    @Test
    void getAllWishlistsShouldReturnListOfWishlists() throws Exception {
        List<Wishlist> wishlists = Arrays.asList(new Wishlist(), new Wishlist());

        when(wishlistService.getAllWishlists()).thenReturn(wishlists);

        mockMvc.perform(get("/api/v1/wishlist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());
        verify(wishlistService).getAllWishlists();
    }
}
