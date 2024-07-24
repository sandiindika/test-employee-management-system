package com.sans.emsapp.adapter.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PositionRequest {

    @NotBlank(message = "Name can't be empty")
    private String name;

    private String description;
}
