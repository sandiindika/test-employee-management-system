package com.sans.emsapp.adapter.mapper;

import com.sans.emsapp.adapter.dto.request.EmployeeRequest;
import com.sans.emsapp.adapter.dto.response.EmployeeResponse;
import com.sans.emsapp.domain.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .address(employee.getAddress())
                .phone(employee.getPhone())
                .salary(employee.getSalary())
                .positionName(employee.getPosition().getName())
                .departmentName(employee.getDepartment().getName())
                .build();
    }

    public Employee toModel(EmployeeRequest request) {
        return Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .address(request.getAddress())
                .phone(request.getPhone())
                .salary(request.getSalary())
                .build();
    }
}
