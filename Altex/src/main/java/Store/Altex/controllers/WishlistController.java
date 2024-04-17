package Store.Altex.controllers;


import Store.Altex.models.Product;
import Store.Altex.models.Wishlist;
import Store.Altex.services.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/wishlist")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000/","http://127.0.0.1:1080/"})
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<Wishlist> addToWishlist(@RequestParam Long userId, @RequestParam Long productId) {
        Wishlist wishlistItem = wishlistService.addToWishlist(userId, productId);
        return ResponseEntity.ok(wishlistItem);
    }


    @DeleteMapping("/{wishlistItemId}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable Long wishlistItemId) {
        wishlistService.removeFromWishlist(wishlistItemId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Wishlist>> getAllWishlistItems() {
        List<Wishlist> wishlistItems = wishlistService.getAllWishlistItems();
        return ResponseEntity.ok(wishlistItems);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Wishlist>> getWishlistsByUserId(@PathVariable Long userId) {
        List<Wishlist> wishlists = wishlistService.getWishlistsByUserId(userId);
        return ResponseEntity.ok(wishlists);
    }
    @GetMapping("/user/{userId}/products")
    public ResponseEntity<List<Product>> getProductsForUserWishlist(@PathVariable Long userId) {
        List<Product> products = wishlistService.getProductsForUserWishlist(userId);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<List<Wishlist>> getAllWishlists() {
        List<Wishlist> wishlists = wishlistService.getAllWishlists();
        return ResponseEntity.ok(wishlists);
    }

}
