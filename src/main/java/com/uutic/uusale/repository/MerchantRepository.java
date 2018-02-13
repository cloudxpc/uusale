package com.uutic.uusale.repository;

import com.uutic.uusale.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, String> {
    Boolean existsByUsername(String username);
    Merchant findByUsernameAndPassword(String username, String password);
    Merchant findByPhoneNumberAndPassword(String phoneNumber, String password);
}
