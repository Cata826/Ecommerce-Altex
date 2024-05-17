
package Store.Altex.services;

import Store.Altex.models.Product;
import Store.Altex.models.User;
import Store.Altex.models.Wishlist;
import Store.Altex.repositories.ProductRepository;
import Store.Altex.repositories.UserRepository;
import Store.Altex.repositories.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class WishlistServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void getAllWishlistsReturnsData() {

        List<Wishlist> wishlistList = new ArrayList<>();
        when(wishlistRepository.findAllWithUserAndProduct()).thenReturn(wishlistList);

        List<Wishlist> fetchedWishlists = wishlistService.getAllWishlists();

        assertThat(fetchedWishlists).isSameAs(wishlistList);
        verify(wishlistRepository).findAllWithUserAndProduct();
    }
    @Test
    void addToWishlistAddsItem() {
        Long userId = 1L;
        Long productId = 1L;
        User user = new User();
        Product product = new Product();

        Wishlist wishlistItem = new Wishlist();
        wishlistItem.setUser(user);
        wishlistItem.setProduct(product);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlistItem);

        Wishlist savedWishlistItem = wishlistService.addToWishlist(userId, productId);

        assertThat(savedWishlistItem.getUser()).isEqualTo(user);
        assertThat(savedWishlistItem.getProduct()).isEqualTo(product);
        verify(wishlistRepository).save(any(Wishlist.class));
    }


    @Test
    void addToWishlistThrowsIfUserNotFound() {
        Long userId = 1L;
        Long productId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> wishlistService.addToWishlist(userId, productId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void addToWishlistThrowsIfProductNotFound() {

        Long userId = 1L;
        Long productId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> wishlistService.addToWishlist(userId, productId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Product not found");
    }

    @Test
    void getProductsForUserWishlistReturnsProducts() {

        Long userId = 1L;
        List<Product> products = new ArrayList<>();
        when(wishlistRepository.findProductsByUserId(userId)).thenReturn(products);
        List<Product> fetchedProducts = wishlistService.getProductsForUserWishlist(userId);
        assertThat(fetchedProducts).isSameAs(products);
        verify(wishlistRepository).findProductsByUserId(userId);
    }

    @Test
    void getWishlistsByUserIdReturnsWishlist() {

        Long userId = 1L;
        List<Wishlist> wishlists = new ArrayList<>();
        when(wishlistRepository.findByUserId(userId)).thenReturn(wishlists);
        List<Wishlist> fetchedWishlists = wishlistService.getWishlistsByUserId(userId);
        assertThat(fetchedWishlists).isSameAs(wishlists);
        verify(wishlistRepository).findByUserId(userId);
    }

    @Test
    void removeFromWishlistDeletesItem() {

        Long wishlistItemId = 1L;
        doNothing().when(wishlistRepository).deleteById(wishlistItemId);
        wishlistService.removeFromWishlist(wishlistItemId);
        verify(wishlistRepository).deleteById(wishlistItemId);
    }
}
