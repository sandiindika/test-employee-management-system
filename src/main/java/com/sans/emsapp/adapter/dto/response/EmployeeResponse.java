package com.sans.emsapp.adapter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private Long salary;
    private String positionName;
    private String departmentName;
}
