package com.example.expensetracker.filter.service;

import com.example.expensetracker.expense.dto.ExpenseResponseDto;
import com.example.expensetracker.expense.model.Expense;
import com.example.expensetracker.expense.repository.ExpenseRepository;
import com.example.expensetracker.expense.service.ExpenseMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilteringService {
    private final ExpenseRepository expenseRepository;


    public FilteringService(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    public List<ExpenseResponseDto> filterExpenses(String category, LocalDate startDate, LocalDate endDate){
        List<Expense> expenses = expenseRepository.findAll();

        return expenses.stream()
                .filter(exp -> category == null || exp.getCategory().equalsIgnoreCase(category))
                .filter(exp -> startDate == null || !exp.getDate().isBefore(startDate))
                .filter(exp -> endDate == null || !exp.getDate().isAfter(endDate))
                .map(ExpenseMapper::toDto)
                .collect(Collectors.toList());
    }

}
