package com.example.expensetracker.expense.repository;

import com.example.expensetracker.expense.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
