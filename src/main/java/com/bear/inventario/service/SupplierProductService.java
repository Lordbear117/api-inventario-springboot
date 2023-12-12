package com.bear.inventario.service;

import com.bear.inventario.mapper.ProductMapper;
import com.bear.inventario.mapper.SupplierProductMapper;
import com.bear.inventario.repository.SupplierProductRepository;
import com.bear.inventario.dto.product.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierProductService {

    @Autowired
    private SupplierProductRepository repository;

    @Autowired
    private SupplierProductMapper mapper;

    @Autowired
    private ProductMapper productMapper;

    public void addProduct(long supplierId, long productId) {
        repository.save(mapper.toModel(supplierId, productId));
    }

    public List<ProductDTO> findProductsBySupplier(long supplierId) {
        return productMapper.toDTO(repository.findProductsBySupplier(supplierId));
    }
}
