package com.uutic.uusale.repository;

import com.uutic.uusale.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, String> {
    List<ProductPrice> findByProductId(String productId);
}
