package com.sans.emsapp.adapter.controller;

import com.sans.emsapp.adapter.config.constant.APIEndpoint;
import com.sans.emsapp.adapter.config.constant.ResponseMessage;
import com.sans.emsapp.adapter.dto.request.PositionRequest;
import com.sans.emsapp.adapter.dto.response.APIResponse;
import com.sans.emsapp.adapter.dto.response.PageResponse;
import com.sans.emsapp.adapter.dto.response.PositionResponse;
import com.sans.emsapp.adapter.mapper.PageMapper;
import com.sans.emsapp.adapter.mapper.PositionMapper;
import com.sans.emsapp.application.contract.DepartmentService;
import com.sans.emsapp.application.contract.PositionService;
import com.sans.emsapp.domain.model.Position;
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
@RequestMapping(path = APIEndpoint.POSITION_ENDPOINT)
public class PositionController {

    private static final Logger logger = LogManager.getLogger(PositionService.class);
    private final PositionService positionService;
    private final PositionMapper positionMapper;
    private final PageMapper pageMapper;

    private ResponseEntity<APIResponse<PageResponse<?>>> getApiResponseResponseEntity(Page<Position> positionPage) {
        List<PositionResponse> positionResponses = positionPage.getContent().stream()
                .map(positionMapper::toResponse)
                .collect(Collectors.toList());

        PageResponse<?> response = pageMapper.toResponse(positionPage, positionResponses);

        return ResponseEntity.ok().body(
                APIResponse.<PageResponse<?>>builder()
                        .code(HttpStatus.OK.value())
                        .message(ResponseMessage.SUCCESS_GET_DATA)
                        .data(response)
                        .build()
        );
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<PageResponse<?>>> getAllPosition(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Accessed Endpoint: " + APIEndpoint.POSITION_ENDPOINT + " to GET All Positions");

        Sort sort = Sort.by("description");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Position> positionPage = positionService.findAllPosition(pageable);

        return getApiResponseResponseEntity(positionPage);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<PositionResponse>> createPosition(@RequestBody PositionRequest payload) {

        logger.info("Accessed Endpoint: " + APIEndpoint.POSITION_ENDPOINT + " to POST Position data");

        Position position = positionService.savePosition(payload);
        PositionResponse response = positionMapper.toResponse(position);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                APIResponse.<PositionResponse>builder()
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
    public ResponseEntity<APIResponse<String>> deletePosition(@PathVariable("id") String id) {
        logger.info("Accessed Endpoint: " + APIEndpoint.POSITION_ENDPOINT + " to DELETE Position with id: {}", id);

        positionService.deletePosition(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                APIResponse.<String>builder()
                        .code(HttpStatus.OK.value())
                        .message(ResponseMessage.SUCCESS_DELETE_DATA)
                        .data("No content")
                        .build()
        );
    }

    @GetMapping(
            path = "/result",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<PageResponse<?>>> getAllPositionByNames(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Accessed Endpoint: " + APIEndpoint.POSITION_ENDPOINT + " to GET All Positions by name: {}", name);

        Sort sort = Sort.by("description");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Position> positionPage = positionService.findPositionByName(name, pageable);

        return getApiResponseResponseEntity(positionPage);
    }

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<PositionResponse>> updatePosition(
            @PathVariable("id") String id,
            @RequestBody PositionRequest payload) {

        logger.info("Accessed Endpoint: " + APIEndpoint.POSITION_ENDPOINT + " to PUT Position with id: {}", id);

        Position position = positionService.updatePosition(id, payload);
        PositionResponse response = positionMapper.toResponse(position);

        return ResponseEntity.status(HttpStatus.OK).body(
                APIResponse.<PositionResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                        .data(response)
                        .build()
        );
    }
}
