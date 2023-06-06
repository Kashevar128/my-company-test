package org.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.PositionRequest;
import org.example.application.api.Response;
import org.example.application.services.PositionsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionsController {

    private final PositionsService positionsService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> getAllPositions() {
        return positionsService.getAllPositionsResponse();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> getPositionById(@PathVariable int id) {
        return positionsService.getPositionDtoByIdResponse(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> createNewPosition(@RequestBody PositionRequest positionRequest) {
        return positionsService.createNewPositionResponse(positionRequest);
    }

}
