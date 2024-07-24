package com.sans.emsapp.adapter.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeRequest {

    @NotBlank(message = "Name can't be empty")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email can't be empty")
    @Email
    private String email;

    @NotBlank(message = "Address can't be empty")
    private String address;

    @NotBlank(message = "Is not a valid phone number")
    @Size(min = 10, max = 15)
    @Pattern(regexp = "\\+?[0-9]+")
    private String phone;

    @NotNull
    private Long salary;

    @NotNull
    private String positionId;

    @NotNull
    private String departmentId;
}
