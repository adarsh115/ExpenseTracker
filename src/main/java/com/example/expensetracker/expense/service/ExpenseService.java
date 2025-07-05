package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseRequestDto;
import com.example.expensetracker.dto.ExpenseResponseDto;

import java.util.List;

public interface ExpenseService {
    ExpenseResponseDto createExpense(ExpenseRequestDto requestDto);
    List<ExpenseResponseDto> getAllExpenses();
    ExpenseResponseDto getExpenseById(Long id);
    ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto requestDto);
    ExpenseResponseDto deleteExpense(Long id);
}