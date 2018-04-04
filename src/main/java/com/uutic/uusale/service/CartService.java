package com.uutic.uusale.service;

import com.uutic.uusale.dto.OrderDto;
import com.uutic.uusale.dto.OrderItemDto;

import java.util.List;

public interface CartService {
    void save(OrderDto orderDto);
    List<OrderItemDto> get(String userId);
    void clear(String userId);
}
