package com.uutic.uusale.controller;

import com.uutic.uusale.dto.LoginResultDto;
import com.uutic.uusale.dto.UserDto;
import com.uutic.uusale.entity.Merchant;
import com.uutic.uusale.entity.User;
import com.uutic.uusale.exceptions.CaptchaInvalidException;
import com.uutic.uusale.exceptions.CustomException;
import com.uutic.uusale.service.MerchantService;
import com.uutic.uusale.service.OrderService;
import com.uutic.uusale.service.UserService;
import com.uutic.uusale.util.CaptchaCodeUtil;
import com.uutic.uusale.util.JwtUtil;
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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResultDto login(@RequestBody UserDto userDto) throws Exception {
        if (StringUtils.isEmpty(userDto.getUsername()) || StringUtils.isEmpty(userDto.getPassword()))
            throw new CustomException("请输入用户名及密码");
        User user = userService.find(userDto);
        if (user != null) {
            Map<String, String> claims = new HashMap<>();
            claims.put("user_id", user.getId());
            claims.put("user_type", "U");
            String token = JwtUtil.encode(claims);
            LoginResultDto loginResultDto = new LoginResultDto();
            loginResultDto.setUserType("U");
            loginResultDto.setToken(token);
            loginResultDto.setPhoneNumber(user.getPhoneNumber());
            loginResultDto.setUserDisplayName(user.getDisplayName());
            loginResultDto.setUsername(user.getUsername());
            return loginResultDto;
        }

        Merchant merchant = merchantService.find(userDto);
        if (merchant != null){
            Map<String, String> claims = new HashMap<>();
            claims.put("user_id", merchant.getId());
            claims.put("user_type", "M");
            String token = JwtUtil.encode(claims);
            LoginResultDto loginResultDto = new LoginResultDto();
            loginResultDto.setUserType("M");
            loginResultDto.setToken(token);
            loginResultDto.setPhoneNumber(merchant.getPhoneNumber());
            loginResultDto.setUserDisplayName(merchant.getDisplayName());
            loginResultDto.setUsername(merchant.getUsername());
            loginResultDto.setUnreadCount(orderService.getUnreadCount(merchant.getId()));

            return loginResultDto;
        }

        throw new CustomException("用户名或密码不正确");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
        Object captchaCode = request.getSession().getAttribute("captcha_code");
        if (captchaCode == null || !userDto.getCaptchaCode().trim().toUpperCase().equals(captchaCode.toString()))
            throw new CaptchaInvalidException("验证码错误");
        if (userService.check(userDto.getUsername().trim()) || merchantService.check(userDto.getUsername().trim()))
            throw new CustomException("用户已经存在");

        userService.save(userDto);
    }

    @RequestMapping(value = "/mch/register", method = RequestMethod.POST)
    public void registerMch(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
        Object captchaCode = request.getSession().getAttribute("captcha_code");
        if (captchaCode == null || !userDto.getCaptchaCode().trim().toUpperCase().equals(captchaCode.toString()))
            throw new CaptchaInvalidException("验证码错误");
        if (merchantService.check(userDto.getUsername().trim()) || userService.check(userDto.getUsername().trim()))
            throw new CustomException("用户已经存在");

        merchantService.save(userDto);
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
