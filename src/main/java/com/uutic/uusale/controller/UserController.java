package com.uutic.uusale.controller;

import com.uutic.uusale.dto.UserDto;
import com.uutic.uusale.entity.User;
import com.uutic.uusale.exceptions.CustomException;
import com.uutic.uusale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestBody UserDto userDto) throws Exception {
        if (userDto.getUsername() == null || userDto.getPassword() == null)
            throw new CustomException("请输入用户名及密码");
        User user = userService.find(userDto);
        if (user == null)
            throw new CustomException("用户名或密码不正确");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody UserDto userDto) throws Exception {
        if (!userDto.getPassword().trim().equals(userDto.getPasswordConfirm().trim()))
            throw new CustomException("两次密码输入不一致");
        if (userService.check(userDto.getUsername().trim()))
            throw new CustomException("用户已经存在");

        userService.save(userDto);
    }
}
