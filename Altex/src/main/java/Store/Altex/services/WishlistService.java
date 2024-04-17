package Store.Altex.services;
import Store.Altex.models.Product;
import Store.Altex.models.User;
import Store.Altex.models.Wishlist;
import Store.Altex.repositories.ProductRepository;
import Store.Altex.repositories.UserRepository;
import Store.Altex.repositories.WishlistRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class WishlistService {


    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    @Autowired
    private WishlistRepository wishlistRepository;

    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.findAllWithUserAndProduct();
    }

    public Wishlist addToWishlist(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Wishlist wishlistItem = new Wishlist();
        wishlistItem.setUser(user);
        wishlistItem.setProduct(product);
        return wishlistRepository.save(wishlistItem);
    }
    public List<Product> getProductsForUserWishlist(Long userId) {
        return wishlistRepository.findProductsByUserId(userId);
    }
    public List<Wishlist> getWishlistsByUserId(Long userId) {
        return wishlistRepository.findByUserId(userId);
    }
    public void removeFromWishlist(Long wishlistItemId) {
        wishlistRepository.deleteById(wishlistItemId);
    }
    public List<Wishlist> getAllWishlistItems() {
        return wishlistRepository.findAll();
    }


}
