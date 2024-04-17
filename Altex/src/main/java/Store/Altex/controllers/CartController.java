package Store.Altex.controllers;


import Store.Altex.models.Cart;
import Store.Altex.models.Order;
import Store.Altex.models.Product;
import Store.Altex.services.CartService;
import Store.Altex.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/cart")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000/","http://127.0.0.1:1080/"})
public class CartController {

    private final CartService cartService;
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<Cart> addToCart(@RequestParam Long userId, @RequestParam Long productId) {
        Cart wishlistItem = cartService.addToCart(userId, productId);
        return ResponseEntity.ok(wishlistItem);
    }


    @DeleteMapping("/{wishlistItemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long wishlistItemId) {
        cartService.removeFromCart(wishlistItemId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cart>> getAllCartItems() {
        List<Cart> wishlistItems = cartService.getAllCartItems();
        return ResponseEntity.ok(wishlistItems);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cart>> getCartsByUserId(@PathVariable Long userId) {
        List<Cart> wishlists = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(wishlists);
    }
    @GetMapping("/user/{userId}/products")
    public ResponseEntity<List<Product>> getProductsForUserCart(@PathVariable Long userId) {
        List<Product> products = cartService.getProductsForUserCart(userId);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/user/{userId}/products/move")
    public ResponseEntity<List<Product>> move(@PathVariable Long userId) throws MessagingException {
        List<Product> products = cartService.getProductsForUserCart(userId);
        for(Product product : products) {
            Long productId = product.getId();
            Order order = orderService.addToWishlist(userId, productId);
        }
        List<Cart> carts = cartService.getCartByUserId(userId);
        for(Cart cart : carts){
            Long cartId = cart.getId();
            cartService.removeFromCart(cartId);
        }
        return ResponseEntity.ok(products);
    }

//        public List<Cart> getCartByUserId(Long userId) {
//        return cartRepository.findByUserId(userId);
//    }
//    public void removeFromCart(Long order) {
//        cartRepository.deleteById(order);
//    }
    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> wishlists = cartService.getAllWishlists();
        return ResponseEntity.ok(wishlists);
    }

}
