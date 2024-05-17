
package Store.Altex.controllers;

import Store.Altex.models.Product;
import Store.Altex.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000/","http://127.0.0.1:1080/"})
public class ProductCategoryController {

    private final ProductService productService;
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }
    @PutMapping("/{productId}/stars")
    public ResponseEntity<String> updateProductStars(@PathVariable Long productId, @RequestParam int stars) {
        productService.updateProductStars(productId, stars);
        return ResponseEntity.ok("Product stars updated successfully.");
    }
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProductDetails) {
        return ResponseEntity.ok(productService.updateProduct(id, updatedProductDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }
}