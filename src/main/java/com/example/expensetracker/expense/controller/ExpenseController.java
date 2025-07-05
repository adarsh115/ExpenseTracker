package com.example.expensetracker.expense.controller;

import com.example.expensetracker.expense.dto.ExpenseRequestDto;
import com.example.expensetracker.expense.dto.ExpenseResponseDto;
import com.example.expensetracker.expense.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Expenses", description = "Operations related to expenses")
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Operation(
            summary = "Get all expenses",
            description = "Retrieve a list of all recorded expenses",
            tags = {"Expenses"},
            operationId = "getAllExpenses"
    )
    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @Operation(
            summary = "Get expense by ID",
            description = "Retrieve an expense by its unique identifier",
            tags = {"Expenses"},
            operationId = "getExpenseById"
    )
    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expense found"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    public ResponseEntity<ExpenseResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @Operation(
            summary = "Create a new expense",
            description = "Add a new expense record to the system",
            tags = {"Expenses"},
            operationId = "createExpense"
    )
    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Expense successfully created"),
            @ApiResponse(responseCode = "400", description = "Validation failed: missing or invalid fields")
    })
    public ResponseEntity<ExpenseResponseDto> createExpense(@RequestBody @Valid ExpenseRequestDto requestDto) {
        ExpenseResponseDto saved = expenseService.createExpense(requestDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update an existing expense",
            description = "Edit fields of an existing expense using its ID",
            tags = {"Expenses"},
            operationId = "updateExpense"
    )
    @PutMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expense updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    public ResponseEntity<ExpenseResponseDto> updateExpense(@PathVariable Long id
    ,@Valid @RequestBody ExpenseRequestDto dto){
        ExpenseResponseDto updated = expenseService.updateExpense(id, dto);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted expense returned"),
            @ApiResponse(responseCode = "204", description = "Expense deleted"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    public ResponseEntity<ExpenseResponseDto> deleteExpense(@PathVariable Long id){
        ExpenseResponseDto deleted = expenseService.deleteExpense(id);
        return ResponseEntity.ok(deleted);
    }

}
