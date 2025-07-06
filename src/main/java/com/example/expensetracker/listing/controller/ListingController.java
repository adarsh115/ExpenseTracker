package com.example.expensetracker.listing.controller;

import com.example.expensetracker.expense.dto.ExpenseResponseDto;
import com.example.expensetracker.listing.dto.PagedResponse;
import com.example.expensetracker.listing.service.ListingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@Tag(name = "Expense Listing", description = "Endpoint for viewing paginated expenses")
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
}