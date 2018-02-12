package com.uutic.uusale.repository;

import com.uutic.uusale.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
}
