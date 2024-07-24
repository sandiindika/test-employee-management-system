package com.sans.emsapp.infrastructure.repository;

import com.sans.emsapp.domain.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String> {

    @Query(value = "SELECT * FROM m_employees WHERE salary > :salary", nativeQuery = true)
    List<Employee> findEmployeeWithSalaryAbove(@Param("salary") long salary);

    @Query(value = "SELECT d.name as department_name, COUNT(e.id) as employee_count " +
            "FROM m_employees e " +
            "JOIN m_departments d ON e.department_id = d.id " +
            "GROUP BY d.name", nativeQuery = true)
    List<Object[]> countEmployeesByDepartment();

    @Query(value = "SELECT * FROM m_employees WHERE active <> false", nativeQuery = true)
    Page<Employee> findAllEmployee(Pageable pageable);
}
