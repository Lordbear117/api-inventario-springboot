package com.bear.inventario.repository;

import com.bear.inventario.model.Product;
import com.bear.inventario.model.SupplierProduct;
import com.bear.inventario.model.SupplierProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierProductRepository extends JpaRepository<SupplierProduct, SupplierProductKey> {
    @Query("SELECT i.product FROM SupplierProduct i WHERE i.supplier.id = :supplierId")
    List<Product> findProductsBySupplier(long supplierId);
}
