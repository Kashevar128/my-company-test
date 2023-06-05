package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.example.application.dto.EmployeeDto;
import org.example.application.dto.PositionDto;
import org.example.application.exeptions.ResultException;
import org.example.application.mappers.EmployeesMapper;
import org.example.application.mappers.PositionsMapper;
import org.example.application.model.Employee;
import org.example.application.model.Position;
import org.example.application.repositories.EmployeesRepository;
import org.example.application.repositories.PositionsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionsService {

    //private final EmployeesService employeesService;
    private final EmployeesRepository employeesRepository;
    private final PositionsRepository positionsRepository;
    private final PositionsMapper positionsMapper;
    private final EmployeesMapper employeesMapper;

    public PositionDto getPositionDtoById(int id) throws ResultException {
        PositionDto positionDto;
        Position positionById = positionsRepository.getPositionById(id);
        positionDto = positionsMapper.mapToPositionDto(positionById);
        return positionDto;
    }

    public Response<?> getAllPositionsResponse() {
        try {
            List<PositionDto> positionDtoList = new ArrayList<>();
            for (Position position : positionsRepository.getAllPositions()) {
                List<Employee> employeeDtoListByPositionId = employeesRepository.getEmployeeListByPositionId(position.getId());
                List<EmployeeDto> employeeDtoList = new ArrayList<>();
                for (Employee employee : employeeDtoListByPositionId) {
                    EmployeeDto employeeDto = employeesMapper.mapToEmployeeDto(employee);
                    employeeDtoList.add(employeeDto);
                }
                PositionDto positionDto = positionsMapper.mapToPositionDto(position, employeeDtoList);
                positionDtoList.add(positionDto);
            }
            return Response.<List<PositionDto>>builder()
                    .data(positionDtoList)
                    .success(true)
                    .build();
        } catch (ResultException e) {
            return Response.<String>builder()
                    .data(e.getMessage())
                    .success(false)
                    .build();
        }
    }
}
