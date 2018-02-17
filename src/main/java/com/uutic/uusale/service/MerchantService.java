package com.uutic.uusale.service;

import com.uutic.uusale.dto.PasswordDto;
import com.uutic.uusale.dto.UserDto;
import com.uutic.uusale.entity.Merchant;

public interface MerchantService {
    Merchant find(UserDto userDto) throws Exception;
    Merchant find(String id);
    Boolean check(String username);
    Merchant save(UserDto userDto) throws Exception;
    void updateInfo(UserDto userDto, Merchant merchant);
    void updatePassword(Merchant merchant, PasswordDto passwordDto) throws Exception;
}
