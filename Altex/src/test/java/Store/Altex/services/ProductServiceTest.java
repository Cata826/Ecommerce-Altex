package Store.Altex.services;

import Store.Altex.exceptions.UserNotFoundException;
import Store.Altex.models.Category;
import Store.Altex.models.Product;
import Store.Altex.repositories.CategoryRepository;
import Store.Altex.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProductSuccessfully() {
        // given
        Category category = new Category();
        category.setName("Electronics");
        Product product = new Product();
        product.setCategories(new HashSet<>(Arrays.asList(category)));
        when(categoryRepository.findByName("Electronics")).thenReturn(Arrays.asList(category));
        when(productRepository.save(product)).thenReturn(product);

        // when
        Product createdProduct = productService.createProduct(product);

        // then
        assertThat(createdProduct).isEqualTo(product);
        verify(productRepository).save(product);
        verify(categoryRepository).findByName("Electronics");
    }


    @Test
    void updateProductStarsSuccessfully() {
        // given
        Long productId = 1L;
        int stars = 5;
        when(productRepository.updateStars(productId, stars)).thenReturn(1);

        // when
        productService.updateProductStars(productId, stars);

        // then
        verify(productRepository).updateStars(productId, stars);
    }

    @Test
    void updateProductStarsNotFoundThrowsException() {
        // given
        Long productId = 1L;
        int stars = 5;
        when(productRepository.updateStars(productId, stars)).thenReturn(0);

        // when
        // then
        assertThatThrownBy(() -> productService.updateProductStars(productId, stars))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Product with ID " + productId + " not found.");
    }

    @Test
    void getProductByIdFound() {
        // given
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // when
        Product foundProduct = productService.getProductById(productId);

        // then
        assertThat(foundProduct).isEqualTo(product);
    }

    @Test
    void getProductByIdNotFoundThrowsException() {
        // given
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> productService.getProductById(productId))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void updateProductDetails() {
        // given
        Long productId = 1L;
        Product existingProduct = new Product();
        Product updatedProductDetails = new Product();
        updatedProductDetails.setName("New Name");
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        // when
        Product updatedProduct = productService.updateProduct(productId, updatedProductDetails);

        // then
        assertThat(updatedProduct.getName()).isEqualTo("New Name");
        verify(productRepository).save(existingProduct);
    }

    @Test
    void deleteProductSuccessfully() {
        // given
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        // when
        productService.deleteProduct(productId);

        // then
        verify(productRepository).delete(product);
    }
}
