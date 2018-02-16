package com.uutic.uusale.controller;

import com.uutic.uusale.dto.ProductDto;
import com.uutic.uusale.dto.ProductPriceDto;
import com.uutic.uusale.entity.Merchant;
import com.uutic.uusale.entity.Product;
import com.uutic.uusale.entity.ProductPrice;
import com.uutic.uusale.exceptions.CustomException;
import com.uutic.uusale.service.MerchantService;
import com.uutic.uusale.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductService productService;

    private ProductDto entityToDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImages(StringUtils.isEmpty(product.getImgs()) ? new ArrayList<>() : Arrays.asList(product.getImgs().split(";")));
        productDto.setTimestamp(product.getTimestamp());

        return productDto;
    }

    private ProductPriceDto entityToDto(ProductPrice productPrice){
        ProductPriceDto productPriceDto = new ProductPriceDto();
        productPriceDto.setId(productPrice.getId());
        productPriceDto.setDate(productPrice.getTimestamp());
        productPriceDto.setPrice(productPrice.getPrice());
        return productPriceDto;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ProductDto get(@RequestParam String id){
        Product product = productService.find(id);
        if (product == null)
            throw new CustomException("找不到商品");

        return entityToDto(product);
    }

    @RequestMapping(value = "/mch/list", method = RequestMethod.GET)
    public List<ProductDto> listForMch(HttpServletRequest request) {
        List<Product> products = productService.findByMchId(request.getAttribute("user_id").toString());
        return products.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ProductDto> list() {
        List<Product> products = productService.findAll();
        return products.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request, @RequestBody ProductDto productDto) {
        Merchant merchant = merchantService.find(request.getAttribute("user_id").toString());
        if (merchant == null)
            throw new CustomException("商户不存在");

        productDto.setMchId(merchant.getId());

        return productService.save(productDto);
    }

    @RequestMapping(value = "/price", method = RequestMethod.GET)
    public ProductDto getPriceHistory(@RequestParam String id){
        Product product = productService.find(id);
        if (product == null)
            throw new CustomException("找不到商品");

        ProductDto productDto = entityToDto(product);
        List<ProductPrice> allPrice = productService.findAllPrice(product.getId());
        List<ProductPriceDto> priceDtos = allPrice.stream().map(this::entityToDto).collect(Collectors.toList());
        productDto.setProductPrices(priceDtos);
        return productDto;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, @RequestBody ProductDto productDto) {
        Merchant merchant = merchantService.find(request.getAttribute("user_id").toString());
        if (merchant == null)
            throw new CustomException("商户不存在");

        productService.delete(productDto.getId(), merchant.getId());
    }
}
