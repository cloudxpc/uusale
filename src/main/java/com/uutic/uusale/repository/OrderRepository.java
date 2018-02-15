package com.uutic.uusale.repository;

import com.uutic.uusale.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    @Query(value = "select * from `order` o where exists (select count(1) from order_item oi left join product p on oi.product_id = p.id where oi.order_id=o.id and p.mch_id=?1) order by creation_time desc", nativeQuery = true)
    List<Order> findAllByMchId(String mchId);
    List<Order> findAllByUserIdOrderByCreationTimeDesc(String userId);
}
