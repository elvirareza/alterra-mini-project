package com.alterra.miniproject.domain.common;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExpenseInput {
    private String name;
    private String username;
    private Long paymentId;
    private LocalDateTime expenseDate;
}
