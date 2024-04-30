package Store.Altex.services;

import Store.Altex.exceptions.UserNotFoundException;
import Store.Altex.models.Category;
import Store.Altex.models.Product;
import Store.Altex.repositories.CategoryRepository;
import Store.Altex.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Product createProduct(Product productRequest) {
        Set<Category> productCategories = new HashSet<>();
        for (Category categoryRequest : productRequest.getCategories()) {
            // Assuming category names are unique, so we only expect one or none
            List<Category> categories = categoryRepository.findByName(categoryRequest.getName());
            Category category;
            if (categories.isEmpty()) {
                category = new Category();
                category.setName(categoryRequest.getName());
                category = categoryRepository.save(category);
            } else {
                // Since category names are treated as unique, take the first one if exists
                category = categories.get(0);
            }
            productCategories.add(category);
        }
        productRequest.setCategories(productCategories);
        return productRepository.save(productRequest);
    }
    public List<Product> getProductsByCategoryId(Long categoryId) {
        // Assuming you have a method in ProductRepository that retrieves products by category id
        return productRepository.findByCategories_Id(categoryId);
    }
    @Transactional
    public void updateProductStars(Long productId, int stars) {
        int updatedRows = productRepository.updateStars(productId, stars);
        if (updatedRows == 0) {
            throw new EntityNotFoundException("Product with ID " + productId + " not found.");
        }
    }
    public void updateProductImage(Long productId, String imagePath) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setImage(imagePath);
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public Product updateProduct(Long id, Product updatedProductDetails) {
        Product product = getProductById(id);
        product.setName(updatedProductDetails.getName());
        product.setShort_description(updatedProductDetails.getShort_description());
        product.setLong_description(updatedProductDetails.getLong_description());
        product.setPret(updatedProductDetails.getPret());
        product.setQuantity(updatedProductDetails.getQuantity());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
