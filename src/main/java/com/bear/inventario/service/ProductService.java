package com.bear.inventario.service;

import java.util.List;
import java.util.Optional;

import com.bear.inventario.dto.product.CreateProductDTO;
import com.bear.inventario.dto.product.ProductDTO;
import com.bear.inventario.dto.product.UpdateProductDTO;
import com.bear.inventario.exception.ProductNotFoundException;
import com.bear.inventario.mapper.ProductMapper;
import com.bear.inventario.model.Product;
import com.bear.inventario.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    public List<ProductDTO> findAll() {
        return mapper.toDTO(repository.findAll());
    }

    public ProductDTO save(CreateProductDTO data) {
        Product entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }

    public void update(long productId, UpdateProductDTO data) throws ProductNotFoundException {
        Optional<Product> result = repository.findById(productId);

        if (!result.isPresent()) {
            throw new ProductNotFoundException(productId);
        }

        Product product = result.get();

        // Aplicar los cambios al artista
        mapper.update(product, data);

        repository.save(product);
    }

    public void delete(long productId) throws ProductNotFoundException {
        Optional<Product> result = repository.findById(productId);

        if (!result.isPresent()) {
            throw new ProductNotFoundException(productId);
        } else {
            repository.deleteById(productId);
        }
    }

}

