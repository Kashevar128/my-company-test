package org.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.CreateEmployeeRequest;
import org.example.application.api.UpdateEmployeeRequest;
import org.example.application.api.Response;
import org.example.application.services.EmployeesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeesController {

    private final EmployeesService employeesService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> getAllEmployees() {
        return employeesService.getAllEmployeesResponse();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> getEmployeeById(@PathVariable int id) {
        return employeesService.getEmployeeByIdResponse(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> createEmployee(@RequestBody CreateEmployeeRequest createEmployeeRequest) {
        return employeesService.createNewEmployeeResponse(createEmployeeRequest);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> updateEmployee(@RequestBody UpdateEmployeeRequest updateEmployeeRequest, @PathVariable int id) {
        return employeesService.updateEmployeeResponse(updateEmployeeRequest, id);
    }

    @DeleteMapping(value = "/{id}")
    public Response<?> deleteEmployee(@PathVariable int id) {
        return employeesService.deleteEmployeeResponse(id);
    }

}
