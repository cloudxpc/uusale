package com.uutic.uusale.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @RequestMapping("/list")
    public String list(HttpServletRequest request) {
        Object user_id = request.getAttribute("user_id");
        Object user_type = request.getAttribute("user_type");
        return user_id + ":" + user_type;
    }
}
