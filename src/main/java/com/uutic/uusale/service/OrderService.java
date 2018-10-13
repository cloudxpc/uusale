package com.uutic.uusale.service;

import com.uutic.uusale.dto.OrderDto;
import com.uutic.uusale.entity.Order;
import com.uutic.uusale.entity.OrderItem;

import java.util.List;

public interface OrderService {
    Order find(String id);
    List<Order> findAllByUserId(String userId);
    List<Order> findAllByMchId(String mchId);
    String save(OrderDto orderDto);
    void cancel(String id);
    List<OrderItem> findOrderItems(String orderId);
    void delete(String id);
    void read(String id, boolean read);
    Integer getUnreadCount(String mchId);
    byte[] generateOrderReport(String from, String to) throws Exception;
}
