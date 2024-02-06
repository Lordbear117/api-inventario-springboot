package com.bear.inventario;

import com.bear.inventario.controller.ProductController;
import com.bear.inventario.dto.product.CreateProductDTO;
import com.bear.inventario.dto.product.ProductDTO;
import com.bear.inventario.dto.product.UpdateProductDTO;
import com.bear.inventario.exception.ProductNotFoundException;
import com.bear.inventario.service.ProductService;
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
public class ProductControllerTest {

    @MockBean
    private ProductService service;

    @InjectMocks
    private ProductController controller;

    @Autowired
    public ProductControllerTest (ProductService service,ProductController controller) {
        this.service = service;
        this.controller = controller;
    }

    @Test
    @DisplayName("Controller Product should be injected")
    public void smokeTest(){
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Controller should return a list of products")
    public void findAllProductsTest(){
        // Arrange o precondicion
        List<ProductDTO> fakeData = new LinkedList<>();

        ProductDTO fakeProduct = new ProductDTO();

        fakeProduct.setId(100);
        fakeProduct.setNombre("TV SAMSUNG OLED 65");
        fakeProduct.setDescripcion("New TV for sale 2024");
        fakeProduct.setCantidad(200);
        fakeProduct.setPrecio(15000);

        fakeData.add(fakeProduct);

        // Mimic call of findAll() method
        when(service.findAll()).thenReturn(fakeData);

        // Act o acto
        List<ProductDTO> result = controller.findAll();

        // Assert
        assertEquals(fakeData, result);

    }

    @Test
    @DisplayName("Controller should save a product")
    public void saveProductTest(){
        // Arrange
        CreateProductDTO dto = new CreateProductDTO();

        dto.setNombre("TV SAMSUNG OLED 65");
        dto.setDescripcion("New TV for sale 2024");
        dto.setCantidad(200);
        dto.setPrecio(15000);

        ProductDTO saved = new ProductDTO();

        saved.setId(1);
        saved.setNombre("TV SAMSUNG OLED 65");
        saved.setDescripcion("New TV for sale 2024");
        saved.setCantidad(200);
        saved.setPrecio(15000);

        when(service.save(any(CreateProductDTO.class))).thenReturn(saved);

        // Act
        ProductDTO result = controller.save(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.getNombre(), result.getNombre());
        assertEquals(dto.getCantidad(), result.getCantidad());
    }

    @Test
    @DisplayName("Controller should update a product")
    public void updateProductTest() throws ProductNotFoundException {
        // Arrange
        long productId = 1L;
        UpdateProductDTO updateData = new UpdateProductDTO();

        updateData.setNombre("TV SAMSUNG OLED 65");
        updateData.setDescripcion("New TV for sale 2024");
        updateData.setCantidad(200);
        updateData.setPrecio(15000);

        // Act
        controller.update(productId, updateData);

        // Assert
        // verify that productService.update was called with valid args
        verify(service).update(productId, updateData);
    }

    @Test
    @DisplayName("Controller exception should work if product is not found")
    public void updateProductNotFoundExceptionTest() throws ProductNotFoundException {
        // Arrange
        long productId = 2L;
        UpdateProductDTO updateData = new UpdateProductDTO();
        doThrow(new ProductNotFoundException(productId)).when(service).update(productId, updateData);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            controller.update(productId, updateData);
        });
    }

    @Test
    @DisplayName("Controller should handle product deletion")
    public void deleteProductTest() throws ProductNotFoundException {
        // Arrange
        long productId = 2L;
        doNothing().when(service).delete(productId);

        // Act & Assert
        assertDoesNotThrow(() -> {
            controller.delete(productId);
        });

        // Verify that the delete method was called with the specified productId
        verify(service, times(1)).delete(productId);
    }

    @Test
    @DisplayName("Controller exception should work if product is not found during deletion")
    public void deleteProductNotFoundExceptionTest() throws ProductNotFoundException {
        // Arrange
        long productId = 2L;
        doThrow(new ProductNotFoundException(productId)).when(service).delete(productId);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            controller.delete(productId);
        });

        // Verify that the delete method was called with the specified productId
        verify(service, times(1)).delete(productId);
    }

}
