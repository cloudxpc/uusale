package com.uutic.uusale.repository;

import com.uutic.uusale.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String>{
    List<Product> findByMchIdOrderByTimestampDesc(String mchId);
    Product findByIdAndMchId(String id, String mchId);
}
