//package Store.Altex.services;
//
//import Store.Altex.models.Product;
//import Store.Altex.models.User;
//import Store.Altex.models.Wishlist;
//import Store.Altex.repositories.ProductRepository;
//import Store.Altex.repositories.UserRepository;
//import Store.Altex.repositories.WishlistRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.*;
//
//class WishlistServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private WishlistRepository wishlistRepository;
//
//    @InjectMocks
//    private WishlistService wishlistService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void getAllWishlistsRetrievesWishlists() {
//        // given
//        List<Wishlist> wishlists = List.of(mock(Wishlist.class));
//        when(wishlistRepository.findAllWithUserAndProduct()).thenReturn(wishlists);
//
//        // when
//        List<Wishlist> result = wishlistService.getAllWishlists();
//
//        // then
//        assertThat(result).isEqualTo(wishlists);
//        verify(wishlistRepository).findAllWithUserAndProduct();
//    }
//
//    @Test
//    void addToWishlistAddsProductToUserWishlist() {
//        // given
//        Long userId = 1L;
//        Long productId = 1L;
//        User user = mock(User.class);
//        Product product = mock(Product.class);
//        Wishlist wishlist = new Wishlist();
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);
//
//        // when
//        Wishlist newWishlist = wishlistService.addToWishlist(userId, productId);
//
//        // then
//        assertThat(newWishlist).isNotNull();
//        assertThat(newWishlist.getUser()).isEqualTo(user);
//        assertThat(newWishlist.getProduct()).isEqualTo(product);
//        verify(userRepository).findById(userId);
//        verify(productRepository).findById(productId);
//        verify(wishlistRepository).save(any(Wishlist.class));
//    }
//
//    @Test
//    void addToWishlistThrowsIfUserNotFound() {
//        // given
//        Long userId = 1L;
//        Long productId = 1L;
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        // when
//        // then
//        assertThatThrownBy(() -> wishlistService.addToWishlist(userId, productId))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessageContaining("User not found");
//    }
//
//    @Test
//    void addToWishlistThrowsIfProductNotFound() {
//        // given
//        Long userId = 1L;
//        Long productId = 1L;
//        User user = mock(User.class);
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        // when
//        // then
//        assertThatThrownBy(() -> wishlistService.addToWishlist(userId, productId))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessageContaining("Product not found");
//    }
//}
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
        // given
        List<Wishlist> wishlistList = new ArrayList<>();
        when(wishlistRepository.findAllWithUserAndProduct()).thenReturn(wishlistList);

        // when
        List<Wishlist> fetchedWishlists = wishlistService.getAllWishlists();

        // then
        assertThat(fetchedWishlists).isSameAs(wishlistList);
        verify(wishlistRepository).findAllWithUserAndProduct();
    }
    @Test
    void addToWishlistAddsItem() {
        // given
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

        // when
        Wishlist savedWishlistItem = wishlistService.addToWishlist(userId, productId);

        // then
        assertThat(savedWishlistItem.getUser()).isEqualTo(user);
        assertThat(savedWishlistItem.getProduct()).isEqualTo(product);
        verify(wishlistRepository).save(any(Wishlist.class));
    }


//    @Test
//    void addToWishlistAddsItem() {
//        // given
//        Long userId = 1L;
//        Long productId = 1L;
//        User user = new User();
//        Product product = new Product();
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        // when
//        Wishlist wishlistItem = wishlistService.addToWishlist(userId, productId);
//
//        // then
//        assertThat(wishlistItem.getUser()).isEqualTo(user);
//        assertThat(wishlistItem.getProduct()).isEqualTo(product);
//        verify(wishlistRepository).save(any(Wishlist.class));
//    }

    @Test
    void addToWishlistThrowsIfUserNotFound() {
        // given
        Long userId = 1L;
        Long productId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> wishlistService.addToWishlist(userId, productId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void addToWishlistThrowsIfProductNotFound() {
        // given
        Long userId = 1L;
        Long productId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> wishlistService.addToWishlist(userId, productId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Product not found");
    }

    @Test
    void getProductsForUserWishlistReturnsProducts() {
        // given
        Long userId = 1L;
        List<Product> products = new ArrayList<>();
        when(wishlistRepository.findProductsByUserId(userId)).thenReturn(products);

        // when
        List<Product> fetchedProducts = wishlistService.getProductsForUserWishlist(userId);

        // then
        assertThat(fetchedProducts).isSameAs(products);
        verify(wishlistRepository).findProductsByUserId(userId);
    }

    @Test
    void getWishlistsByUserIdReturnsWishlist() {
        // given
        Long userId = 1L;
        List<Wishlist> wishlists = new ArrayList<>();
        when(wishlistRepository.findByUserId(userId)).thenReturn(wishlists);

        // when
        List<Wishlist> fetchedWishlists = wishlistService.getWishlistsByUserId(userId);

        // then
        assertThat(fetchedWishlists).isSameAs(wishlists);
        verify(wishlistRepository).findByUserId(userId);
    }

    @Test
    void removeFromWishlistDeletesItem() {
        // given
        Long wishlistItemId = 1L;
        doNothing().when(wishlistRepository).deleteById(wishlistItemId);

        // when
        wishlistService.removeFromWishlist(wishlistItemId);

        // then
        verify(wishlistRepository).deleteById(wishlistItemId);
    }
}
