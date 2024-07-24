package com.sans.emsapp.infrastructure.repository;

import com.sans.emsapp.domain.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Query(value = "SELECT * FROM Employee WHERE salary > :salary", nativeQuery = true)
    List<Employee> findEmployeeWithSalaryAbove(@Param("salary") long salary);

    @Query(value = "SELECT department_id, COUNT(*) as employee_count FROM Employee GROUP BY department_id", nativeQuery = true)
    List<Object[]> countEmployeesByDepartment();
}
