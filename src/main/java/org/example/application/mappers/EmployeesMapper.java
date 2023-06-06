package org.example.application.mappers;

import org.example.application.dto.EmployeeDto;
import org.example.application.dto.PositionDto;
import org.example.application.dto.ProjectDto;
import org.example.application.model.Employee;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class EmployeesMapper {

    public Employee mapToEmployee(ResultSet resultSet) {
        try {
            return Employee.builder()
                    .id(resultSet.getInt("id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .email(resultSet.getString("email"))
                    .age(resultSet.getInt("age"))
                    .idPosition(resultSet.getInt("id_position"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    public EmployeeDto mapToEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .age(employee.getAge())
                .build();
    }

    public EmployeeDto mapToEmployeeDto(Employee employee, PositionDto positionDto) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .age(employee.getAge())
                .position(positionDto)
                .build();
    }
}
