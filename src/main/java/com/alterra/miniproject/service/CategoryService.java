package com.alterra.miniproject.service;

import java.util.List;

import com.alterra.miniproject.domain.common.CategoryInput;
import com.alterra.miniproject.domain.model.Category;
import com.alterra.miniproject.repository.CategoryRepository;
import com.alterra.miniproject.repository.ExpenseDetailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseDetailRepository expenseDetailRepository;

    public List<Category> getCategory() {
        try {
            List<Category> categories = categoryRepository.findAll();

            if(categories == null) throw new Exception();

            log.info("Get categories success");

            return categories;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Category getCategory(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Category with id " + id + " not found"));
            log.info("Get category by id success");

            return category;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Category postCategory(CategoryInput category) {
        try {
            Category cat = new Category();
            cat.setName(category.getName());

            if(cat.getName() == null) throw new Exception();

            categoryRepository.save(cat);
            log.info("Category posted");
            return cat; 
        } catch (Exception e) {
            log.error("Post category error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Category updateCategory(Long id, CategoryInput category) {
        try {
            Category cat = getCategory(id);
            
            if(category.getName() != null) cat.setName(category.getName());

            if(cat.getName() == null) throw new Exception();

            categoryRepository.save(cat);
            log.info("Category updated");
            return cat; 
        } catch (Exception e) {
            log.error("Update category error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void deleteCategory(Long id) {
        try {
            categoryRepository.deleteById(id);
            expenseDetailRepository.deleteExpenseDetailByCategory(id);
            log.info("Category deleted");
        } catch (Exception e) {
            log.error("Delete category error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
