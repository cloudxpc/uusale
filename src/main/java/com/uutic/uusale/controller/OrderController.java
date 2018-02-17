package com.uutic.uusale.controller;

import com.uutic.uusale.dto.OrderDto;
import com.uutic.uusale.dto.OrderItemDto;
import com.uutic.uusale.entity.*;
import com.uutic.uusale.exceptions.CustomException;
import com.uutic.uusale.service.MerchantService;
import com.uutic.uusale.service.OrderService;
import com.uutic.uusale.service.ProductService;
import com.uutic.uusale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    private OrderDto entityToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setOrderNo(order.getOrderNo());
        orderDto.setState(order.getState());
        orderDto.setUserId(order.getUserId());
        orderDto.setTotalAmt(order.getTotalAmt());
        orderDto.setComments(order.getComments());
        orderDto.setCreationTime(order.getCreationTime());
        orderDto.setLastUpdateTime(order.getLastUpdateTime());
        return orderDto;
    }

    private OrderItemDto entityToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setProductId(orderItem.getProductId());
        orderItemDto.setUnitPrice(orderItem.getUnitPrice());
        orderItemDto.setCount(orderItem.getCount());
        return orderItemDto;
    }

    @RequestMapping(value = "/mch/list", method = RequestMethod.GET)
    public List<OrderDto> mchOrders(HttpServletRequest request) {
        Merchant merchant = merchantService.find(request.getAttribute("user_id").toString());
        if (merchant == null)
            throw new CustomException("商户不存在");

        List<Order> orders = orderService.findAllByMchId(merchant.getId());
        List<OrderDto> orderDtos = orders.stream().map(this::entityToDto).collect(Collectors.toList());
        orderDtos.forEach(o -> {
            User user = userService.find(o.getUserId());
            o.setUserName(user.getUsername());
            o.setUserDisplayName(user.getDisplayName());
            o.setUserPhoneNumber(user.getPhoneNumber());
        });

        return orderDtos;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<OrderDto> orders(HttpServletRequest request) {
        User user = userService.find(request.getAttribute("user_id").toString());
        if (user == null)
            throw new CustomException("用户不存在");

        List<Order> orders = orderService.findAllByUserId(user.getId());
        List<OrderDto> orderDtos = orders.stream().map(this::entityToDto).collect(Collectors.toList());
        orderDtos.forEach(o -> {
            o.setUserName(user.getUsername());
            o.setUserDisplayName(user.getDisplayName());
            o.setUserPhoneNumber(user.getPhoneNumber());
        });

        return orderDtos;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request, @RequestBody OrderDto orderDto) {
        orderDto.setUserId(request.getAttribute("user_id").toString());
        return orderService.save(orderDto);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public void cancel(HttpServletRequest request, @RequestBody OrderDto orderDto) {
        orderService.cancel(orderDto.getId());
    }

    @RequestMapping("")
    public OrderDto get(@RequestParam String id) {
        Order order = orderService.find(id);
        if (order == null)
            throw new CustomException("找不到订单");
        List<OrderItem> orderItems = orderService.findOrderItems(order.getId());

        OrderDto orderDto = entityToDto(order);
        User user = userService.find(orderDto.getUserId());
        orderDto.setUserName(user.getUsername());
        orderDto.setUserDisplayName(user.getDisplayName());
        orderDto.setUserPhoneNumber(user.getPhoneNumber());

        if (orderItems != null)
            orderDto.setOrderItems(orderItems.stream().map(orderItem -> {
                Product product = productService.find(orderItem.getProductId());
                OrderItemDto orderItemDto = entityToDto(orderItem);
                orderItemDto.setProductName(product.getName());
                return orderItemDto;
            }).collect(Collectors.toList()));

        return orderDto;
    }
}
