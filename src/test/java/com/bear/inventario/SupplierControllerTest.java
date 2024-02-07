package com.bear.inventario;

import com.bear.inventario.controller.SupplierController;
import com.bear.inventario.dto.product.AddProductDTO;
import com.bear.inventario.dto.product.ProductDTO;
import com.bear.inventario.dto.supplier.CreateSupplierDTO;
import com.bear.inventario.dto.supplier.SupplierDTO;
import com.bear.inventario.dto.supplier.UpdateSupplierDTO;
import com.bear.inventario.exception.SupplierNotFoundException;
import com.bear.inventario.service.SupplierProductService;
import com.bear.inventario.service.SupplierService;
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
class SupplierControllerTest {

    @MockBean
    private SupplierService supplierService;

    @MockBean
    private SupplierProductService supplierProductService;

    @InjectMocks
    private SupplierController controller;

    @Autowired
    public SupplierControllerTest (SupplierService supplierService, SupplierProductService supplierProductService,
                                   SupplierController controller) {
        this.supplierService = supplierService;
        this.supplierProductService = supplierProductService;
        this.controller = controller;
    }

    @Test
    @DisplayName("Controller Supplier should be injected")
    void smokeTest(){
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Controller should return a list of supplier")
    void findAllSuppliersTest(){
        // Arrange
        List<SupplierDTO> fakeData = new LinkedList<>();

        SupplierDTO fakeSupplier = new SupplierDTO();

        fakeSupplier.setId(1);
        fakeSupplier.setNombre("Lenovo");
        fakeSupplier.setDireccion("Calle Patria 3002");
        fakeSupplier.setContacto("lenovo@gmail.com");

        fakeData.add(fakeSupplier);

        // Mimic call of findAll() method
        when(supplierService.findAll()).thenReturn(fakeData);

        // Act
        List<SupplierDTO> result = controller.findAll();

        // Assert
        assertEquals(fakeData, result);

    }

    @Test
    @DisplayName("Controller should save a supplier")
    void saveSupplierTest(){
        // Arrange
        CreateSupplierDTO dto = new CreateSupplierDTO();

        dto.setNombre("Lenovo");
        dto.setDireccion("Calle Patria 3002");
        dto.setContacto("lenovo@gmail.com");

        SupplierDTO saved = new SupplierDTO();

        saved.setId(1);
        saved.setNombre("Lenovo");
        saved.setDireccion("Calle Patria 3002");
        saved.setContacto("lenovo@gmail.com");

        when(supplierService.save(any(CreateSupplierDTO.class))).thenReturn(saved);

        // Act
        SupplierDTO result = controller.save(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.getNombre(), result.getNombre());
        assertEquals(dto.getContacto(), result.getContacto());
        assertEquals(dto.getDireccion(), result.getDireccion());
    }

    @Test
    @DisplayName("Controller should update a supplier")
    void updateSupplierTest() throws SupplierNotFoundException {
        // Arrange
        long supplierId = 1L;
        UpdateSupplierDTO updateData = new UpdateSupplierDTO();

        updateData.setNombre("Lenovo");
        updateData.setDireccion("Calle Patria 3002");
        updateData.setContacto("lenovo@gmail.com");

        // Act
        controller.update(supplierId, updateData);

        // Assert
        // verify that supplierService.update was called with valid args
        verify(supplierService).update(supplierId, updateData);
    }

    @Test
    @DisplayName("Controller exception should work if supplier is not found")
    void updateSupplierNotFoundExceptionTest() throws SupplierNotFoundException {
        // Arrange
        long supplierId = 2L;
        UpdateSupplierDTO updateData = new UpdateSupplierDTO();
        doThrow(new SupplierNotFoundException(supplierId)).when(supplierService).update(supplierId, updateData);

        // Act & Assert
        assertThrows(SupplierNotFoundException.class, () -> {
            controller.update(supplierId, updateData);
        });
    }

    @Test
    @DisplayName("Controller should handle supplier deletion")
    void deleteSupplierTest() throws SupplierNotFoundException {
        // Arrange
        long supplierId = 2L;
        doNothing().when(supplierService).delete(supplierId);

        // Act & Assert
        assertDoesNotThrow(() -> {
            controller.delete(supplierId);
        });

        // Verify that the delete method was called with the specified supplierId
        verify(supplierService, times(1)).delete(supplierId);
    }

    @Test
    @DisplayName("Controller exception should work if a supplier is not found during deletion")
    void deleteSupplierNotFoundExceptionTest() throws SupplierNotFoundException {
        // Arrange
        long supplierId = 2L;
        doThrow(new SupplierNotFoundException(supplierId)).when(supplierService).delete(supplierId);

        // Act & Assert
        assertThrows(SupplierNotFoundException.class, () -> {
            controller.delete(supplierId);
        });

        // Verify that the delete method was called with the specified supplierId
        verify(supplierService, times(1)).delete(supplierId);
    }

    @Test
    @DisplayName("Controller should handle adding product to supplier")
    void addProductToSupplierTest() {
        // Arrange
        long supplierId = 1L;
        AddProductDTO addProductDTO = new AddProductDTO();

        addProductDTO.setProductId(2L);

        // Act & Assert
        assertDoesNotThrow(() -> {
            controller.addProduct(supplierId, addProductDTO);
        });

        // Verify that the addProduct method was called with the specified supplierId and productId
        verify(supplierProductService, times(1)).addProduct(supplierId, addProductDTO.getProductId());
    }

    @Test
    @DisplayName("Controller should return products for a given supplier")
    void findProductsBySupplierTest() {
        // Arrange
        long supplierId = 1L;
        List<ProductDTO> fakeProducts = new LinkedList<>();

        ProductDTO fakeProduct = new ProductDTO();

        fakeProduct.setId(1);
        fakeProduct.setNombre("TV Samsung 65");
        fakeProduct.setDescripcion("Black Friday Sale");
        fakeProduct.setCantidad(50);
        fakeProduct.setPrecio(12000);

        fakeProducts.add(fakeProduct);

        when(supplierProductService.findProductsBySupplier(supplierId)).thenReturn(fakeProducts);

        // Act
        List<ProductDTO> result = controller.findProductsBySupplier(supplierId);

        // Assert
        assertEquals(fakeProducts, result);
    }

}
