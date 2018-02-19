package com.uutic.uusale.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ProductDto {
    private String id;
    private String mchId;
    private String name;
    private String description;
    private BigDecimal price;
    private String state;
    private List<String> images;
    private List<ProductPriceDto> productPrices;
    private Date timestamp;

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<ProductPriceDto> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(List<ProductPriceDto> productPrices) {
        this.productPrices = productPrices;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
