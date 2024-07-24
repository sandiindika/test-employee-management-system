package com.sans.emsapp.application.service;

import com.sans.emsapp.adapter.config.constant.ResponseMessage;
import com.sans.emsapp.domain.model.Position;
import com.sans.emsapp.infrastructure.repository.PositionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PositionServiceImplTest {

    @Mock
    private PositionRepository positionRepository;

    @InjectMocks
    private PositionServiceImpl positionService;

    private Pageable pageable;
    private Position position;

    @BeforeEach
    public void setUp() {
        pageable = PageRequest.of(0, 10);
        position = Position.builder()
                .name("HRD")
                .description("Human Resource Development")
                .build();
    }

    @Test
    @DisplayName("Test find all position - Success")
    public void testFindAllPosition_Success() {
        Page<Position> positions = new PageImpl<>(Collections.singletonList(position));
        when(positionRepository.findAll(any(Pageable.class))).thenReturn(positions);

        Page<Position> result = positionService.findAllPosition(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("Test find all position - Data is Empty")
    public void testFindAllPosition_Empty() {
        Page<Position> emptyPage = new PageImpl<>(Collections.emptyList());
        when(positionRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                positionService.findAllPosition(pageable));
        assertEquals(ResponseMessage.ERROR_NOT_FOUND, exception.getMessage());
    }
}