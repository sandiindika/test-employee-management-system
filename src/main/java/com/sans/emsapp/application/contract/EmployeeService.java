package com.sans.emsapp.application.contract;

import com.sans.emsapp.adapter.dto.request.EmployeeRequest;
import com.sans.emsapp.domain.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    Page<Employee> findAllEmployees(Pageable pageable);
    Employee saveEmployee(EmployeeRequest request);
    void deleteEmployee(String id);
    Page<Employee> findEmployeeByName(String keyword, Pageable pageable);
    Employee updateEmployee(String id, EmployeeRequest request);
    Page<Employee> filterEmployeesBySalary(long salary, Pageable pageable);
    List<Object[]> getEmployeeCountByDepartment();
}
