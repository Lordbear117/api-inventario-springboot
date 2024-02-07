package com.bear.inventario;

import com.bear.inventario.dto.category.CategoryDTO;
import com.bear.inventario.dto.category.CreateCategoryDTO;
import com.bear.inventario.dto.category.UpdateCategoryDTO;
import com.bear.inventario.exception.CategoryNotFoundException;
import com.bear.inventario.mapper.CategoryMapper;
import com.bear.inventario.model.Category;
import com.bear.inventario.repository.CategoryRepository;
import com.bear.inventario.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Service CategoryService should be injected")
    void smokeTest(){
        assertNotNull(categoryService);
    }

    @Test
    @DisplayName("Service CategoryService should return an empty list")
    void testFindAllEmpty() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(new LinkedList<>());

        // Act
        List<CategoryDTO> result = categoryService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Service CategoryService should return a list with categories")
    void testFindAllNotEmpty() {
        // Arrange
        List<Category> categories = new LinkedList<>();

        Category category1 = new Category();
        category1.setId(1);
        category1.setNombre("Computadoras");
        categories.add(category1);

        Category category2 = new Category();
        category2.setId(2);
        category2.setNombre("Celulares");
        categories.add(category2);

        when(categoryRepository.findAll()).thenReturn(categories);

        // CategoryDTO simulated list
        List<CategoryDTO> categoryDTOs = new LinkedList<>();
        for (Category category : categories) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(category.getId());
            dto.setNombre(category.getNombre());
            categoryDTOs.add(dto);
        }

        when(categoryMapper.toDTO(categories)).thenReturn(categoryDTOs);

        // Act
        List<CategoryDTO> result = categoryService.findAll();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        // Verify that unique object are created
        Set<CategoryDTO> uniqueCategories = new HashSet<>(result);
        assertEquals(result.size(), uniqueCategories.size());
    }

    @Test
    @DisplayName("Service CategoryService should save a new category when valid data is provided")
    void saveCategoryServiceTest() {
        // Arrange
        CreateCategoryDTO testData = new CreateCategoryDTO();
        testData.setNombre("Computadoras");

        Category entity = new Category();
        entity.setId(1);
        entity.setNombre("Computadoras");

        CategoryDTO expectedDTO = new CategoryDTO();
        expectedDTO.setId(1);
        expectedDTO.setNombre("Computadoras");

        when(categoryMapper.toModel(testData)).thenReturn(entity);
        when(categoryRepository.save(entity)).thenReturn(entity);
        when(categoryMapper.toDTO(entity)).thenReturn(expectedDTO);

        // Act
        CategoryDTO result = categoryService.save(testData);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDTO.getId(), result.getId());
        assertEquals(expectedDTO.getNombre(), result.getNombre());

        verify(categoryMapper, times(1)).toModel(testData);
        verify(categoryRepository, times(1)).save(entity);
        verify(categoryMapper, times(1)).toDTO(entity);
    }

    @Test
    @DisplayName("Service CategoryService should update an existing category when valid data is provided")
    void testUpdate() throws CategoryNotFoundException {
        // Arrange
        int categoryId = 1;
        UpdateCategoryDTO testData = new UpdateCategoryDTO();
        testData.setNombre("Nueva categoría");

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setNombre("Categoría existente");

        Optional<Category> optionalCategory = Optional.of(existingCategory);

        when(categoryRepository.findById((long) categoryId)).thenReturn(optionalCategory);

        // Act
        categoryService.update(categoryId, testData);

        // Assert
        verify(categoryRepository, times(1)).findById((long) categoryId);
        verify(categoryMapper, times(1)).update(existingCategory, testData);
        verify(categoryRepository, times(1)).save(existingCategory);
    }

    @Test
    @DisplayName("Service CategoryService exception should work if a category is not found")
    void UpdateCategoryNotFoundExceptionTest() {
        // Arrange
        long categoryId = 1;
        UpdateCategoryDTO testData = new UpdateCategoryDTO();
        testData.setNombre("Nueva categoría");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.update(categoryId, testData);
        });

        verify(categoryRepository, times(1)).findById(categoryId);
        // simulating a scenario where the category does not exist
        // and, therefore, no category needs to be updated or saved.
        verify(categoryMapper, never()).update(any(), any());
        verify(categoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Service CategoryService should delete existing category")
    void deleteExistingCategoryTest() throws CategoryNotFoundException {
        // Arrange
        long categoryId = 1L;
        Category category = new Category();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act
        categoryService.delete(categoryId);

        // Assert
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    @DisplayName("Service CategoryService delete non-existing category should throw exception")
    void deleteNonExistingCategoryTest() {
        // Arrange
        long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(CategoryNotFoundException.class, () -> categoryService.delete(categoryId));
    }

}
