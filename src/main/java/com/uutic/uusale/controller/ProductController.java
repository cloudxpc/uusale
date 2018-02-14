package com.uutic.uusale.controller;

import com.uutic.uusale.dto.ProductDto;
import com.uutic.uusale.entity.Merchant;
import com.uutic.uusale.entity.Product;
import com.uutic.uusale.exceptions.CustomException;
import com.uutic.uusale.service.MerchantService;
import com.uutic.uusale.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductService productService;

    @RequestMapping("")
    public ProductDto get(@RequestParam String id){
        Product product = productService.find(id);
        if (product == null)
            throw new CustomException("找不到商品");

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImages(StringUtils.isEmpty(product.getImgs()) ? new ArrayList<>() : Arrays.asList(product.getImgs().split(";")));

        return productDto;
    }

    @RequestMapping("/list")
    public String list(HttpServletRequest request) {
        Object user_id = request.getAttribute("user_id");
        Object user_type = request.getAttribute("user_type");
        return user_id + ":" + user_type;
    }

    @RequestMapping("/save")
    public String save(HttpServletRequest request, @RequestBody ProductDto productDto) {
        Merchant merchant = merchantService.find(request.getAttribute("user_id").toString());
        if (merchant == null)
            throw new CustomException("商户不存在");

        productDto.setMchId(merchant.getId());

        return productService.save(productDto);
    }
}
