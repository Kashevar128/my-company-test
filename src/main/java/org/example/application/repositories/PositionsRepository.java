package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.exeptions.ResultException;
import org.example.application.mappers.PositionsMapper;
import org.example.application.model.Position;
import org.example.application.services.ConnectionService;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@RequiredArgsConstructor
public class PositionsRepository {

    private final ConnectionService connectionService;
    private final PositionsMapper positionsMapper;

    public Position getPositionById(int id) throws ResultException {
        String query = "SELECT * FROM positions p WHERE p.id = ?";
        Position position;
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet == null) throw new ResultException("Такой должности не существует.");

            position = positionsMapper.mapToPosition(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return position;
    }
}
