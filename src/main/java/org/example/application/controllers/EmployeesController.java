package org.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.EmployeeCreateRequest;
import org.example.application.api.EmployeeUpdateRequest;
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
    public Response<?> createEmployee(@RequestBody EmployeeCreateRequest employeeCreateRequest) {
        return employeesService.createNewEmployeeResponse(employeeCreateRequest);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> updateEmployee(@RequestBody EmployeeUpdateRequest employeeUpdateRequest, @PathVariable int id) {
        return employeesService.updateEmployeeResponse(employeeUpdateRequest, id);
    }

    @DeleteMapping(value = "/{id}")
    public Response<?> deleteEmployee(@PathVariable int id) {
        return employeesService.deleteEmployeeResponse(id);
    }

}
