package com.uutic.uusale.controller;

import com.uutic.uusale.dto.CategoryDto;
import com.uutic.uusale.entity.Category;
import com.uutic.uusale.exceptions.CustomException;
import com.uutic.uusale.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    private CategoryDto entityToDto(Category entity) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(entity.getId());
        categoryDto.setName(entity.getName());
        return categoryDto;
    }

    @GetMapping("/list")
    public List<CategoryDto> getAll() {
        return categoryService.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/get")
    public CategoryDto get(@RequestParam String id) {
        Category one = categoryService.findOne(id);
        if (one == null)
            throw new CustomException("找不到分类");
        return entityToDto(one);
    }

    @PostMapping("/save")
    public void save(@RequestBody CategoryDto categoryDto) throws Exception {
        categoryService.save(categoryDto);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody CategoryDto categoryDto) {
        categoryService.delete(categoryDto.getId());
    }
}
