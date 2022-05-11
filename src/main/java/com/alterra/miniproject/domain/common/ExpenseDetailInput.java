package com.alterra.miniproject.domain.common;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExpenseDetailInput {
    private String name;
    private Double amount;
    private Long categoryId;
    private Long expenseId;
}
