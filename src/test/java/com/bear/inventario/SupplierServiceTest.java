package com.bear.inventario;

import com.bear.inventario.dto.supplier.CreateSupplierDTO;
import com.bear.inventario.dto.supplier.SupplierDTO;
import com.bear.inventario.dto.supplier.UpdateSupplierDTO;
import com.bear.inventario.exception.SupplierNotFoundException;
import com.bear.inventario.mapper.SupplierMapper;
import com.bear.inventario.model.Supplier;
import com.bear.inventario.repository.SupplierRepository;
import com.bear.inventario.service.SupplierService;
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
class SupplierServiceTest {

    @MockBean
    private SupplierRepository supplierRepository;

    @MockBean
    private SupplierMapper supplierMapper;

    @InjectMocks
    private SupplierService supplierService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Service SupplierService should be injected")
    void smokeTest(){
        assertNotNull(supplierService);
    }

    @Test
    @DisplayName("Service SupplierService should return an empty list")
    void testFindAllEmpty() {
        // Arrange
        when(supplierRepository.findAll()).thenReturn(new LinkedList<>());

        // Act
        List<SupplierDTO> result = supplierService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Service SupplierService should find all suppliers")
    void findAllSupplierServiceTest() {
        // Arrange
        Supplier supplier1 = new Supplier();
        supplier1.setId(1);
        supplier1.setNombre("ProveedorXYZ");
        supplier1.setContacto("contacto@proveedor.com");
        supplier1.setDireccion("Calle Principal #123");

        Supplier supplier2 = new Supplier();
        supplier2.setId(2);
        supplier2.setNombre("ProveedorABC");
        supplier2.setContacto("contacto@proveedor.com");
        supplier2.setDireccion("Calle Secundaria #456");

        List<Supplier> suppliers = new LinkedList<>();
        suppliers.add(supplier1);
        suppliers.add(supplier2);

        SupplierDTO supplierDTO1 = new SupplierDTO();
        supplierDTO1.setId(1);
        supplierDTO1.setNombre("ProveedorXYZ");
        supplierDTO1.setContacto("contacto@proveedor.com");
        supplierDTO1.setDireccion("Calle Principal #123");

        SupplierDTO supplierDTO2 = new SupplierDTO();
        supplierDTO2.setId(2);
        supplierDTO2.setNombre("ProveedorABC");
        supplierDTO2.setContacto("contacto@proveedor.com");
        supplierDTO2.setDireccion("Calle Secundaria #456");

        List<SupplierDTO> expectedDTOList = new LinkedList<>();
        expectedDTOList.add(supplierDTO1);
        expectedDTOList.add(supplierDTO2);

        when(supplierRepository.findAll()).thenReturn(suppliers);
        when(supplierMapper.toDTO(suppliers)).thenReturn(expectedDTOList);

        // Act
        List<SupplierDTO> actualDTOList = supplierService.findAll();

        // Assert
        assertEquals(expectedDTOList.size(), actualDTOList.size());
        assertEquals(expectedDTOList, actualDTOList);

        // Verify
        verify(supplierRepository, times(1)).findAll();
        verify(supplierMapper, times(1)).toDTO(suppliers);
        verifyNoMoreInteractions(supplierRepository, supplierMapper);
    }

    @Test
    @DisplayName("Service SupplierService should save a new supplier when valid data is provided")
    void saveNewSupplierTest() {
        // Arrange
        CreateSupplierDTO createSupplierDTO = new CreateSupplierDTO();
        createSupplierDTO.setNombre("ProveedorXYZ");
        createSupplierDTO.setContacto("contacto@proveedor.com");
        createSupplierDTO.setDireccion("Calle Principal #123");

        Supplier entity = new Supplier();
        entity.setId(1);
        entity.setNombre("ProveedorXYZ");
        entity.setContacto("contacto@proveedor.com");
        entity.setDireccion("Calle Principal #123");

        SupplierDTO expectedDTO = new SupplierDTO();
        expectedDTO.setId(1);
        expectedDTO.setNombre("ProveedorXYZ");
        expectedDTO.setContacto("contacto@proveedor.com");
        expectedDTO.setDireccion("Calle Principal #123");

        when(supplierMapper.toModel(createSupplierDTO)).thenReturn(entity);
        when(supplierRepository.save(entity)).thenReturn(entity);
        when(supplierMapper.toDTO(entity)).thenReturn(expectedDTO);

        // Act
        SupplierDTO actualDTO = supplierService.save(createSupplierDTO);

        // Assert
        assertNotNull(actualDTO);
        assertEquals(expectedDTO.getId(), actualDTO.getId());
        assertEquals(expectedDTO.getNombre(), actualDTO.getNombre());
        assertEquals(expectedDTO.getContacto(), actualDTO.getContacto());
        assertEquals(expectedDTO.getDireccion(), actualDTO.getDireccion());

        // Verify
        verify(supplierRepository, times(1)).save(entity);
        verify(supplierMapper, times(1)).toModel(createSupplierDTO);
        verify(supplierMapper, times(1)).toDTO(entity);
    }

    @Test
    @DisplayName("Service SupplierService should update a supplier")
    void updateSupplierServiceTest() throws SupplierNotFoundException {
        // Arrange
        long supplierId = 1L;
        UpdateSupplierDTO updateSupplierDTO = new UpdateSupplierDTO();
        updateSupplierDTO.setNombre("NuevoNombre");
        updateSupplierDTO.setContacto("nuevo@proveedor.com");
        updateSupplierDTO.setDireccion("Nueva Dirección");

        Supplier existingSupplier = new Supplier();
        existingSupplier.setId((int) supplierId);
        existingSupplier.setNombre("ProveedorExistente");
        existingSupplier.setContacto("contacto@proveedor.com");
        existingSupplier.setDireccion("Calle Principal #123");

        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(existingSupplier));

        // Act
        supplierService.update(supplierId, updateSupplierDTO);

        // Assert
        verify(supplierRepository, times(1)).findById(supplierId);
        verify(supplierMapper, times(1)).update(existingSupplier, updateSupplierDTO);
        verify(supplierRepository, times(1)).save(existingSupplier);
        verifyNoMoreInteractions(supplierRepository, supplierMapper);
    }

    @Test
    @DisplayName("Service SupplierService exception should work if a supplier is not found")
    void updateSupplierServiceNotFoundExceptionTest() {
        // Arrange
        long nonExistingSupplierId = 999L;
        UpdateSupplierDTO updateSupplierDTO = new UpdateSupplierDTO();
        updateSupplierDTO.setNombre("NuevoNombre");
        updateSupplierDTO.setContacto("nuevo@proveedor.com");
        updateSupplierDTO.setDireccion("Nueva Dirección");

        when(supplierRepository.findById(nonExistingSupplierId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SupplierNotFoundException.class, () -> supplierService.update(nonExistingSupplierId, updateSupplierDTO));

        // Verify
        verify(supplierRepository, times(1)).findById(nonExistingSupplierId);
        verifyNoMoreInteractions(supplierRepository, supplierMapper);
    }

    @Test
    @DisplayName("Service SupplierService should delete a supplier")
    void deleteSupplierServiceTest() throws SupplierNotFoundException {
        // Arrange
        long supplierId = 1L;

        Supplier existingSupplier = new Supplier();
        existingSupplier.setId((int) supplierId);

        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(existingSupplier));

        // Act
        supplierService.delete(supplierId);

        // Assert
        verify(supplierRepository, times(1)).findById(supplierId);
        verify(supplierRepository, times(1)).deleteById(supplierId);
        verifyNoMoreInteractions(supplierRepository);
    }

    @Test
    @DisplayName("Service SupplierService exception should work if a supplier is not found to delete")
    void deleteSupplierServiceNotFoundExceptionTest() {
        // Arrange
        long nonExistingSupplierId = 999L;

        when(supplierRepository.findById(nonExistingSupplierId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SupplierNotFoundException.class, () -> supplierService.delete(nonExistingSupplierId));

        // Verify
        verify(supplierRepository, times(1)).findById(nonExistingSupplierId);
        verifyNoMoreInteractions(supplierRepository);
    }

}
