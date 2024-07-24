package com.sans.emsapp.application.contract;

import com.sans.emsapp.adapter.dto.request.DepartmentRequest;
import com.sans.emsapp.domain.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentService {

    Page<Department> findAllDepartment(Pageable pageable);
    Department saveDepartment(DepartmentRequest request);
    void deleteDepartment(String id);
    Page<Department> findDepartmentByName(String keyword, Pageable pageable);
    Department updateDepartment(String id, DepartmentRequest request);
}