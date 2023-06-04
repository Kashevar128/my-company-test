package org.example.application.controllers;

import io.swagger.annotations.ApiOperation;;
import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployersController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Integer> getEmployers() {
        return Response.<Integer>builder()
                .data(45)
                .build();
    }
}
