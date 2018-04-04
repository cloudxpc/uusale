package com.uutic.uusale.repository;

import com.uutic.uusale.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, String> {
    List<Cart> findAllByUserId(String userId);
    void deleteAllByUserId(String userId);
}
