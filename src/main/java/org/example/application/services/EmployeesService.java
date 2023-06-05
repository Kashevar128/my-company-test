package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.Exeptions.ResultSetException;
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

    private final EmployersRepository employersRepository;
    private final EmployeesMapperDto employeesMapperDto;


    public Response<List<EmployeeDto>> getEmployees() {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        try {
            for (Employee employee : employersRepository.getAllEmployers()) {
                employeeDtoList.add(employeesMapperDto.mapToEmployeeDto(employee));
            }
        } catch (ResultSetException e) {
            return Response.<List<EmployeeDto>>builder()
                    .data(null)
                    .success(false)
                    .build();
        }
        return Response.<List<EmployeeDto>>builder()
                .data(employeeDtoList)
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
