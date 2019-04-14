package com.uutic.uusale.repository;

import com.uutic.uusale.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findFirstByName(String name);
    boolean existsByName(String name);

    @Modifying
    @Query(value = "UPDATE product SET cat_id = null WHERE cat_id = ?1", nativeQuery = true)
    void removeCategoryFromProducts(String id);
}
