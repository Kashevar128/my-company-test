package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.dto.PositionDto;
import org.example.application.dto.ProjectDto;
import org.example.application.exeptions.ResultException;
import org.example.application.api.Response;
import org.example.application.dto.EmployeeDto;
import org.example.application.mappers.EmployeesMapper;
import org.example.application.model.Employee;
import org.example.application.repositories.EmployersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    private final PositionsService positionsService;
    private final ProjectService projectService;
    private final EmployersRepository employeesRepository;
    private final EmployeesMapper employeesMapper;

    public Response<?> getEmployeesResponse() {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        try {
            for (Employee employee : employeesRepository.getAllEmployers()) {
                PositionDto positionDtoById = positionsService.getPositionDtoById(employee.getIdPosition());
                List<ProjectDto> projectDtoListById = projectService.getProjectDtoListById(employee.getId());
                EmployeeDto employeeDto = employeesMapper.mapToEmployeeDto(employee, positionDtoById, projectDtoListById);
                employeeDtoList.add(employeeDto);
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

    public Response<Integer> test() {
        return Response.<Integer>builder()
                .data(1001)
                .success(true)
                .build();
    }

}
