package org.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.example.application.services.PositionsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionsController {

    private final PositionsService positionsService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> getAllPositions() {
        return positionsService.getAllPositionsResponse();
    }

}
