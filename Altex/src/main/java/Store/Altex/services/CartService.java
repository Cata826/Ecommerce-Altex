package Store.Altex.services;

import Store.Altex.email.EmailSender;
import Store.Altex.email.EmailValidator;
import Store.Altex.models.Cart;
import Store.Altex.models.Product;
import Store.Altex.models.User;
import Store.Altex.repositories.CartRepository;
import Store.Altex.repositories.ProductRepository;
import Store.Altex.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
public class CartService {


    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final EmailValidator emailValidator;
    private final EmailSender emailSender;
    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getAllWishlists() {
        return cartRepository.findAllWithUserAndProduct();
    }

    public Cart addToCart(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setQuantity(product.getQuantity()-1);
        Cart order = new Cart();
        order.setUser(user);
        order.setProduct(product);

        return cartRepository.save(order);
    }
    //    private void sendOrderConfirmationEmail(String emailAddress) {
//        // Ensure the email address is valid
//        if (!emailValidator.test(emailAddress)) {
//            throw new IllegalStateException("Email is not valid");
//        }
//        String subject = "Order Confirmation";
////        String body = "Your order has been successfully processed.";
//        emailSender.send(emailAddress, subject);
//    }
    public List<Product> getProductsForUserCart(Long userId) {
        return cartRepository.findProductsByUserId(userId);
    }
    public List<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
    public void removeFromCart(Long order) {
        cartRepository.deleteById(order);
    }
    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }
}