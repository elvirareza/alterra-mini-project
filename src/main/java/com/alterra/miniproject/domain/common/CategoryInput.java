package com.alterra.miniproject.domain.common;

import javax.persistence.Column;

import lombok.Data;

@Data
public class CategoryInput {
    @Column(nullable = false)
    private String name;
}
