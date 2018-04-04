package com.uutic.uusale.service.impl;

import com.uutic.uusale.dto.OrderDto;
import com.uutic.uusale.dto.OrderItemDto;
import com.uutic.uusale.entity.Cart;
import com.uutic.uusale.entity.Product;
import com.uutic.uusale.repository.CartRepository;
import com.uutic.uusale.repository.ProductRepository;
import com.uutic.uusale.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public void save(OrderDto orderDto) {
        cartRepository.deleteAllByUserId(orderDto.getUserId());

        List<Cart> carts = new ArrayList<>();
        if (orderDto.getOrderItems() != null && !orderDto.getOrderItems().isEmpty()) {
            for (OrderItemDto orderItemDto : orderDto.getOrderItems()){
                Cart cart = new Cart();
                cart.setId(UUID.randomUUID().toString());
                cart.setUserId(orderDto.getUserId());
                cart.setProductId(orderItemDto.getProductId());
                cart.setCount(orderItemDto.getCount());
                carts.add(cart);
            }
        }
        cartRepository.save(carts);
    }

    @Override
    public List<OrderItemDto> get(String userId) {
        List<Cart> carts = cartRepository.findAllByUserId(userId);
        return carts.stream().map(c -> {
            Product product = productRepository.findOne(c.getProductId());
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setProductId(product.getId());
            orderItemDto.setProductName(product.getName());
            orderItemDto.setUnitPrice(product.getPrice());
            orderItemDto.setCount(c.getCount());
            return orderItemDto;
        }).collect(Collectors.toList());
    }

    @Override
    public void clear(String userId) {
        cartRepository.deleteAllByUserId(userId);
    }
}
