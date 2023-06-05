package org.example.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Builder
@Data
public class EmployeeDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private PositionDto position;
    private List<ProjectDto> projects;
}
