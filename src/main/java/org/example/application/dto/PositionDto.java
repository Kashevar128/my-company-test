package org.example.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Data
@JsonInclude(value = NON_NULL)
public class PositionDto {
    private int id;
    private String positionName;
    private List<EmployeeDto> employeeDtoList;

}
