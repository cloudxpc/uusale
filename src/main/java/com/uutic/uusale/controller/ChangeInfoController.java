package com.uutic.uusale.controller;

import com.uutic.uusale.dto.PasswordDto;
import com.uutic.uusale.dto.UserDto;
import com.uutic.uusale.entity.Merchant;
import com.uutic.uusale.entity.User;
import com.uutic.uusale.exceptions.CustomException;
import com.uutic.uusale.service.MerchantService;
import com.uutic.uusale.service.UserService;
import com.uutic.uusale.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/change")
public class ChangeInfoController {
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(HttpServletRequest request, @RequestBody UserDto userDto) {
        String id = request.getAttribute("user_id").toString();
        User user = userService.find(id);
        if (user != null) {
            userService.updateInfo(userDto, user);
            return;
        }

        Merchant merchant = merchantService.find(id);
        if (merchant != null) {
            merchantService.updateInfo(userDto, merchant);
            return;
        }

        throw new CustomException("找不到用户");
    }

    @RequestMapping(value = "/changepwd", method = RequestMethod.POST)
    public void changepwd(HttpServletRequest request, @RequestBody PasswordDto passwordDto) throws Exception {
        String id = request.getAttribute("user_id").toString();
        User user = userService.find(id);
        if (user != null) {
            if (!user.getPassword().equals(Md5Util.MD5(passwordDto.getOldPassword().trim())))
                throw new CustomException("旧密码输入不正确");

            userService.updatePassword(user, passwordDto);
            return;
        }

        Merchant merchant = merchantService.find(id);
        if (merchant != null) {
            if (!merchant.getPassword().equals(Md5Util.MD5(passwordDto.getOldPassword().trim())))
                throw new CustomException("旧密码输入不正确");

            merchantService.updatePassword(merchant, passwordDto);
            return;
        }

        throw new CustomException("找不到用户");
    }
}
