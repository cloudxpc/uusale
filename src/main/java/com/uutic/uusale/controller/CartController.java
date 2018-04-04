package com.uutic.uusale.controller;

import com.uutic.uusale.dto.OrderDto;
import com.uutic.uusale.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/save")
    public void Save(HttpServletRequest request, @RequestBody OrderDto orderDto) {
        orderDto.setUserId(request.getAttribute("user_id").toString());
        cartService.save(orderDto);
    }
}
