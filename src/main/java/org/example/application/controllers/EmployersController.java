package org.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.example.application.dto.EmployeeDto;
import org.example.application.services.EmployeesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployersController {

    private final EmployeesService employeesService;

    @GetMapping(value = "/test",produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Integer> getTest() {
        return employeesService.test();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<EmployeeDto>> getEmployees() {
        return employeesService.getEmployees();
    }


}
