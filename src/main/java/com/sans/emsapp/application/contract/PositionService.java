package com.sans.emsapp.application.contract;

import com.sans.emsapp.adapter.dto.request.PositionRequest;
import com.sans.emsapp.domain.model.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PositionService {

    Page<Position> findAllPosition(Pageable pageable);
    Position savePosition(PositionRequest request);
    void deletePosition(String id);
    Page<Position> findPositionByName(String keyword, Pageable pageable);
    Position updatePosition(String id, PositionRequest request);
}