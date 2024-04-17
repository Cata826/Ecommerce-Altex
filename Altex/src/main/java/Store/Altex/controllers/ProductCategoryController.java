
package Store.Altex.controllers;

import Store.Altex.models.Product;
import Store.Altex.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000/","http://127.0.0.1:1080/"})
public class ProductCategoryController {

    private final ProductService productService;
//    private static final String TARGET_FOLDER = "uploads/";
//
//    @PostMapping("/{productId}/uploadImage")
//    public ResponseEntity<String> uploadImage(@PathVariable Long productId, @RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("No file uploaded");
//        }
//        try {
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(TARGET_FOLDER + file.getOriginalFilename());
//            Files.write(path, bytes);
//            // Assuming you have a service to update the product's image URL
//            productService.updateProductImage(productId, path.toString());
//            return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
//        } catch (IOException e) {
//            return ResponseEntity.internalServerError().body("Error occurred: " + e.getMessage());
//        }
//    }

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
}