package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.dto.PositionDto;
import org.example.application.exeptions.ResultException;
import org.example.application.mappers.PositionsMapper;
import org.example.application.model.Position;
import org.example.application.repositories.PositionsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionsService {

    private final PositionsRepository positionsRepository;
    private final PositionsMapper positionsMapper;

    public PositionDto getPositionDtoById(int id) throws ResultException {
        PositionDto positionDto;
        Position positionById = positionsRepository.getPositionById(id);
        positionDto = positionsMapper.mapToPositionDto(positionById);
        return positionDto;
    }
}
