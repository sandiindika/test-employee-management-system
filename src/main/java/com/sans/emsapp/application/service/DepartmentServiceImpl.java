package com.sans.emsapp.application.service;

import com.sans.emsapp.adapter.dto.request.DepartmentRequest;
import com.sans.emsapp.adapter.mapper.DepartmentMapper;
import com.sans.emsapp.application.contract.DepartmentService;
import com.sans.emsapp.domain.model.Department;
import com.sans.emsapp.infrastructure.repository.DepartmentRepository;
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
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<Department> findAllDepartment(Pageable pageable) {
        Page<Department> departments = departmentRepository.findAll(pageable);
        if (departments.getTotalElements() <= 0)
            throw new EntityNotFoundException("The resource you requested is empty");
        return departments;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Department saveDepartment(DepartmentRequest request) {
        return departmentRepository.save(departmentMapper.toModel(request));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteDepartment(String id) {
        Department department = departmentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("The data you requested is not found. ID Department " + id));
        departmentRepository.delete(department);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Department> findDepartmentByName(String keyword, Pageable pageable) {
        List<Department> filteredDepartments = departmentRepository.findAll(pageable).getContent().stream()
                .filter(dpt -> dpt.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        if (filteredDepartments.isEmpty()) throw new EntityNotFoundException("The resource you requested is empty");
        return new PageImpl<>(filteredDepartments, pageable, filteredDepartments.size());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Department updateDepartment(String id, DepartmentRequest request) {
        Department department = departmentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("The data you requested is not found. ID Department: " + id));

        department.setName(request.getName());
        department.setDescription(request.getDescription());
        return departmentRepository.save(department);
    }
}
