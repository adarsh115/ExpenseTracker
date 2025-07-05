package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseRequestDto;
import com.example.expensetracker.dto.ExpenseResponseDto;
import com.example.expensetracker.exception.ResourceNotFoundException;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
@Service
public class ExpenseServiceImpl implements ExpenseService{
    private final ExpenseRepository expenseRepository;
    private static final Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;

    }

    @Override
    public List<ExpenseResponseDto> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream()
                .map(ExpenseMapper::toDto)
                .toList();
    }

    @Override
    public ExpenseResponseDto getExpenseById(Long id) {
        logger.debug("Fetching expense with ID: {}", id);
        logger.info("🔍 Inside getExpenseById method for ID: {}", id);

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with ID: " + id));
        ExpenseResponseDto dto = ExpenseMapper.toDto(expense);
        return dto;
    }

    @Override
    public ExpenseResponseDto createExpense(ExpenseRequestDto requestDto) {
        Expense expense = Expense.builder()
                .title(requestDto.getTitle())
                .amount(requestDto.getAmount())
                .category(requestDto.getCategory())
                .date(requestDto.getDate())
                .build();

        Expense saved = expenseRepository.save(expense);
        logger.info("CREATED NEW EXPENSE: {}", saved.getId());
        return ExpenseMapper.toDto(saved);
    }

    @Override
    public ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto requestDto) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with ID: "+id));

        expense.setTitle(requestDto.getTitle());
        expense.setAmount(requestDto.getAmount());
        expense.setCategory(requestDto.getCategory());
        expense.setDate(requestDto.getDate());

        Expense updated  = expenseRepository.save(expense);
        logger.info("✏\uFE0F Updated expense ID: {}\", id");
        return ExpenseMapper.toDto(updated);
    }

    @Override
    public ExpenseResponseDto deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with ID: "+ id));

        expenseRepository.delete(expense);
        logger.info("Deleted expense ID: {}", id);

        return ExpenseMapper.toDto(expense);
    }


}