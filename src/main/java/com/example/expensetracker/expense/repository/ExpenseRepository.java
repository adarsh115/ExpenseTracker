package com.example.expensetracker.expense.repository;

import com.example.expensetracker.expense.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("""
    SELECT e FROM Expense e 
    WHERE LOWER(e.category) = LOWER(:category)
""")

    Page<Expense> findByCategoryIgnoreCase(String category, Pageable pageable);

    Page<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("""
    SELECT e FROM Expense e 
    WHERE LOWER(e.category) = LOWER(:category)
      AND e.date BETWEEN :startDate AND :endDate
""")
    Page<Expense> findByCategoryAndDateBetweenIgnoreCase(String category, LocalDate startDate, LocalDate endDate, Pageable pageable);

}
