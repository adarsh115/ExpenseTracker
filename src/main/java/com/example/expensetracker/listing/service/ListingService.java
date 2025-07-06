package com.example.expensetracker.listing.service;

import com.example.expensetracker.expense.dto.ExpenseResponseDto;
import com.example.expensetracker.listing.dto.PagedResponse;

public interface ListingService {
    PagedResponse<ExpenseResponseDto> getPagedExpense(int page, int size);
}
