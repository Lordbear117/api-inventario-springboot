package com.bear.inventario;

import com.bear.inventario.dto.product.CreateProductDTO;
import com.bear.inventario.dto.product.ProductDTO;
import com.bear.inventario.dto.product.UpdateProductDTO;
import com.bear.inventario.exception.ProductNotFoundException;
import com.bear.inventario.mapper.ProductMapper;
import com.bear.inventario.model.Product;
import com.bear.inventario.repository.ProductRepository;
import com.bear.inventario.service.ProductService;
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
class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Service ProductService should be injected")
    void smokeTest(){
        assertNotNull(productService);
    }

    @Test
    @DisplayName("Service ProductService should return an empty list")
    void testFindAllEmpty() {
        // Arrange
        when(productRepository.findAll()).thenReturn(new LinkedList<>());

        // Act
        List<ProductDTO> result = productService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Service ProductService should find all products")
    void testFindAllProducts() {
        // Arrange
        List<Product> productList = new LinkedList<>();

        Product product1 = new Product();
        product1.setId(1);
        product1.setNombre("Televisor LED");
        product1.setDescripcion("Televisor de 55 pulgadas con resolución 4K");
        product1.setPrecio(1299.99);
        product1.setCantidad(50);
        productList.add(product1);

        Product product2 = new Product();
        product2.setId(2);
        product2.setNombre("Laptop");
        product2.setDescripcion("Laptop con procesador i7 y 16GB de RAM");
        product2.setPrecio(1599.99);
        product2.setCantidad(30);
        productList.add(product2);

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDTO> expectedDTOList = new LinkedList<>();
        for (Product product : productList) {
            ProductDTO dto = new ProductDTO();
            dto.setId(product.getId());
            dto.setNombre(product.getNombre());
            dto.setDescripcion(product.getDescripcion());
            dto.setPrecio(product.getPrecio());
            dto.setCantidad(product.getCantidad());
            expectedDTOList.add(dto);
        }

        when(productMapper.toDTO(productList)).thenReturn(expectedDTOList);

        // Act
        List<ProductDTO> result = productService.findAll();

        // Assert
        assertEquals(expectedDTOList.size(), result.size());
        Set<ProductDTO> uniqueDTOs = new HashSet<>(result);
        assertEquals(result.size(), uniqueDTOs.size());
    }

    @Test
    @DisplayName("Service ProductService should save a new product when valid data is provided")
    void saveProductServiceTest() {
        // Arrange
        CreateProductDTO createData = new CreateProductDTO();
        createData.setNombre("Televisor LED");
        createData.setDescripcion("Televisor de 55 pulgadas con resolución 4K");
        createData.setPrecio(1299.99);
        createData.setCantidad(50);

        Product product = new Product();
        product.setId(1);
        product.setNombre("Televisor LED");
        product.setDescripcion("Televisor de 55 pulgadas con resolución 4K");
        product.setPrecio(1299.99);
        product.setCantidad(50);

        ProductDTO expectedDTO = new ProductDTO();
        expectedDTO.setId(1);
        expectedDTO.setNombre("Televisor LED");
        expectedDTO.setDescripcion("Televisor de 55 pulgadas con resolución 4K");
        expectedDTO.setPrecio(1299.99);
        expectedDTO.setCantidad(50);

        when(productMapper.toModel(createData)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(expectedDTO);

        // Act
        ProductDTO savedProductDTO = productService.save(createData);

        // Assert
        assertNotNull(savedProductDTO);
        assertEquals(product.getId(), savedProductDTO.getId());
        assertEquals(product.getNombre(), savedProductDTO.getNombre());
        assertEquals(product.getDescripcion(), savedProductDTO.getDescripcion());
        assertEquals(product.getPrecio(), savedProductDTO.getPrecio());
        assertEquals(product.getCantidad(), savedProductDTO.getCantidad());
    }

    @Test
    @DisplayName("Service ProductService should update a product")
    void updateProductServiceTest() throws ProductNotFoundException {
        // Arrange
        long productId = 1L;
        UpdateProductDTO updateData = new UpdateProductDTO();
        Product product = new Product();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        productService.update(productId, updateData);

        // Verify
        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, times(1)).update(product, updateData);
        verify(productRepository, times(1)).save(product);
        verifyNoMoreInteractions(productRepository, productMapper);
    }

    @Test
    @DisplayName("Service ProductService exception should work if a product is not found")
    void UpdateProductServiceNotFoundExceptionTest() {
        // Arrange
        long productId = 1L;
        UpdateProductDTO updateData = new UpdateProductDTO();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productService.update(productId, updateData));

        // Verify
        verify(productRepository, times(1)).findById(productId);
        verifyNoMoreInteractions(productRepository, productMapper);
    }

    @Test
    @DisplayName("Service ProductService should delete a product")
    void deleteProductServiceTest() throws ProductNotFoundException {
        // Arrange
        long productId = 1L;
        Product product = new Product();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        productService.delete(productId);

        // Verify
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).deleteById(productId);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("Service ProductService exception should work if a product is not found to delete")
    void deleteProductServiceNotFoundExceptionTest() {
        // Arrange
        long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productService.delete(productId));

        // Verify
        verify(productRepository, times(1)).findById(productId);
        verifyNoMoreInteractions(productRepository);
    }

}
