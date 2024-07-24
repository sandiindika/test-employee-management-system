package com.sans.emsapp.adapter.mapper;

import com.sans.emsapp.adapter.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageMapper {

    public PageResponse<Object> toResponse(Page page, Object content) {
        return PageResponse.builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber() + 1)
                .numberOfElements(page.getSize())
                .content(content)
                .build();
    }
}
