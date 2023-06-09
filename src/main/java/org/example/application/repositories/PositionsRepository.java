package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.api.CreatePositionRequest;
import org.example.application.api.UpdatePositionRequest;
import org.example.application.exeptions.ResultException;
import org.example.application.interfaces.MyCallback;
import org.example.application.model.Position;
import org.example.application.services.HibernateService;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PositionsRepository {

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

    public boolean updatePosition(UpdatePositionRequest updatePositionRequest, int id) {
        try {
            String positionName = updatePositionRequest.getPositionName();

            EntityManager entityManager = hibernateService.getEntityManager();
            MyCallback<Boolean> updateCallable = () -> {
                Optional<Position> positionOpt = Optional.ofNullable(entityManager.find(Position.class, id));
                Position position;
                if (!positionOpt.isPresent()) return false;
                else position = positionOpt.get();

                Optional.ofNullable(positionName).ifPresent(position ::setPositionName);
                return true;
            };
            return hibernateService.executeQuery(updateCallable);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePosition(int id) {
        try {
            EntityManager entityManager = hibernateService.getEntityManager();
            MyCallback<Boolean> deletePositionCallback = () -> {
                Optional<Position> positionOpt = Optional.ofNullable(entityManager.find(Position.class, id));
                if (!positionOpt.isPresent()) return false;
                entityManager.remove(positionOpt.get());
                return true;
            };
            return hibernateService.executeQuery(deletePositionCallback);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
