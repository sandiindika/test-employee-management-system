package com.sans.emsapp.application.service;

import com.sans.emsapp.adapter.dto.request.EmployeeRequest;
import com.sans.emsapp.adapter.mapper.EmployeeMapper;
import com.sans.emsapp.application.contract.EmployeeService;
import com.sans.emsapp.domain.model.Department;
import com.sans.emsapp.domain.model.Employee;
import com.sans.emsapp.domain.model.Position;
import com.sans.emsapp.infrastructure.repository.DepartmentRepository;
import com.sans.emsapp.infrastructure.repository.EmployeeRepository;
import com.sans.emsapp.infrastructure.repository.PositionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<Employee> findAllEmployees(Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAllEmployee(pageable);
        if (employees.getTotalElements() == 0) throw new EntityNotFoundException("There are no employees");
        return employees;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Employee saveEmployee(EmployeeRequest request) {
        Employee employee = employeeMapper.toModel(request);
        Position position = positionRepository.findById(request.getPositionId()).orElseThrow(() ->
                new EntityNotFoundException("Position not found"));
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() ->
                new EntityNotFoundException("Department not found"));

        employee.setPosition(position);
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteEmployee(String id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Employee not found"));
        employee.setActive(false);
        employeeRepository.save(employee);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Employee updateEmployee(String id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Employee not found"));
        Position position = positionRepository.findById(request.getPositionId()).orElseThrow(() ->
                new EntityNotFoundException("Position not found"));
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() ->
                new EntityNotFoundException("Department not found"));
        
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setAddress(request.getAddress());
        employee.setSalary(request.getSalary());
        employee.setPosition(position);
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Employee> findEmployeeByName(String name, Pageable pageable) {
        List<Employee> filteredEmployee = employeeRepository.findAllEmployee(pageable).getContent().stream()
                .filter(emp -> emp.getFirstName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        if (filteredEmployee.isEmpty()) throw new EntityNotFoundException("Employee not found");
        return new PageImpl<>(filteredEmployee, pageable, filteredEmployee.size());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Employee> filterEmployeesBySalary(long salary, Pageable pageable) {
        List<Employee> filteredEmployee = employeeRepository.findEmployeeWithSalaryAbove(salary);
        if (filteredEmployee.isEmpty()) throw new EntityNotFoundException("Employee not found");
        return new PageImpl<>(filteredEmployee, pageable, filteredEmployee.size());
    }

    @Override
    public List<Object[]> getEmployeeCountByDepartment() {
        return employeeRepository.countEmployeesByDepartment();
    }
}
