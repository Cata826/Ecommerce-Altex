package Store.Altex.services;

import Store.Altex.models.Cart;
import Store.Altex.models.Product;
import Store.Altex.models.User;
import Store.Altex.repositories.CartRepository;
import Store.Altex.repositories.ProductRepository;
import Store.Altex.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CartServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCartsReturnsAllCarts() {

        List<Cart> expectedCarts = Arrays.asList(new Cart(), new Cart());
        when(cartRepository.findAllWithUserAndProduct()).thenReturn(expectedCarts);

        List<Cart> carts = cartService.getAllWishlists();


        assertThat(carts).isEqualTo(expectedCarts);
        verify(cartRepository).findAllWithUserAndProduct();
    }

    @Test
    void addToCartShouldAddCartSuccessfully() {
        Long userId = 1L;
        Long productId = 2L;
        User user = new User();
        user.setId(userId);
        Product product = new Product();
        product.setId(productId);
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart addedCart = cartService.addToCart(userId, productId);

        assertThat(addedCart.getUser()).isEqualTo(user);
        assertThat(addedCart.getProduct()).isEqualTo(product);
        verify(cartRepository).save(cart);
    }

    @Test
    void addToCartShouldFailWhenUserNotFound() {
        Long userId = 1L;
        Long productId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            cartService.addToCart(userId, productId);
        });
        assertThat(exception.getMessage()).contains("User not found");
    }

    @Test
    void addToCartShouldFailWhenProductNotFound() {

        Long userId = 1L;
        Long productId = 2L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());


        Exception exception = assertThrows(RuntimeException.class, () -> {
            cartService.addToCart(userId, productId);
        });
        assertThat(exception.getMessage()).contains("Product not found");
    }

    @Test
    void removeFromCartShouldRemoveCart() {

        Long orderId = 1L;
        doNothing().when(cartRepository).deleteById(orderId);
        cartService.removeFromCart(orderId);
        verify(cartRepository).deleteById(orderId);
    }
}
