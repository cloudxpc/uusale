package com.uutic.uusale.service.impl;

import com.uutic.uusale.dto.UserDto;
import com.uutic.uusale.entity.Merchant;
import com.uutic.uusale.repository.MerchantRepository;
import com.uutic.uusale.service.MerchantService;
import com.uutic.uusale.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    public Merchant find(UserDto userDto) throws Exception {
        Merchant merchant = merchantRepository.findByUsernameAndPassword(userDto.getUsername().trim(), Md5Util.MD5(userDto.getPassword().trim()));
        if (merchant == null)
            merchant = merchantRepository.findByPhoneNumberAndPassword(userDto.getUsername().trim(), Md5Util.MD5(userDto.getPassword().trim()));

        return merchant;
    }

    @Override
    public Boolean check(String username) {
        return merchantRepository.existsByUsername(username);
    }

    @Override
    public Merchant save(UserDto userDto) throws Exception {
        Merchant merchant = new Merchant();
        merchant.setId(UUID.randomUUID().toString());
        merchant.setUsername(userDto.getUsername().trim());
        merchant.setPassword(Md5Util.MD5(userDto.getPassword().trim()));
        merchant.setDisplayName(userDto.getDisplayName().trim());
        merchant.setPhoneNumber(userDto.getPhoneNumber().trim());
        merchantRepository.save(merchant);
        return merchant;
    }
}
