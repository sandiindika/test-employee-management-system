package com.sans.emsapp.adapter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {

    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int numberOfElements;
    private T content;
}
