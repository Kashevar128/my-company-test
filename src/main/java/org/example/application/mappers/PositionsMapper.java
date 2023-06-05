package org.example.application.mappers;

import org.example.application.dto.EmployeeDto;
import org.example.application.dto.PositionDto;
import org.example.application.model.Position;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class PositionsMapper {

    public Position mapToPosition(ResultSet resultSet) {
        try {
            return Position.builder()
                    .id(resultSet.getInt("id"))
                    .positionName(resultSet.getString("position_name"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PositionDto mapToPositionDto(Position position) {
        return PositionDto.builder()
                .id(position.getId())
                .positionName(position.getPositionName())
                .build();
    }

    public PositionDto mapToPositionDto(Position position, List<EmployeeDto> employeeDtoList) {
        return PositionDto.builder()
                .id(position.getId())
                .positionName(position.getPositionName())
                .employeeDtoList(employeeDtoList)
                .build();
    }
}
