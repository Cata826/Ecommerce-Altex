package Store.Altex.controllers;

import Store.Altex.models.Order;
import Store.Altex.models.Product;
import Store.Altex.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/order")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000/","http://127.0.0.1:1080/"})
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> addToOrder(@RequestParam Long userId, @RequestParam Long productId) throws MessagingException {
        Order wishlistItem = orderService.addToWishlist(userId, productId);
        return ResponseEntity.ok(wishlistItem);
    }


    @DeleteMapping("/{wishlistItemId}")
    public ResponseEntity<?> removeFromOrder(@PathVariable Long wishlistItemId) {
        orderService.removeFromOrder(wishlistItemId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrderItems() {
        List<Order> wishlistItems = orderService.getAllOrderItems();
        return ResponseEntity.ok(wishlistItems);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> wishlists = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(wishlists);
    }
    @GetMapping("/user/{userId}/products")
    public ResponseEntity<List<Product>> getProductsForUserOrder(@PathVariable Long userId) {
        List<Product> products = orderService.getProductsForUserOrder(userId);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrder() {
        List<Order> wishlists = orderService.getAllWishlists();
        return ResponseEntity.ok(wishlists);
    }

}