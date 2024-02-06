package com.bear.inventario;

import com.bear.inventario.controller.CategoryController;
import com.bear.inventario.dto.category.CategoryDTO;
import com.bear.inventario.dto.category.CreateCategoryDTO;
import com.bear.inventario.dto.category.UpdateCategoryDTO;
import com.bear.inventario.exception.CategoryNotFoundException;
import com.bear.inventario.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CategoryControllerTest {

    @MockBean
    private CategoryService service;

    @InjectMocks
    private CategoryController controller;

    @Autowired
    public CategoryControllerTest (CategoryService service, CategoryController controller) {
        this.service = service;
        this.controller = controller;
    }

    @Test
    @DisplayName("Controller Category should be injected")
    public void smokeTest(){
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Controller should return a list of categories")
    public void findAllCategoriesTest(){
        // Arrange o precondicion
        List<CategoryDTO> fakeData = new LinkedList<>();

        CategoryDTO fakeCategory = new CategoryDTO();

        fakeCategory.setId(1);
        fakeCategory.setNombre("Computadoras y Laptops");

        fakeData.add(fakeCategory);

        // Mimic call of findAll() method
        when(service.findAll()).thenReturn(fakeData);

        // Act o acto
        List<CategoryDTO> result = controller.findAll();

        // Assert
        assertEquals(fakeData, result);
    }

    @Test
    @DisplayName("Controller should save a category")
    public void saveCategoryTest(){
        // Arrange
        CreateCategoryDTO dto = new CreateCategoryDTO();

        dto.setNombre("Computadoras y Laptops");

        CategoryDTO saved = new CategoryDTO();

        saved.setId(1);
        saved.setNombre("Computadoras y Laptops");

        when(service.save(any(CreateCategoryDTO.class))).thenReturn(saved);

        // Act
        CategoryDTO result = controller.save(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.getNombre(), result.getNombre());
    }

    @Test
    @DisplayName("Controller should update a category")
    public void updateCategoryTest() throws CategoryNotFoundException {
        // Arrange
        long categoryId = 1L;
        UpdateCategoryDTO updateData = new UpdateCategoryDTO();

        updateData.setNombre("Computadoras y Laptops");

        // Act
        controller.update(categoryId, updateData);

        // Assert
        // verify that productService.update was called with valid args
        verify(service).update(categoryId, updateData);
    }

    @Test
    @DisplayName("Controller exception should work if a category is not found")
    public void updateCategoryNotFoundExceptionTest() throws CategoryNotFoundException {
        // Arrange
        long categoryId = 2L;
        UpdateCategoryDTO updateData = new UpdateCategoryDTO();
        doThrow(new CategoryNotFoundException(categoryId)).when(service).update(categoryId, updateData);

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> {
            controller.update(categoryId, updateData);
        });
    }

    @Test
    @DisplayName("Controller should handle category deletion")
    public void deleteCategoryTest() throws CategoryNotFoundException {
        // Arrange
        long categoryId = 2L;
        doNothing().when(service).delete(categoryId);

        // Act & Assert
        assertDoesNotThrow(() -> {
            controller.delete(categoryId);
        });

        // Verify that the delete method was called with the specified categoryId
        verify(service, times(1)).delete(categoryId);
    }

    @Test
    @DisplayName("Controller exception should work if a category is not found during deletion")
    public void deleteCategoryNotFoundExceptionTest() throws CategoryNotFoundException {
        // Arrange
        long categoryId = 2L;
        doThrow(new CategoryNotFoundException(categoryId)).when(service).delete(categoryId);

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> {
            controller.delete(categoryId);
        });

        // Verify that the delete method was called with the specified categoryId
        verify(service, times(1)).delete(categoryId);
    }

}
