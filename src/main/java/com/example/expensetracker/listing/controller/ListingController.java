package com.example.expensetracker.listing.controller;

import com.example.expensetracker.expense.dto.ExpenseResponseDto;
import com.example.expensetracker.listing.dto.ExpenseSummaryDto;
import com.example.expensetracker.listing.dto.PagedResponse;
import com.example.expensetracker.listing.service.ListingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/expenses")
@Tag(name = "Expense Listing", description = "Endpoint for viewing paginated expenses")
//@PreAuthorize("hasRole('ADMIN')")
//@SecurityRequirement(name = "bearerAuth")

public class ListingController {

    private final ListingServiceImpl listingService;

    public ListingController(ListingServiceImpl listingService) {
        this.listingService = listingService;
    }

    @Operation(
            summary = "Get expenses in paginated format",
            description = "Returns a page of expenses based on page and size. ⚠️ Pages are zero-indexed (starts from 0). If you request a page with no data, you'll receive a response with a message indicating no expenses were found."
    )
    @GetMapping("/paged")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response with paginated expenses"),
            @ApiResponse(responseCode = "204", description = "No expenses found for the requested page"),
            @ApiResponse(responseCode = "400", description = "Invalid page index (out of bounds)")
    })
    public ResponseEntity<PagedResponse<ExpenseResponseDto>> getPagedExpenses(
            @Parameter(description = "Page number (starts from 0 — first page is 0)")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "10") int size
    ) {
        PagedResponse<ExpenseResponseDto> response = listingService.getPagedExpense(page, size);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get filtered paginated expenses",
            description = "Fetches a paged list of expenses with optional filters: category, start date, end date. Returns 204 if no data matches."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No expenses found for given filters"),
            @ApiResponse(responseCode = "400", description = "Invalid query parameter format"),
            @ApiResponse(responseCode = "404", description = "Requested page is out of bounds")
    })
    @GetMapping("/expenses")
    public ResponseEntity<PagedResponse<ExpenseResponseDto>> getFilteredExpense(
            @Parameter(description = "Page number (zero-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of expenses per page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Expense category to filter by") @RequestParam(required = false) String category,
            @Parameter(description = "Start date for filtering (ISO format: yyyy-MM-dd)")
             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date for filtering (ISO format: yyyy-MM-dd)")
             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ){
        PagedResponse<ExpenseResponseDto> response = listingService.getFilteredExpenses(page, size, category, startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/summary")
    @Operation(summary = "Get summary of expenses", description = "Returns total spent, top category, and monthly spending breakdown")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved expense summary",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExpenseSummaryDto.class)))
    @ApiResponse(responseCode = "204", description = "No expenses found to summarize")
    public ResponseEntity<ExpenseSummaryDto> getSummary() {
        return ResponseEntity.ok(listingService.getExpenseSummary());
    }
}