package com.uutic.uusale.repository;

import com.uutic.uusale.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    Boolean existsByProductId(String productId);
    List<OrderItem> findAllByOrderId(String orderId);
    void deleteAllByOrderId(String orderId);
}
