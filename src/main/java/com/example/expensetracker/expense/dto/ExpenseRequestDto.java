package com.example.expensetracker.expense.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ExpenseRequestDto {
    @NotBlank(message = "Title is required")
    private String title;

    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

//    public String getTitle() {
//        return title;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setAmount(BigDecimal amount) {
//        this.amount = amount;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }
//

}
