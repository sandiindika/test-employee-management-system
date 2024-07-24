package com.sans.emsapp.adapter.mapper;

import com.sans.emsapp.adapter.dto.request.DepartmentRequest;
import com.sans.emsapp.adapter.dto.response.DepartmentResponse;
import com.sans.emsapp.domain.model.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public DepartmentResponse toResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .build();
    }

    public Department toModel(DepartmentRequest request) {
        return Department.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
