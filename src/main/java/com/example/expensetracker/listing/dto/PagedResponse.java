package com.example.expensetracker.listing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "PaginatedExpenses")

public class PagedResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private int totalPage;
    private long totalElements;
    private boolean last;
}
