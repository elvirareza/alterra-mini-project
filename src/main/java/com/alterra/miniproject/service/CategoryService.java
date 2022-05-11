package com.alterra.miniproject.service;

import com.alterra.miniproject.domain.common.CategoryInput;
import com.alterra.miniproject.domain.model.Category;
import com.alterra.miniproject.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryInput saveCategory(CategoryInput category) {
        try {
            Category cat = new Category();
            cat.setName(category.getName());
            log.info("Category: {}", cat.getName());
            categoryRepository.save(cat);
            log.info("Category saved");

            return category; 
        } catch (Exception e) {
            log.error("Save category error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
