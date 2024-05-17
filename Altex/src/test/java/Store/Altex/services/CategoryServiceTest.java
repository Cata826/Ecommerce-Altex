package Store.Altex.services;

import Store.Altex.models.Category;
import Store.Altex.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategoriesReturnsAllCategories() {

        List<Category> expectedCategories = Arrays.asList(new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(expectedCategories);


        List<Category> categories = categoryService.getAllCategories();


        assertThat(categories).isEqualTo(expectedCategories);
        verify(categoryRepository).findAll();
    }

    @Test
    void getCategoryByIdReturnsCategory() {

        Long id = 1L;
        Optional<Category> expectedCategory = Optional.of(new Category());
        when(categoryRepository.findById(id)).thenReturn(expectedCategory);
        Optional<Category> category = categoryService.getCategoryById(id);
        assertTrue(category.isPresent());
        assertThat(category).isEqualTo(expectedCategory);
        verify(categoryRepository).findById(id);
    }

    @Test
    void saveCategorySavesAndReturnsCategory() {

        Category category = new Category();
        when(categoryRepository.save(category)).thenReturn(category);

        Category savedCategory = categoryService.saveCategory(category);

        assertThat(savedCategory).isEqualTo(category);
        verify(categoryRepository).save(category);
    }

    @Test
    void deleteCategoryDeletesCategory() {

        Long id = 1L;
        doNothing().when(categoryRepository).deleteById(id);

        categoryService.deleteCategory(id);

        verify(categoryRepository).deleteById(id);
    }
}
