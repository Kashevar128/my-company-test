package org.example.application.mappers;

import org.example.application.dto.EmployeeDto;
import org.example.application.dto.PositionDto;
import org.example.application.dto.ProjectDto;
import org.example.application.model.Employee;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class EmployeesMapper {

    public EmployeeDto mapToEmployeeDto(Employee employee, PositionDto positionDto, List<ProjectDto> projectDtoList) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .age(employee.getAge())
                .position(positionDto)
                .projects(projectDtoList)
                .build();
    }

    public EmployeeDto mapToEmployeeDtoSimple(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .age(employee.getAge())
                .build();
    }


}
