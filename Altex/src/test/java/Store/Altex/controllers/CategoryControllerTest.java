package Store.Altex.controllers;

import Store.Altex.models.Category;
import Store.Altex.services.CategoryService;
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
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void getAllCategoriesReturnsAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getCategoryByIdReturnsCategory() throws Exception {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(category));

        mockMvc.perform(get("/api/v1/categories/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryId));
    }

    @Test
    void getCategoryByIdReturnsNotFound() throws Exception {
        Long categoryId = 1L;
        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/categories/{id}", categoryId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCategoryCreatesAndReturnsCategory() throws Exception {
        Category category = new Category();
        category.setName("New Category");
        when(categoryService.saveCategory(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Category\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Category"));
    }

    @Test
    void updateCategoryUpdatesAndReturnsCategory() throws Exception {
        Long categoryId = 1L;
        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("Existing Category");

        Category updatedCategory = new Category();
        updatedCategory.setId(categoryId);
        updatedCategory.setName("Updated Category");

        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryService.saveCategory(any(Category.class))).thenReturn(updatedCategory);

        mockMvc.perform(put("/api/v1/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Category\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Category"));
    }

    @Test
    void deleteCategoryDeletesAndReturnsOk() throws Exception {
        Long categoryId = 1L;
        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(new Category()));

        mockMvc.perform(delete("/api/v1/categories/{id}", categoryId))
                .andExpect(status().isOk());
        verify(categoryService).deleteCategory(categoryId);
    }

    @Test
    void deleteCategoryReturnsNotFoundWhenCategoryDoesNotExist() throws Exception {
        Long categoryId = 1L;
        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/categories/{id}", categoryId))
                .andExpect(status().isNotFound());
    }
}
