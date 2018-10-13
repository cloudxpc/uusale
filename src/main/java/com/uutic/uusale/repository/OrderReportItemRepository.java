package com.uutic.uusale.repository;

import com.uutic.uusale.entity.OrderReportItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderReportItemRepository extends JpaRepository<OrderReportItem, String> {
    @Query(value = "SELECT " +
            "   oi.id, " +
            "    o.order_no, " +
            "    o.state, " +
            "    p.name, " +
            "    oi.unit_price, " +
            "    oi.count, " +
            "    o.creation_time, " +
            "    u.display_name, " +
            "    u.phone_number " +
            "FROM `order_item` oi " +
            "left join product p on oi.product_id = p.id " +
            "left join `order` o on oi.order_id = o.id " +
            "left join `user` u on o.user_id = u.id " +
            "where o.creation_time >= ?1 and o.creation_time <= ?2 " +
            "order by o.creation_time ", nativeQuery = true)
    List<OrderReportItem> findByDate(Date from, Date to);
}
