package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.api.PositionRequest;
import org.example.application.exeptions.ResultException;
import org.example.application.mappers.PositionsMapper;
import org.example.application.model.Position;
import org.example.application.services.ConnectionService;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
@RequiredArgsConstructor
public class PositionsRepository {

    private final ConnectionService connectionService;
    private final PositionsMapper positionsMapper;

    public Position getPositionById(int id) throws ResultException {
        String query = "SELECT * FROM positions p WHERE p.id = ?";
        Position position = null;
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet == null) throw new ResultException("Такой должности не существует.");

            while (resultSet.next()) {
                position = positionsMapper.mapToPosition(resultSet);
            }
            return position;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Position> getAllPositions() throws ResultException {
        String query = "SELECT * FROM positions";

        List<Position> positionList = new ArrayList<>();
        try (Connection connection = connectionService.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet == null) throw new ResultException("База данных пуста.");

            while (resultSet.next()) {
                Position position = positionsMapper.mapToPosition(resultSet);
                positionList.add(position);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return positionList;
    }

    public boolean savePosition(PositionRequest positionRequest) {
        String query = "INSERT INTO positions (position_name) VALUES (?)";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, positionRequest.getPositionName());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePosition(PositionRequest positionRequest, int id) {
        String positionNameSQL = "UPDATE positions SET position_name = ? WHERE id = ?";

        try (Connection connection = connectionService.getConnection()){
            Consumer<String> positionNameConsumer = (positionName) -> {
                PreparedStatement statement;
                try {
                    statement = connection.prepareStatement(positionNameSQL);
                    statement.setString(1, positionName);
                    statement.setInt(2, id);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            };

            String positionName = positionRequest.getPositionName();
            Optional.ofNullable(positionName).ifPresent(positionNameConsumer);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deletePosition(int id) {
        String query  = "DELETE FROM positions WHERE id = ?";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
