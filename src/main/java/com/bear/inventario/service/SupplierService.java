package com.bear.inventario.service;

import java.util.List;
import java.util.Optional;

import com.bear.inventario.exception.SupplierNotFoundException;
import com.bear.inventario.dto.supplier.CreateSupplierDTO;
import com.bear.inventario.dto.supplier.SupplierDTO;
import com.bear.inventario.dto.supplier.UpdateSupplierDTO;
import com.bear.inventario.mapper.SupplierMapper;
import com.bear.inventario.model.Supplier;
import com.bear.inventario.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository repository;

    @Autowired
    private SupplierMapper mapper;

    public List<SupplierDTO> findAll() {
        return mapper.toDTO(repository.findAll());
    }

    public SupplierDTO save(CreateSupplierDTO data) {
        Supplier entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }

    public void update(long supplierId, UpdateSupplierDTO data) throws SupplierNotFoundException {
        Optional<Supplier> result = repository.findById(supplierId);

        if (!result.isPresent()) {
            throw new SupplierNotFoundException(supplierId);
        }

        Supplier supplier = result.get();

        // Aplicar los cambios al proveedor
        mapper.update(supplier, data);

        repository.save(supplier);
    }

    public void delete(long supplierId) throws SupplierNotFoundException {
        Optional<Supplier> result = repository.findById(supplierId);

        if (!result.isPresent()) {
            throw new SupplierNotFoundException(supplierId);
        } else {
            repository.deleteById(supplierId);
        }
    }
}
