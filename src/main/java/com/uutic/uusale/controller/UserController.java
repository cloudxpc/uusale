package com.uutic.uusale.controller;

import com.uutic.uusale.dto.UserDto;
import com.uutic.uusale.entity.User;
import com.uutic.uusale.exceptions.CaptchaInvalidException;
import com.uutic.uusale.exceptions.CustomException;
import com.uutic.uusale.service.UserService;
import com.uutic.uusale.util.CaptchaCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestBody UserDto userDto) throws Exception {
        if (StringUtils.isEmpty(userDto.getUsername()) || StringUtils.isEmpty(userDto.getPassword()))
            throw new CustomException("请输入用户名及密码");
        User user = userService.find(userDto);
        if (user == null)
            throw new CustomException("用户名或密码不正确");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
        Object captchaCode = request.getSession().getAttribute("captcha_code");
        if (captchaCode == null || !userDto.getCaptchaCode().trim().toUpperCase().equals(captchaCode.toString()))
            throw new CaptchaInvalidException("验证码错误");
        if (userService.check(userDto.getUsername().trim()))
            throw new CustomException("用户已经存在");

        userService.save(userDto);
    }

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public String captchaCode(HttpServletRequest request) throws IOException {
        CaptchaCodeUtil.CaptchaCodeModel captchaCodeModel = CaptchaCodeUtil.getCode();
        request.getSession().setAttribute("captcha_code", captchaCodeModel.getCaptchaCode());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(captchaCodeModel.getCaptchaImage(), "gif", os);
        String base64 = Base64.getEncoder().encodeToString(os.toByteArray());
        return "data:image/gif;base64," + base64;
    }
}
