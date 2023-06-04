package org.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.example.application.model.Employee;
import org.example.application.services.EmployeesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployersController {

    private final EmployeesService employeesService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getEmployees() {
        return employeesService.getEmployees();
    }

    @GetMapping(value = "/test",produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Integer> getTest() {
        return Response.<Integer>builder()
                .data(1001)
                .build();
    }


}
