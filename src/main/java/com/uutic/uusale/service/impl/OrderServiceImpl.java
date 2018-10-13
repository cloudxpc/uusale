package com.uutic.uusale.service.impl;

import com.uutic.uusale.dto.OrderDto;
import com.uutic.uusale.dto.OrderItemDto;
import com.uutic.uusale.entity.Order;
import com.uutic.uusale.entity.OrderItem;
import com.uutic.uusale.entity.OrderReportItem;
import com.uutic.uusale.exceptions.CustomException;
import com.uutic.uusale.repository.CartRepository;
import com.uutic.uusale.repository.OrderItemRepository;
import com.uutic.uusale.repository.OrderReportItemRepository;
import com.uutic.uusale.repository.OrderRepository;
import com.uutic.uusale.service.OrderService;
import com.uutic.uusale.util.ExcelReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderReportItemRepository orderReportItemRepository;

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    private String generateOrderNo(){
        String prefix = "800";
        String timestamp = formatter.format(Calendar.getInstance().getTime());
        int suffix = ThreadLocalRandom.current().nextInt(100, 999);
        return prefix + timestamp + suffix;
    }

    @Override
    public Order find(String id) {
        return orderRepository.findOne(id);
    }

    @Override
    public List<Order> findAllByUserId(String userId) {
        return orderRepository.findAllByUserIdOrderByCreationTimeDesc(userId);
    }

    @Override
    public List<Order> findAllByMchId(String mchId) {
        return orderRepository.findAllByMchId(mchId);
    }

    @Override
    @Transactional
    public String save(OrderDto orderDto) {
        if (StringUtils.isEmpty(orderDto.getId())) {
            // New
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            order.setOrderNo(generateOrderNo());
            order.setState("A");
            order.setUserId(orderDto.getUserId());
            order.setComments(!StringUtils.isEmpty(orderDto.getComments()) ? orderDto.getComments().trim() : null);
            order.setCreationTime(Calendar.getInstance().getTime());
            List<OrderItem> orderItems = new ArrayList<>();
            if (orderDto.getOrderItems() != null && !orderDto.getOrderItems().isEmpty()) {
                BigDecimal totalAmt = BigDecimal.ZERO;
                for (OrderItemDto orderItemDto : orderDto.getOrderItems()){
                    OrderItem orderItem = new OrderItem();
                    orderItem.setId(UUID.randomUUID().toString());
                    orderItem.setOrderId(order.getId());
                    orderItem.setProductId(orderItemDto.getProductId());
                    orderItem.setUnitPrice(orderItemDto.getUnitPrice());
                    orderItem.setCount(orderItemDto.getCount());
                    orderItems.add(orderItem);
                    totalAmt = totalAmt.add(orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getCount())));
                }
                order.setTotalAmt(totalAmt);
            }
            orderRepository.save(order);
            orderItemRepository.save(orderItems);

            cartRepository.deleteAllByUserId(orderDto.getUserId());

            return order.getId();
        } else {
            // Edit
            Order order = orderRepository.findOne(orderDto.getId());
            if (order == null)
                throw new CustomException("找不到订单");

            order.setComments(orderDto.getComments());

            List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getId());
            List<OrderItem> orderItemsToDelete = orderItems.stream().filter(oi -> orderDto.getOrderItems() == null || orderDto.getOrderItems().stream().noneMatch(oid -> oid.getId().equals(oi.getId()))).collect(Collectors.toList());

            if (orderDto.getOrderItems() != null && !orderDto.getOrderItems().isEmpty()) {
                BigDecimal totalAmt = BigDecimal.ZERO;
                for (OrderItemDto orderItemDto : orderDto.getOrderItems()) {
                    Optional<OrderItem> findOrderItem = orderItems.stream().filter(oi -> oi.getId().equals(orderItemDto.getId())).findFirst();
                    if (findOrderItem.isPresent()) {
                        OrderItem orderItem = findOrderItem.get();
                        orderItem.setCount(orderItemDto.getCount());
                        orderItemRepository.save(orderItem);
                        totalAmt = totalAmt.add(orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getCount())));
                    } else {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setId(UUID.randomUUID().toString());
                        orderItem.setOrderId(order.getId());
                        orderItem.setProductId(orderItemDto.getProductId());
                        orderItem.setUnitPrice(orderItemDto.getUnitPrice());
                        orderItem.setCount(orderItemDto.getCount());
                        orderItemRepository.save(orderItem);
                        totalAmt = totalAmt.add(orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getCount())));
                    }
                }
                order.setTotalAmt(totalAmt);
            }

            orderItemRepository.delete(orderItemsToDelete);
            orderRepository.save(order);

            return order.getId();
        }
    }

    @Override
    @Transactional
    public void cancel(String id) {
        Order order = orderRepository.findOne(id);
        if (order == null)
            throw new CustomException("找不到订单");

        order.setState("C");
        orderRepository.save(order);
    }

    @Override
    public List<OrderItem> findOrderItems(String orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }

    @Override
    @Transactional
    public void delete(String id) {
        orderItemRepository.deleteAllByOrderId(id);
        orderRepository.delete(id);
    }

    @Override
    @Transactional
    public void read(String id, boolean read) {
        Order order = orderRepository.findOne(id);
        if (order == null)
            throw new CustomException("找不到订单");

        order.setState(read ? "R" : "A");
        orderRepository.save(order);
    }

    @Override
    public Integer getUnreadCount(String mchId) {
        return orderRepository.unreadCount(mchId);
    }

    @Override
    public byte[] generateOrderReport(String from, String to) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date dteFrom = dateFormat.parse(from);
        Date dteTo = dateFormat.parse(to);
        List<OrderReportItem> items = orderReportItemRepository.findByDate(dteFrom, dteTo);
        return generateExcelFile(items);
    }

    private byte[] generateExcelFile(List<OrderReportItem> orderReportItems) throws Exception {
        BigDecimal totalAmt = orderReportItems.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, BigDecimal.ROUND_HALF_UP);

        ExcelReport excelReport = new ExcelReport();
        excelReport.createSheet("订单列表").setTitle("订单列表").setColumnHeader(new String[]{
                "订单号", "订单状态", "客户名称", "手机号", "商品名称", "单价", "数量", "日期"
        });

        orderReportItems.forEach(p ->
                excelReport.createRow()
                        .setStringValue(p.getOrderNo())
                        .setStringValue(p.getState())
                        .setStringValue(p.getDisplayName())
                        .setStringValue(p.getPhoneNumber())
                        .setStringValue(p.getName())
                        .setCurrencyValue(p.getUnitPrice())
                        .setStringValue(p.getCount().toString())
                        .setDateTimeValue(p.getCreationTime())
        );

        //Summary line
        excelReport.createRow()
                .setStringValue("合计").span(5)
                .setCurrencyValue(totalAmt).span(3);

        return excelReport.save();
    }
}
