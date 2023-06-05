package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.Exeptions.ResultException;
import org.example.application.api.Response;
import org.example.application.dto.EmployeeDto;
import org.example.application.mappers.EmployeesMapperDto;
import org.example.application.model.Employee;
import org.example.application.repositories.EmployersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    private final EmployersRepository employeesRepository;
    private final EmployeesMapperDto employeesMapperDto;


    public Response<?> getEmployees() {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        try {
            for (Employee employee : employeesRepository.getAllEmployers()) {
                employeeDtoList.add(employeesMapperDto.mapToEmployeeDto(employee));
            }
        } catch (ResultException e) {
            return Response.<String>builder()
                    .data(e.getMessage())
                    .success(false)
                    .build();
        }
        return Response.<List<EmployeeDto>>builder()
                .data(employeeDtoList)
                .success(true)
                .build();
    }

    public Response<?> getEmployeeById(int id) {
        Employee employeeById;
        EmployeeDto employeeDto;
        try {
            employeeById = employeesRepository.getEmployeeById(id);
            employeeDto = employeesMapperDto.mapToEmployeeDto(employeeById);
        } catch (ResultException e) {
            return Response.<String>builder()
                    .data(e.getMessage())
                    .success(false)
                    .build();
        }
        return Response.<EmployeeDto>builder()
                .data(employeeDto)
                .success(true)
                .build();
    }

    public Response<Integer> test() {
        return Response.<Integer>builder()
                .data(1001)
                .success(true)
                .build();
    }

}
