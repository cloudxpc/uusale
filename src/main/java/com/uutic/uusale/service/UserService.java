package com.uutic.uusale.service;

import com.uutic.uusale.dto.UserDto;
import com.uutic.uusale.entity.User;

public interface UserService {
    User find(UserDto userDto) throws Exception;
    Boolean check(String username);
    User save(UserDto userDto) throws Exception;
}
