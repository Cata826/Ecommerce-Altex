package Store.Altex.controllers;

import Store.Altex.models.Order;
import Store.Altex.models.Product;
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

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void addToOrderShouldReturnOrder() throws Exception {
        Long userId = 1L;
        Long productId = 1L;
        Order order = new Order();

        when(orderService.addToWishlist(userId, productId)).thenReturn(order);

        mockMvc.perform(post("/api/v1/order")
                        .param("userId", String.valueOf(userId))
                        .param("productId", String.valueOf(productId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void removeFromOrderShouldReturnOkStatus() throws Exception {
        Long wishlistItemId = 1L;

        mockMvc.perform(delete("/api/v1/order/{wishlistItemId}", wishlistItemId))
                .andExpect(status().isOk());

        verify(orderService).removeFromOrder(wishlistItemId);
    }

    @Test
    void getAllOrderItemsShouldReturnListOfOrders() throws Exception {
        List<Order> orders = Arrays.asList(new Order(), new Order());

        when(orderService.getAllOrderItems()).thenReturn(orders);

        mockMvc.perform(get("/api/v1/order/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void getOrdersByUserIdShouldReturnListOfOrders() throws Exception {
        Long userId = 1L;
        List<Order> orders = Arrays.asList(new Order(), new Order());

        when(orderService.getOrdersByUserId(userId)).thenReturn(orders);

        mockMvc.perform(get("/api/v1/order/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void getProductsForUserOrderShouldReturnListOfProducts() throws Exception {
        Long userId = 1L;
        List<Product> products = Arrays.asList(new Product(), new Product());

        when(orderService.getProductsForUserOrder(userId)).thenReturn(products);

        mockMvc.perform(get("/api/v1/order/user/{userId}/products", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void getAllOrderShouldReturnListOfOrders() throws Exception {
        List<Order> orders = Arrays.asList(new Order(), new Order());

        when(orderService.getAllWishlists()).thenReturn(orders);

        mockMvc.perform(get("/api/v1/order"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());
    }
}
