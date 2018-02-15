package com.uutic.uusale.controller;

import com.uutic.uusale.dto.OrderDto;
import com.uutic.uusale.dto.OrderItemDto;
import com.uutic.uusale.entity.Merchant;
import com.uutic.uusale.entity.Order;
import com.uutic.uusale.entity.OrderItem;
import com.uutic.uusale.exceptions.CustomException;
import com.uutic.uusale.service.MerchantService;
import com.uutic.uusale.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    private OrderItemDto entityToDto(OrderItem orderItem){
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setOrderId(orderItem.getOrderId());
        orderItemDto.setProductId(orderItem.getProductId());
        orderItemDto.setUnitPrice(orderItem.getUnitPrice());
        orderItemDto.setCount(orderItem.getCount());
        return orderItemDto;
    }

    @RequestMapping(value = "/mch/list", method = RequestMethod.GET)
    private List<OrderDto> mchOrders(HttpServletRequest request){
        Merchant merchant = merchantService.find(request.getAttribute("user_id").toString());
        if (merchant == null)
            throw new CustomException("商户不存在");

        List<Order> orders = orderService.findAllByMchId(merchant.getId());
        return orders.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    private String save(HttpServletRequest request, @RequestBody OrderDto orderDto){
        orderDto.setUserId(request.getAttribute("user_id").toString());
        return orderService.save(orderDto);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    private void cancel(HttpServletRequest request, @RequestBody OrderDto orderDto){
        orderService.cancel(orderDto.getId());
    }
}
