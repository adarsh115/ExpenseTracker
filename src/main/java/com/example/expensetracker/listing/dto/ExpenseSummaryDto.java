package com.example.expensetracker.listing.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Summary of expenses including totals and monthly breakdown")
//        example = "{\"totalSpent\":12250.50,\"topCategory\":\"Food\",\"monthlyBreakdown\":{\"2024-07\":3450.75,\"2024-06\":2700.00}}")
public record ExpenseSummaryDto(

        @Schema(description = "Total amount spent across all expenses", example = "55854.00")
        double totalExpenses,

        @Schema(description = "Category with the highest cumulative spending", example = "Travel")
        String topCategory,

        @Schema(description = "Monthly totals formatted as 'YYYY-MM'", example = "{\"2023-07\": 55854.00}")
        Map<String, Double> breakdown
) {}


/*

OR
public final class ExpenseSummaryDto {
    private final double totalSpent;
    private final String topCategory;
    private final Map<String, Double> monthlyBreakdown;

    public ExpenseSummaryDto(double totalSpent, String topCategory, Map<String, Double> monthlyBreakdown) {
        this.totalSpent = totalSpent;
        this.topCategory = topCategory;
        this.monthlyBreakdown = monthlyBreakdown;
    }

    public double getTotalSpent() { return totalSpent; }
    public String getTopCategory() { return topCategory; }
    public Map<String, Double> getMonthlyBreakdown() { return monthlyBreakdown; }
}
 */
