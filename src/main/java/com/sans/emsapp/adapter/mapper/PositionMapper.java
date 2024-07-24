package com.sans.emsapp.adapter.mapper;

import com.sans.emsapp.adapter.dto.request.PositionRequest;
import com.sans.emsapp.adapter.dto.response.PositionResponse;
import com.sans.emsapp.domain.model.Position;
import org.springframework.stereotype.Component;

@Component
public class PositionMapper {

    public PositionResponse toResponse(Position position) {
        return PositionResponse.builder()
                .id(position.getId())
                .name(position.getName())
                .description(position.getDescription())
                .build();
    }

    public Position toModel(PositionRequest request) {
        return Position.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
