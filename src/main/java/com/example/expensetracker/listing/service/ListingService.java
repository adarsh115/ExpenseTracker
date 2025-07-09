package com.example.expensetracker.listing.service;

import com.example.expensetracker.expense.dto.ExpenseResponseDto;
import com.example.expensetracker.listing.dto.ExpenseSummaryDto;
import com.example.expensetracker.listing.dto.PagedResponse;

import java.time.LocalDate;

public interface ListingService {
    PagedResponse<ExpenseResponseDto> getPagedExpense(int page, int size);
    PagedResponse<ExpenseResponseDto> getFilteredExpenses(int page, int size, String category, LocalDate startDate, LocalDate endDate);
    ExpenseSummaryDto getExpenseSummary();
}
