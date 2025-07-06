package com.example.expensetracker.listing.service;

import com.example.expensetracker.expense.dto.ExpenseResponseDto;
import com.example.expensetracker.expense.model.Expense;
import com.example.expensetracker.expense.repository.ExpenseRepository;
import com.example.expensetracker.expense.service.ExpenseMapper;
import com.example.expensetracker.listing.dto.PagedResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.flogger.Flogger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ListingServiceImplementation implements ListingServiceImpl{
    private final ExpenseRepository expenseRepository;

    public ListingService(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    @Override
    public PagedResponse<ExpenseResponseDto> getPagedExpense(int page, int size
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").ascending());
        Page<Expense> expensePage = expenseRepository.findAll(pageable);

        if (expensePage.isEmpty()) {
            logger.warn("User requested empty page: {}", page);
        }


        List<ExpenseResponseDto> content = expensePage
                .getContent()
                .stream()
                .map(ExpenseMapper::toDto)
                .toList();

        PagedResponse<ExpenseResponseDto> response = new PagedResponse<>(
                content,
                expensePage.getNumber(),
                expensePage.getSize(),
                expensePage.getTotalPages(),
                expensePage.getTotalElements(),
                expensePage.isLast()
        );

        return response;
    }
}
