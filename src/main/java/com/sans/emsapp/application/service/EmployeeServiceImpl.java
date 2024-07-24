package com.sans.emsapp.application.service;

import com.sans.emsapp.adapter.dto.request.EmployeeRequest;
import com.sans.emsapp.application.contract.EmployeeService;
import com.sans.emsapp.domain.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public Page<Employee> findAllEmployees(Pageable pageable) {
        return null;
    }

    @Override
    public Employee saveEmployee(EmployeeRequest employee) {
        return null;
    }

    @Override
    public void deleteEmployee(String id) {

    }

    @Override
    public Employee updateEmployee(String id, EmployeeRequest employee) {
        return null;
    }

    @Override
    public Page<Employee> findEmployeeByName(String name, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Employee> filterEmployeesBySalary(long salary, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Objects[]> getEmployeeCountByDepartment(String department, Pageable pageable) {
        return null;
    }
}
