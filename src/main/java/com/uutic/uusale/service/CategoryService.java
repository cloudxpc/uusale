package com.uutic.uusale.service;

import com.uutic.uusale.dto.CategoryDto;
import com.uutic.uusale.entity.Category;

import java.util.List;

public interface CategoryService {
    Category findByName(String name);
    void save(CategoryDto categoryDto) throws Exception;
    void delete(String id);
    List<Category> findAll();
    Category findOne(String id);
}
