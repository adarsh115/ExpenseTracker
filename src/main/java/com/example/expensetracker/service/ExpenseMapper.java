package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseRequestDto;
import com.example.expensetracker.dto.ExpenseResponseDto;
import com.example.expensetracker.model.Expense;

public class ExpenseMapper {

    /**
     * Converts ExpenseRequestDto to Expense entity
     * @param dto the request DTO containing expense data
     * @return Expense entity
     */
    public static Expense toEntity(ExpenseRequestDto dto) {
        if (dto == null) return null;

        return Expense.builder()
                .title(dto.getTitle())
                .amount(dto.getAmount())
                .category(dto.getCategory())
                .date(dto.getDate())
                .build();
    }

    /**
     * Converts Expense entity to ExpenseResponseDto
     * @param expense the expense entity
     * @return ExpenseResponseDto containing expense data
     */
    public static ExpenseResponseDto toDto(Expense expense) {
        if (expense == null) return null;

        return ExpenseResponseDto.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .date(expense.getDate())
                .build();
    }

    /**
     * Updates an existing Expense entity with data from ExpenseRequestDto
     * @param expense the existing expense entity to update
     * @param dto the request DTO containing new data
     * @return the updated expense entity
     */
    public static Expense updateEntity(Expense expense, ExpenseRequestDto dto) {
        if (expense == null || dto == null) return expense;

        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());
        expense.setCategory(dto.getCategory());
        expense.setDate(dto.getDate());

        return expense;
    }
}