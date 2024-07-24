package com.sans.emsapp.application.service;

import com.sans.emsapp.adapter.dto.request.PositionRequest;
import com.sans.emsapp.adapter.mapper.PositionMapper;
import com.sans.emsapp.application.contract.PositionService;
import com.sans.emsapp.domain.model.Position;
import com.sans.emsapp.infrastructure.repository.PositionRepository;
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
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<Position> findAllPosition(Pageable pageable) {
        Page<Position> positions = positionRepository.findAll(pageable);
        if (positions.getTotalElements() <= 0) throw new EntityNotFoundException("The resource you requested is empty");
        return positions;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Position savePosition(PositionRequest request) {
        return positionRepository.save(positionMapper.toModel(request));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePosition(String id) {
        Position position = positionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("The data you requested is not found. ID Position: " + id));
        positionRepository.delete(position);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Position> findPositionByName(String keyword, Pageable pageable) {
        List<Position> filteredPositions = positionRepository.findAll(pageable).getContent().stream()
                .filter(pst -> pst.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        if (filteredPositions.isEmpty()) throw new EntityNotFoundException("The resource you requested is empty");
        return new PageImpl<>(filteredPositions, pageable, filteredPositions.size());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Position updatePosition(String id, PositionRequest request) {
        Position position = positionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("The data you requested is not found. ID Position: " + id));

        position.setName(request.getName());
        position.setDescription(request.getDescription());
        return positionRepository.save(position);
    }
}
