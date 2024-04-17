package Store.Altex.services;
import Store.Altex.models.Order;
import Store.Altex.models.Product;
import Store.Altex.models.User;
import Store.Altex.repositories.OrderRepository;
import Store.Altex.repositories.ProductRepository;
import Store.Altex.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserShouldReturnUser() {
        // Given
        Long userId = 1L;
        User user = new User();
        when(orderRepository.findUserByUserId(userId)).thenReturn(Optional.of(user));

        // When
        Optional<User> result = orderService.getUser(userId);

        // Then
        assertThat(result).contains(user);
        verify(orderRepository).findUserByUserId(userId);
    }


    @Test
    void removeFromOrderShouldDeleteOrder() {
        // Given
        Long orderId = 1L;
        doNothing().when(orderRepository).deleteById(orderId);

        // When
        orderService.removeFromOrder(orderId);

        // Then
        verify(orderRepository).deleteById(orderId);
    }


    @Test
    void getAllWishlistsShouldReturnAllOrders() {
        // Given
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findAllWithUserAndProduct()).thenReturn(orders);

        // When
        List<Order> result = orderService.getAllWishlists();

        // Then
        assertThat(result).isEqualTo(orders);
        verify(orderRepository).findAllWithUserAndProduct();
    }

    @Test
    void getProductsForUserOrderShouldReturnProducts() {
        // Given
        Long userId = 1L;
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(orderRepository.findProductsByUserId(userId)).thenReturn(products);

        // When
        List<Product> result = orderService.getProductsForUserOrder(userId);

        // Then
        assertThat(result).isEqualTo(products);
        verify(orderRepository).findProductsByUserId(userId);
    }

    @Test
    void getOrdersByUserIdShouldReturnOrders() {
        // Given
        Long userId = 1L;
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findByUserId(userId)).thenReturn(orders);

        // When
        List<Order> result = orderService.getOrdersByUserId(userId);

        // Then
        assertThat(result).isEqualTo(orders);
        verify(orderRepository).findByUserId(userId);
    }


}
