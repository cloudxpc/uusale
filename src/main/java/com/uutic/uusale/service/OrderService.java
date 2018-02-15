package com.uutic.uusale.service;

import com.uutic.uusale.dto.OrderDto;
import com.uutic.uusale.entity.Order;

import java.util.List;

public interface OrderService {
    Order find(String id);
    List<Order> findAllByUserId(String userId);
    List<Order> findAllByMchId(String mchId);
    String save(OrderDto orderDto);
    void cancel(String id);
}
