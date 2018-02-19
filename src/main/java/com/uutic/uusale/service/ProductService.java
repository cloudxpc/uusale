package com.uutic.uusale.service;

import com.uutic.uusale.dto.ProductDto;
import com.uutic.uusale.entity.Product;
import com.uutic.uusale.entity.ProductPrice;

import java.util.List;

public interface ProductService {
    Product find(String id);
    Product find(String id, String mchId);
    List<Product> findByMchId(String mchId);
    List<Product> findAll();
    String save(ProductDto productDto);
    List<ProductPrice> findAllPrice(String id);
    void delete(String id, String mchId);
    void shelve(String id);
}
