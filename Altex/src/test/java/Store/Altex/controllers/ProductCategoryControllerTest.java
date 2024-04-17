package Store.Altex.controllers;

import Store.Altex.models.Product;
import Store.Altex.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductCategoryControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductCategoryController productCategoryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productCategoryController).build();
    }



    @Test
    void updateProductStarsShouldReturnStatus() throws Exception {
        // Given
        Long productId = 1L;
        int stars = 5;
        doNothing().when(productService).updateProductStars(productId, stars);

        // When / Then
        mockMvc.perform(put("/api/v1/products/{productId}/stars", productId)
                        .param("stars", String.valueOf(stars)))
                .andExpect(status().isOk())
                .andExpect(content().string("Product stars updated successfully."));
    }

    @Test
    void getAllProductsShouldReturnProducts() throws Exception {
        // Given
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.getAllProducts()).thenReturn(products);

        // When / Then
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getProductByIdShouldReturnProduct() throws Exception {
        // Given
        Long id = 1L;
        Product product = new Product();
        when(productService.getProductById(id)).thenReturn(product);

        // When / Then
        mockMvc.perform(get("/api/v1/products/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void updateProductShouldUpdateAndReturnProduct() throws Exception {
        // Given
        Long id = 1L;
        Product product = new Product();
        when(productService.updateProduct(eq(id), any(Product.class))).thenReturn(product);

        // When / Then
        mockMvc.perform(put("/api/v1/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Product\",\"description\":\"Updated description\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProductShouldReturnNoContent() throws Exception {
        // Given
        Long id = 1L;
        doNothing().when(productService).deleteProduct(id);

        // When / Then
        mockMvc.perform(delete("/api/v1/products/{id}", id))
                .andExpect(status().isNoContent());
    }
}
