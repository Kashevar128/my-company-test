package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.api.CreatePositionRequest;
import org.example.application.exeptions.ResultException;
import org.example.application.interfaces.MyCallback;
import org.example.application.model.Employee;
import org.example.application.model.Position;
import org.example.application.services.ConnectionService;
import org.example.application.services.HibernateService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
@RequiredArgsConstructor
public class PositionsRepository {

    private final ConnectionService connectionService;
    private final HibernateService hibernateService;

    public Position getPositionById(int id) throws ResultException {
        EntityManager entityManager = hibernateService.getEntityManager();
        MyCallback<Optional<Position>> myCallback = () -> Optional.ofNullable(entityManager.find(Position.class, id));
        Optional<Position> positionOpt = hibernateService.executeQuery(myCallback);
        if (!positionOpt.isPresent()) throw new RuntimeException("Нет такой позиции");
        else return positionOpt.get();
    }

    public List<Position> getAllPositions() throws ResultException {
        EntityManager entityManager = hibernateService.getEntityManager();
        MyCallback<Optional<List<Position>>> positionsCallback = () ->
                Optional
                        .ofNullable(entityManager
                                .createQuery("SELECT e FROM Position e", Position.class)
                                .getResultList());
        Optional<List<Position>> positions = hibernateService.executeQuery(positionsCallback);
        if (positions.isPresent()) return positions.get();
        else throw new ResultException("База данных пуста.");
    }

    public boolean savePosition(CreatePositionRequest createPositionRequest) {
        EntityManager entityManager = hibernateService.getEntityManager();
        MyCallback<Boolean> createPositionCallback = () -> {
            try {
                Position position = new Position();
                position.setPositionName(createPositionRequest.getPositionName());
                entityManager.persist(position);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        };
        return hibernateService.executeQuery(createPositionCallback);
    }

    public boolean updatePosition(CreatePositionRequest createPositionRequest, int id) {
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

            String positionName = createPositionRequest.getPositionName();
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
