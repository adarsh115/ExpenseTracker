package com.example.expensetracker.listing.service;

import com.example.expensetracker.expense.dto.ExpenseResponseDto;
import com.example.expensetracker.expense.model.Expense;
import com.example.expensetracker.expense.repository.ExpenseRepository;
import com.example.expensetracker.expense.service.ExpenseMapper;
import com.example.expensetracker.expense.service.ExpenseServiceImpl;
import com.example.expensetracker.listing.dto.ExpenseSummaryDto;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

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


    @Override
    public PagedResponse<ExpenseResponseDto> getFilteredExpenses(int page, int size, String category, LocalDate startDate, LocalDate endDate) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Expense> expensePage;

        // Step 1: Run filtered query first
        if (category != null && startDate != null && endDate != null) {
            expensePage = expenseRepository.findByCategoryAndDateBetweenIgnoreCase(category, startDate, endDate, pageable);
        } else if (category != null) {
            expensePage = expenseRepository.findByCategoryIgnoreCase(category, pageable);
        } else if (startDate != null && endDate != null) {
            expensePage = expenseRepository.findByDateBetween(startDate, endDate, pageable);
        } else {
            expensePage = expenseRepository.findAll(pageable);
        }

        // Step 2: Handle completely empty results
        if (expensePage.isEmpty()) {
            if (page == 0) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No expenses match the filter criteria");
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page index out of bounds");
            }
        }

        // Step 3: Map and return response
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

    @Override
    public ExpenseSummaryDto getExpenseSummary(){
        List<Expense> expenses = expenseRepository.findAll();

        if(expenses.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No expenses found to summarize");
        }

        BigDecimal totalSpent = expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        String topCategory = expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
        Map<String, BigDecimal> monthlyBreakdown = expenses.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getDate().getYear() + "-" + String.format("%02d", e.getDate().getMonthValue()),
                        LinkedHashMap::new,
                        Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)
                ));
        return new ExpenseSummaryDto(
                totalSpent.doubleValue(),      // Or change DTO to use BigDecimal directly if preferred
                topCategory,
                monthlyBreakdown.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().doubleValue(),  // Convert values to double for DTO
                                (a, b) -> b,
                                LinkedHashMap::new
                        ))
        );
    }

    /*
    OR
    @Override
    public ExpenseSummaryDto getExpenseSummary() {
        List<Expense> expenses = expenseRepository.findAll();

        if (expenses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No expenses to summarize");
        }

        // Total spent
        BigDecimal totalSpent = BigDecimal.ZERO;

        // Top category tracking
        Map<String, BigDecimal> categoryTotals = new HashMap<>();

        // Monthly breakdown
        Map<String, BigDecimal> monthlyBreakdown = new LinkedHashMap<>();

        for (Expense expense : expenses) {
            // Add to total
            totalSpent = totalSpent.add(expense.getAmount());

            // Category totals
            String category = expense.getCategory();
            categoryTotals.put(category, categoryTotals.getOrDefault(category, BigDecimal.ZERO).add(expense.getAmount()));

            // Monthly breakdown
            String monthKey = expense.getDate().getYear() + "-" + String.format("%02d", expense.getDate().getMonthValue());
            monthlyBreakdown.put(monthKey, monthlyBreakdown.getOrDefault(monthKey, BigDecimal.ZERO).add(expense.getAmount()));
        }

        // Find top category
        String topCategory = "N/A";
        BigDecimal highest = BigDecimal.ZERO;

        for (Map.Entry<String, BigDecimal> entry : categoryTotals.entrySet()) {
            if (entry.getValue().compareTo(highest) > 0) {
                highest = entry.getValue();
                topCategory = entry.getKey();
            }
        }

        // Convert monthly totals to double for DTO
        Map<String, Double> convertedBreakdown = new LinkedHashMap<>();
        for (Map.Entry<String, BigDecimal> entry : monthlyBreakdown.entrySet()) {
            convertedBreakdown.put(entry.getKey(), entry.getValue().doubleValue());
        }

        return new ExpenseSummaryDto(
                totalSpent.doubleValue(),
                topCategory,
                convertedBreakdown
        );
    }
     */
}
