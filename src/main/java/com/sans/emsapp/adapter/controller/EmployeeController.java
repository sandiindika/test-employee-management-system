package com.sans.emsapp.adapter.controller;

import com.sans.emsapp.adapter.config.constant.APIEndpoint;
import com.sans.emsapp.adapter.config.constant.ResponseMessage;
import com.sans.emsapp.adapter.dto.request.EmployeeRequest;
import com.sans.emsapp.adapter.dto.response.APIResponse;
import com.sans.emsapp.adapter.dto.response.EmployeeResponse;
import com.sans.emsapp.adapter.dto.response.PageResponse;
import com.sans.emsapp.adapter.mapper.EmployeeMapper;
import com.sans.emsapp.adapter.mapper.PageMapper;
import com.sans.emsapp.application.contract.EmployeeService;
import com.sans.emsapp.domain.model.Employee;
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
@RequestMapping(APIEndpoint.EMPLOYEE_ENDPOINT)
public class EmployeeController {

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;
    private final PageMapper pageMapper;

    private ResponseEntity<APIResponse<PageResponse<?>>> getApiResponseResponseEntity(Page<Employee> employeePage) {
        List<EmployeeResponse> employeeResponses = employeePage.getContent().stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());

        PageResponse<?> response = pageMapper.toResponse(employeePage, employeeResponses);

        return ResponseEntity.ok().body(
                APIResponse.<PageResponse<?>>builder()
                        .code(HttpStatus.OK.value())
                        .message(ResponseMessage.SUCCESS_GET_DATA)
                        .data(response)
                        .build()
        );
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<PageResponse<?>>> getAllEmployee(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Accessed Endpoint: " + APIEndpoint.EMPLOYEE_ENDPOINT + " to GET All Employees");
        Sort sort = Sort.by("first_name");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employeePage = employeeService.findAllEmployees(pageable);
        return getApiResponseResponseEntity(employeePage);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<EmployeeResponse>> createEmployee(@RequestBody EmployeeRequest payload) {

        logger.info("Accessed Endpoint: " + APIEndpoint.EMPLOYEE_ENDPOINT + " to POST Employee data");

        Employee employee = employeeService.saveEmployee(payload);
        EmployeeResponse response = employeeMapper.toResponse(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                APIResponse.<EmployeeResponse>builder()
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
    public ResponseEntity<APIResponse<String>> deleteEmployee(@PathVariable("id") String id) {
        logger.info("Accessed Endpoint: " + APIEndpoint.EMPLOYEE_ENDPOINT + " to DELETE Employee with id: {}", id);

        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                APIResponse.<String>builder()
                        .code(HttpStatus.OK.value())
                        .message(ResponseMessage.SUCCESS_DELETE_DATA)
                        .data("No Content")
                        .build()
        );
    }

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<EmployeeResponse>> updateEmployee(
            @PathVariable("id") String id,
            @RequestBody EmployeeRequest payload) {

        logger.info("Accessed Endpoint: " + APIEndpoint.EMPLOYEE_ENDPOINT + " to PUT Employee with id: {}", id);

        Employee employee = employeeService.updateEmployee(id, payload);
        EmployeeResponse response = employeeMapper.toResponse(employee);

        return ResponseEntity.status(HttpStatus.OK).body(
                APIResponse.<EmployeeResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                        .data(response)
                        .build()
        );
    }

    @GetMapping(
            path = "/result",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<PageResponse<?>>> getAllEmployeeByName(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Accessed Endpoint: " + APIEndpoint.EMPLOYEE_ENDPOINT + " to GET All Employee by name: {}", name);

        Sort sort = Sort.by("first_name");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employeePage = employeeService.findEmployeeByName(name, pageable);

        return getApiResponseResponseEntity(employeePage);
    }

    @GetMapping(
            path = "/filter",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<PageResponse<?>>> getAllEmployeeSalaryAbove(
            @RequestParam(required = false, name = "salary") long salary,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Accessed Endpoint: " + APIEndpoint.EMPLOYEE_ENDPOINT + " to GET All Employee by salary above: {}", salary);

        Sort sort = Sort.by("firstName");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employeePage = employeeService.filterEmployeesBySalary(salary, pageable);

        return getApiResponseResponseEntity(employeePage);
    }

    @GetMapping(
            path = "/report",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<List<Object[]>>> getAllEmployeeByDepartment() {
        List<Object[]> result = employeeService.getEmployeeCountByDepartment();

        return ResponseEntity.status(HttpStatus.OK).body(
                APIResponse.<List<Object[]>>builder()
                        .code(HttpStatus.OK.value())
                        .message(ResponseMessage.SUCCESS_GET_DATA)
                        .data(result)
                        .build()
        );
    }
}
