package com.sans.emsapp.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "m_employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Name can't be empty")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Email can't be empty")
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Address can't be empty")
    private String address;

    @NotBlank(message = "Is not a valid phone number")
    @Size(min = 10, max = 15)
    @Pattern(regexp = "\\+?[0-9]+")
    @Column(name = "phone_number")
    private String phone;

    @NotNull
    private Long salary;

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
}
