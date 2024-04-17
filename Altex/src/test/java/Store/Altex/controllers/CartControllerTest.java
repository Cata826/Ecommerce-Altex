package Store.Altex.controllers;

import Store.Altex.models.Cart;
import Store.Altex.models.Order;
import Store.Altex.models.Product;
import Store.Altex.services.CartService;
import Store.Altex.services.OrderService;
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

class CartControllerTest {

    @Mock
    private CartService cartService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private CartController cartController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }



    @Test
    void removeFromCartReturnsOk() throws Exception {
        Long cartId = 1L;
        mockMvc.perform(delete("/api/v1/cart/{cartId}", cartId))
                .andExpect(status().isOk());
        verify(cartService).removeFromCart(cartId);
    }

    @Test
    void getAllCartItemsReturnsAllCartItems() throws Exception {
        List<Cart> carts = Arrays.asList(new Cart(), new Cart());
        when(cartService.getAllCartItems()).thenReturn(carts);

        mockMvc.perform(get("/api/v1/cart/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    void getCartsByUserIdReturnsCartsForUser() throws Exception {
        Long userId = 1L;
        List<Cart> carts = Arrays.asList(new Cart(), new Cart());
        when(cartService.getCartByUserId(userId)).thenReturn(carts);

        mockMvc.perform(get("/api/v1/cart/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getProductsForUserCartReturnsProductsForUserCart() throws Exception {
        Long userId = 1L;
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(cartService.getProductsForUserCart(userId)).thenReturn(products);

        mockMvc.perform(get("/api/v1/cart/user/{userId}/products", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$").isArray());
    }



    @Test
    void getAllCartsReturnsAllCarts() throws Exception {
        List<Cart> carts = Arrays.asList(new Cart(), new Cart());
        when(cartService.getAllWishlists()).thenReturn(carts);

        mockMvc.perform(get("/api/v1/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$").isArray());
    }

}