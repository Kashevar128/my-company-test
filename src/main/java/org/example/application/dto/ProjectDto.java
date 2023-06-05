package org.example.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Data
@JsonInclude(value = NON_NULL)
public class ProjectDto {
    private int id;
    private String projectName;
    private List<EmployeeDto> employeeDtoList;
}
