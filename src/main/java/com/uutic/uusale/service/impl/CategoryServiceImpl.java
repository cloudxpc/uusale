package com.uutic.uusale.service.impl;

import com.uutic.uusale.dto.CategoryDto;
import com.uutic.uusale.entity.Category;
import com.uutic.uusale.exceptions.CustomException;
import com.uutic.uusale.repository.CategoryRepository;
import com.uutic.uusale.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findByName(String name) {
        return categoryRepository.findFirstByName(name);
    }

    @Override
    public void save(CategoryDto categoryDto) throws Exception {
        if (StringUtils.isEmpty(categoryDto.getName()))
            throw new CustomException("分类名称不能为空");

        Category category = null;
        if (categoryDto.getId() == null) {
            category = new Category();
            category.setId(UUID.randomUUID().toString());
        } else {
            category = categoryRepository.findOne(categoryDto.getId());
            if (category == null)
                throw new CustomException("找不到分类");
        }

        if (categoryRepository.existsByName(categoryDto.getName()))
            throw new CustomException("分类名称已经存在");

        category.setName(categoryDto.getName());
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void delete(String id) {
        categoryRepository.removeCategoryFromProducts(id);
        categoryRepository.delete(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findOne(String id) {
        return categoryRepository.findOne(id);
    }
}
