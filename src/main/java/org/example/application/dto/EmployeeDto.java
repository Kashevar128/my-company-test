package org.example.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Builder
@Data
@JsonInclude(value = NON_NULL)
public class EmployeeDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private PositionDto position;
    private List<ProjectDto> projects;
}
