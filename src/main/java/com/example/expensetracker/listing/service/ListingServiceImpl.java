package com.example.expensetracker.listing.service;

import com.example.expensetracker.expense.dto.ExpenseResponseDto;
import com.example.expensetracker.expense.model.Expense;
import com.example.expensetracker.expense.repository.ExpenseRepository;
import com.example.expensetracker.expense.service.ExpenseMapper;
import com.example.expensetracker.expense.service.ExpenseServiceImpl;
import com.example.expensetracker.listing.dto.PagedResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ListingServiceImpl implements ListingService{
    private final ExpenseRepository expenseRepository;
    private static final Logger logger = LoggerFactory.getLogger(ListingService.class);

    public ListingServiceImpl(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    @Override
    public PagedResponse<ExpenseResponseDto> getPagedExpense(int page, int size) {
        long total = expenseRepository.count();

        if (total == 0) {
            logger.warn("User requested page {}, but no expenses exist in database", page);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No expenses exist yet");
        }

        int maxPages = (int) Math.ceil((double) total / size);

        if (page < 0 || page >= maxPages) {
            logger.error("User requested out-of-bound page index {}. Valid range: 0 to {}", page, maxPages - 1);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Page index out of bounds. Valid range: 0 to " + (maxPages - 1));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").ascending());
        Page<Expense> expensePage = expenseRepository.findAll(pageable);

        if (expensePage.isEmpty()) {
            logger.warn("User requested page {}, but no expenses found after filters", page);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No expenses found for page " + page);
        }

        List<ExpenseResponseDto> content = expensePage.getContent()
                .stream()
                .map(ExpenseMapper::toDto)
                .toList();

        return new PagedResponse<>(
                content,
                expensePage.getNumber(),
                expensePage.getSize(),
                expensePage.getTotalPages(),
                expensePage.getTotalElements(),
                expensePage.isLast()
        );
    }
}
