package com.uutic.uusale.service.impl;

import com.uutic.uusale.dto.UserDto;
import com.uutic.uusale.entity.User;
import com.uutic.uusale.repository.UserRepository;
import com.uutic.uusale.service.UserService;
import com.uutic.uusale.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User find(UserDto userDto) throws Exception {
        User user = userRepository.findByUsernameAndPassword(userDto.getUsername().trim(), Md5Util.MD5(userDto.getPassword().trim()));
        if (user == null)
            user = userRepository.findByPhoneNumberAndPassword(userDto.getUsername().trim(), Md5Util.MD5(userDto.getPassword().trim()));

        return user;
    }

    @Override
    public User find(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public Boolean check(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional
    public User save(UserDto userDto) throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(userDto.getUsername().trim());
        user.setPassword(Md5Util.MD5(userDto.getPassword().trim()));
        user.setDisplayName(userDto.getDisplayName().trim());
        user.setPhoneNumber(userDto.getPhoneNumber().trim());
        userRepository.save(user);
        return user;
    }
}
