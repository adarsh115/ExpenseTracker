package com.example.expensetracker.filter.controller;

import com.example.expensetracker.expense.dto.ExpenseResponseDto;
import com.example.expensetracker.filter.service.FilteringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Expense Filtering", description = "Endpoints for filtering expenses by category and date")
@RestController
@RequestMapping("/api/expenses/filter")
public class FilterController {

    private final FilteringService filteringService;

    public FilterController(FilteringService filteringService){
        this.filteringService = filteringService;
    }

    @Operation(
            summary = "Filter expenses by category and/or date range",
            description = "Allows clients to filter expenses using optional query parameters: category, startDate, endDate"
    )
    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> getFilteredExpenses(
            @Parameter(description = "Expense category (e.g., Food, Travel)")
            @RequestParam(required = false) String category,

            @Parameter(description = "Start date for filtering (YYYY-MM-DD)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,

            @Parameter(description = "End date for filtering (YYYY-MM-DD)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ){
        return ResponseEntity.ok(filteringService.filterExpenses(category, startDate, endDate));
    }

}
