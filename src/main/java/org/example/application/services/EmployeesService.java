package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.api.EmployeeRequest;
import org.example.application.dto.PositionDto;
import org.example.application.dto.ProjectDto;
import org.example.application.exeptions.ResultException;
import org.example.application.api.Response;
import org.example.application.dto.EmployeeDto;
import org.example.application.mappers.EmployeesMapper;
import org.example.application.model.Employee;
import org.example.application.repositories.EmployeesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    private final PositionsService positionsService;
    private final ProjectService projectService;
    private final EmployeesRepository employeesRepository;
    private final EmployeesMapper employeesMapper;

    public Response<?> getAllEmployeesResponse() {
        try {
            List<EmployeeDto> employeeDtoList = new ArrayList<>();
            for (Employee employee : employeesRepository.getAllEmployees()) {
                PositionDto positionDtoById = positionsService.getPositionDtoById(employee.getIdPosition());
                List<ProjectDto> projectDtoListById = projectService.getProjectDtoListById(employee.getId());
                EmployeeDto employeeDto = employeesMapper.mapToEmployeeDto(employee, positionDtoById, projectDtoListById);
                employeeDtoList.add(employeeDto);
            }
            return Response.<List<EmployeeDto>>builder()
                    .data(employeeDtoList)
                    .success(true)
                    .build();
        } catch (ResultException e) {
            return Response.<String>builder()
                    .data(e.getMessage())
                    .success(false)
                    .build();
        }
    }

    public Response<?> getEmployeeByIdResponse(int id) {
        try {
            Employee employee = employeesRepository.getEmployeeById(id);
            PositionDto positionDtoById = positionsService.getPositionDtoById(employee.getIdPosition());
            List<ProjectDto> projectDtoListById = projectService.getProjectDtoListById(employee.getId());
            EmployeeDto employeeDto = employeesMapper.mapToEmployeeDto(employee, positionDtoById, projectDtoListById);
            return Response.<EmployeeDto>builder()
                    .data(employeeDto)
                    .success(true)
                    .build();
        } catch (ResultException e) {
            return Response.<String>builder()
                    .data(e.getMessage())
                    .success(false)
                    .build();
        }
    }

    public Response<?> createNewEmployeeResponse(EmployeeRequest employeeRequest) {
        if (employeeRequest == null) {
            return Response.<String>builder()
                    .data("Нулевой запрос.")
                    .success(false)
                    .build();
        }
        String firstName = employeeRequest.getFirstName();
        String lastName = employeeRequest.getLastName();
        String email = employeeRequest.getEmail();
        Integer age = employeeRequest.getAge();
        if (firstName == null || lastName == null || email == null || age == null) {
            return Response.<String>builder()
                    .data("Заполнены не все поля.")
                    .success(false)
                    .build();
        }
        if (!employeesRepository.createEmployee(employeeRequest)) {
            return Response.<String>builder()
                    .data("Ошибка при создании сотрудника.")
                    .success(false)
                    .build();
        }
        return Response.<String>builder()
                .data("Пользователь успешно создан.")
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
