package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.api.EmployeeCreateRequest;
import org.example.application.api.EmployeeUpdateRequest;
import org.example.application.dto.PositionDto;
import org.example.application.dto.ProjectDto;
import org.example.application.exeptions.ResultException;
import org.example.application.api.Response;
import org.example.application.dto.EmployeeDto;
import org.example.application.mappers.EmployeesMapper;
import org.example.application.mappers.PositionsMapper;
import org.example.application.mappers.ProjectsMapper;
import org.example.application.model.Employee;
import org.example.application.repositories.EmployeesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    private final PositionsMapper positionsMapper;
    private final ProjectsMapper projectsMapper;
    private final EmployeesRepository employeesRepository;
    private final EmployeesMapper employeesMapper;

    public Response<?> getAllEmployeesResponse() {
        try {
            List<EmployeeDto> employeeDtoList = new ArrayList<>();
            for (Employee employee : employeesRepository.getAllEmployees()) {
                PositionDto positionDto = positionsMapper.mapToPositionDto(employee);
                List<ProjectDto> projectDtoList = projectsMapper.mapToProjectDtoList(employee);
                EmployeeDto employeeDto = employeesMapper.mapToEmployeeDto(employee, positionDto, projectDtoList);
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
            PositionDto positionDto = positionsMapper.mapToPositionDto(employee);
            List<ProjectDto> projectDtoList = projectsMapper.mapToProjectDtoList(employee);
            EmployeeDto employeeDto = employeesMapper.mapToEmployeeDto(employee, positionDto, projectDtoList);
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

    public Response<?> createNewEmployeeResponse(EmployeeCreateRequest employeeCreateRequest) {
        if (employeeCreateRequest == null) {
            return Response.<String>builder()
                    .data("Нулевой запрос.")
                    .success(false)
                    .build();
        }
        String firstName = employeeCreateRequest.getFirstName();
        String lastName = employeeCreateRequest.getLastName();
        String email = employeeCreateRequest.getEmail();
        Integer age = employeeCreateRequest.getAge();
        if (firstName == null || lastName == null || email == null || age == null) {
            return Response.<String>builder()
                    .data("Заполнены не все поля.")
                    .success(false)
                    .build();
        }
        if (!employeesRepository.saveEmployee(employeeCreateRequest)) {
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

    public Response<?> updateEmployeeResponse(EmployeeUpdateRequest employeeUpdateRequest, int id) {
        if (employeeUpdateRequest == null) {
            return Response.<String>builder()
                    .data("Нулевой запрос.")
                    .success(false)
                    .build();
        }

        if (!employeesRepository.updateEmployee(employeeUpdateRequest, id)) {
            return Response.<String>builder()
                    .data("Ошибка при обновлении данных сотрудника.")
                    .success(false)
                    .build();
        }
        return Response.<String>builder()
                .data("Сотрудник успешно обновлен.")
                .success(true)
                .build();
    }

    public Response<?> deleteEmployeeResponse(int id) {
        if (!employeesRepository.deleteEmployee(id)) {
            return Response.<String>builder()
                    .data("Ошибка при удалении сотрудника.")
                    .success(false)
                    .build();
        }
        return Response.<String>builder()
                .data("Сотрудник успешно удален.")
                .success(true)
                .build();
    }

//    public List<EmployeeDto> getEmployeeDtoListByPositionId(int id) {
//        List<Employee> employeeDtoListByPositionId = employeesRepository.getEmployeeListByPositionId(id);
//        List<EmployeeDto> employeeDtoList = new ArrayList<>();
//        for (Employee employee : employeeDtoListByPositionId) {
//            EmployeeDto employeeDto = employeesMapper.mapToEmployeeDto(employee);
//            employeeDtoList.add(employeeDto);
//        }
//        return employeeDtoList;
//    }
//



}
