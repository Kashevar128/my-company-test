package org.example.application.mappers;

import org.example.application.dto.EmployeeDto;
import org.example.application.dto.PositionDto;
import org.example.application.model.Employee;
import org.example.application.model.Position;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.util.List;

@Component
public class PositionsMapper {

    public Position mapToPosition(ResultSet resultSet) {
            return null;
    }

    public PositionDto mapToPositionDto(Position position) {
        return PositionDto.builder()
                .id(position.getId())
                .positionName(position.getPositionName())
                .build();
    }

    public PositionDto mapToPositionDto(Employee employee) {
        if (employee.getPosition() == null) return new PositionDto();
        return PositionDto.builder()
                .id(employee.getPosition().getId())
                .positionName(employee.getPosition().getPositionName())
                .build();
    }

    public PositionDto mapToPositionDto(Position position, List<EmployeeDto> employeeDtoList) {
        if (position == null) return new PositionDto();
        return PositionDto.builder()
                .id(position.getId())
                .positionName(position.getPositionName())
                .employeeDtoList(employeeDtoList)
                .build();
    }
}
