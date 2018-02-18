package com.uutic.uusale.controller;

import com.uutic.uusale.exceptions.CustomException;
import com.uutic.uusale.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPriceRepository productPriceRepository;

    @RequestMapping("/admin/clear/{pwd}")
    @Transactional
    public String ClearDatabase(@PathVariable String pwd) {
        if (!pwd.equals("666"))
            throw new CustomException("No can do");

        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        productPriceRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
        merchantRepository.deleteAll();

        return "OK";
    }
}
