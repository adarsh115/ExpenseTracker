package com.example.expensetracker.expense.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ExpenseResponseDto {
    private  Long id;
    private  String title;
    private  BigDecimal amount;
    private  String category;
    private  LocalDate date;

}
