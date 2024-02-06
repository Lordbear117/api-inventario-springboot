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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ProductControllerTest {

    @MockBean
    private ProductService service;

    @Autowired
    private  ProductController controller;

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

        // Simulando el resultado de ejecutar el metodo findAll()
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



}
