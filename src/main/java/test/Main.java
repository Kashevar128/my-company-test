package test;

import org.example.application.mappers.PositionsMapper;
import org.example.application.repositories.PositionsRepository;
import org.example.application.services.ConnectionService;

public class Main {

    public static void main(String[] args) {

        ConnectionService connectionService = new ConnectionService();
        PositionsMapper positionsMapper = new PositionsMapper();
        PositionsRepository positionsRepository = new PositionsRepository(connectionService, positionsMapper);
        positionsRepository.getPositionById(3);
    }
}
