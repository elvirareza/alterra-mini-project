package com.alterra.miniproject.controller;

import com.alterra.miniproject.domain.common.CategoryInput;
import com.alterra.miniproject.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<?> postCategory (@RequestBody CategoryInput req) {
        return ResponseEntity.ok(categoryService.saveCategory(req));
    }
}
