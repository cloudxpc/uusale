package com.uutic.uusale.service.impl;

import com.uutic.uusale.dto.ProductDto;
import com.uutic.uusale.entity.Product;
import com.uutic.uusale.entity.ProductPrice;
import com.uutic.uusale.exceptions.CustomException;
import com.uutic.uusale.repository.OrderItemRepository;
import com.uutic.uusale.repository.ProductPriceRepository;
import com.uutic.uusale.repository.ProductRepository;
import com.uutic.uusale.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPriceRepository productPriceRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Product find(String id) {
        return productRepository.findOne(id);
    }

    @Override
    public Product find(String id, String mchId) {
        return productRepository.findByIdAndMchId(id, mchId);
    }

    @Override
    public List<Product> findByMchId(String mchId) {
        return productRepository.findByMchIdOrderByTimestampDesc(mchId);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAllByStateOrderByTimestampDesc("A");
    }

    @Override
    @Transactional
    public String save(ProductDto productDto) {
        if (StringUtils.isEmpty(productDto.getId())) {
            // New
            Product product = new Product();
            product.setId(UUID.randomUUID().toString());
            product.setMchId(productDto.getMchId().trim());
            product.setName(productDto.getName().trim());
            product.setDescription(StringUtils.isEmpty(productDto.getDescription()) ? null : productDto.getDescription().trim());
            product.setPrice(productDto.getPrice());
            product.setState("A");
            product.setImgs(productDto.getImages() != null && !productDto.getImages().isEmpty() ? String.join(";", productDto.getImages()) : null);
            productRepository.save(product);

            ProductPrice productPrice = new ProductPrice();
            productPrice.setId(UUID.randomUUID().toString());
            productPrice.setProductId(product.getId());
            productPrice.setPrice(product.getPrice());
            productPriceRepository.save(productPrice);

            return product.getId();
        } else {
            // Edit
            Product product = productRepository.findByIdAndMchId(productDto.getId().trim(), productDto.getMchId().trim());
            if (product == null)
                throw new CustomException("找不到商品");
            product.setName(productDto.getName().trim());
            product.setDescription(StringUtils.isEmpty(productDto.getDescription()) ? null : productDto.getDescription().trim());
            BigDecimal oldPrice = product.getPrice();
            product.setPrice(productDto.getPrice());
            product.setImgs(productDto.getImages() != null && !productDto.getImages().isEmpty() ? String.join(";", productDto.getImages()) : null);
            productRepository.save(product);

            if (product.getPrice().compareTo(oldPrice) != 0) {
                ProductPrice productPrice = new ProductPrice();
                productPrice.setId(UUID.randomUUID().toString());
                productPrice.setProductId(product.getId());
                productPrice.setPrice(product.getPrice());
                productPriceRepository.save(productPrice);
            }

            return product.getId();
        }
    }

    @Override
    public List<ProductPrice> findAllPrice(String id) {
        return productPriceRepository.findByProductId(id);
    }

    @Override
    @Transactional
    public void delete(String id, String mchId) {
        Product product = productRepository.findByIdAndMchId(id, mchId);
        if (product == null)
            throw new CustomException("找不到商品");
        Boolean existsByProductId = orderItemRepository.existsByProductId(product.getId());
        if (existsByProductId)
            throw new CustomException("系统存在关联到此商品的订单, 不能删除");

        productPriceRepository.deleteAllByProductId(product.getId());
        productRepository.delete(product);
    }

    @Override
    @Transactional
    public void shelve(String id) {
        Product product = productRepository.findOne(id);
        if (product == null)
            throw new CustomException("找不到商品");
        if (product.getState().equals("A"))
            product.setState("U");
        else if (product.getState().equals("U"))
            product.setState("A");
        productRepository.save(product);
    }
}
