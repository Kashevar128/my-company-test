package org.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.CreatePositionRequest;
import org.example.application.api.Response;
import org.example.application.services.PositionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionsController {

    private final PositionService positionService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> getAllPositions() {
        return positionService.getAllPositionsResponse();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> getPositionById(@PathVariable int id) {
        return positionService.getPositionDtoByIdResponse(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> createNewPosition(@RequestBody CreatePositionRequest createPositionRequest) {
        return positionService.createNewPositionResponse(createPositionRequest);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> updatePosition(@RequestBody CreatePositionRequest createPositionRequest, @PathVariable int id) {
        return positionService.updatePositionResponse(createPositionRequest, id);
    }

    @DeleteMapping(value = "/{id}")
    public Response<?> deletePosition(@PathVariable int id) {
        return positionService.deletePositionResponse(id);
    }

}
