package com.sans.emsapp.adapter.controller;

import com.sans.emsapp.adapter.config.constant.APIEndpoint;
import com.sans.emsapp.adapter.config.constant.ResponseMessage;
import com.sans.emsapp.adapter.dto.request.DepartmentRequest;
import com.sans.emsapp.adapter.dto.response.APIResponse;
import com.sans.emsapp.adapter.dto.response.DepartmentResponse;
import com.sans.emsapp.adapter.dto.response.PageResponse;
import com.sans.emsapp.adapter.mapper.DepartmentMapper;
import com.sans.emsapp.adapter.mapper.PageMapper;
import com.sans.emsapp.application.contract.DepartmentService;
import com.sans.emsapp.domain.model.Department;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIEndpoint.DEPARTMENT_ENDPOINT)
public class DepartmentController {

    private static final Logger logger = LogManager.getLogger(DepartmentService.class);
    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;
    private final PageMapper pageMapper;

    private ResponseEntity<APIResponse<PageResponse<?>>> getApiResponseResponseEntity(Page<Department> departmentPage) {
        List<DepartmentResponse> departmentResponses = departmentPage.getContent().stream()
                .map(departmentMapper::toResponse)
                .collect(Collectors.toList());

        PageResponse<?> response = pageMapper.toResponse(departmentPage, departmentResponses);

        return ResponseEntity.ok().body(
                APIResponse.<PageResponse<?>>builder()
                        .code(HttpStatus.OK.value())
                        .message(ResponseMessage.SUCCESS_GET_DATA)
                        .data(response)
                        .build()
        );
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<PageResponse<?>>> getAllDepartment(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Accessed Endpoint: " + APIEndpoint.DEPARTMENT_ENDPOINT + " to GET All Departments");
        Sort sort = Sort.by("name");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Department> departmentPage = departmentService.findAllDepartment(pageable);

        return getApiResponseResponseEntity(departmentPage);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<DepartmentResponse>> createDepartment(@RequestBody DepartmentRequest payload) {

        logger.info("Accessed Endpoint: " + APIEndpoint.DEPARTMENT_ENDPOINT + " to POST Department data");

        Department department = departmentService.saveDepartment(payload);
        DepartmentResponse response = departmentMapper.toResponse(department);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                APIResponse.<DepartmentResponse>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(ResponseMessage.SUCCESS_CREATE_DATA)
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<String>> deleteDepartment(@PathVariable("id") String id) {
        logger.info("Accessed Endpoint : " + APIEndpoint.DEPARTMENT_ENDPOINT + " to DELETE Department with id: {}" , id);

        departmentService.deleteDepartment(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                APIResponse.<String>builder()
                        .code(HttpStatus.OK.value())
                        .message(ResponseMessage.SUCCESS_DELETE_DATA)
                        .data("No Content")
                        .build()
        );
    }

    @GetMapping(
            path = "/result",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<PageResponse<?>>> getAllDepartmentByNames(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Accessed Endpoint: " + APIEndpoint.DEPARTMENT_ENDPOINT + " to GET All Departments by name: {}", name);

        Sort sort = Sort.by("name");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Department> departmentPage = departmentService.findDepartmentByName(name, pageable);

        return getApiResponseResponseEntity(departmentPage);
    }

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<DepartmentResponse>> updateDepartment(
            @PathVariable("id") String id,
            @RequestBody DepartmentRequest payload) {

        logger.info("Accessed Endpoint: " + APIEndpoint.DEPARTMENT_ENDPOINT + " to PUT Department with id: {}", id);

        Department department = departmentService.updateDepartment(id, payload);
        DepartmentResponse response = departmentMapper.toResponse(department);

        return ResponseEntity.status(HttpStatus.OK).body(
                APIResponse.<DepartmentResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                        .data(response)
                        .build()
        );
    }
}
