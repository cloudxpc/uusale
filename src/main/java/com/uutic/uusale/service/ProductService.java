package com.uutic.uusale.service;

import com.uutic.uusale.dto.ProductDto;
import com.uutic.uusale.entity.Product;

public interface ProductService {
    Product find(String id);
    Product find(String id, String mchId);
    String save(ProductDto productDto);
}
