package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.api.CreatePositionRequest;
import org.example.application.api.Response;
import org.example.application.api.UpdatePositionRequest;
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
public class PositionService {

    private final EmployeesRepository employeesRepository;
    private final PositionsRepository positionsRepository;
    private final PositionsMapper positionsMapper;
    private final EmployeesMapper employeesMapper;

    public Position getPositionById(int id) throws ResultException {
        return positionsRepository.getPositionById(id);
    }

    public Response<?> getPositionDtoByIdResponse(int id) throws ResultException {
        try {
            Position position = positionsRepository.getPositionById(id);
            List<EmployeeDto> employeeDtoList = new ArrayList<>();
            for (Employee employee : position.getEmployeeList()) {
                EmployeeDto employeeDto = employeesMapper.mapToEmployeeDtoSimple(employee);
                employeeDtoList.add(employeeDto);
            }
            PositionDto positionDto = positionsMapper.mapToPositionDto(position, employeeDtoList);
            return Response.<PositionDto>builder()
                    .data(positionDto)
                    .success(true)
                    .build();
        } catch (ResultException e) {
            return Response.<String>builder()
                    .data(e.getMessage())
                    .success(false)
                    .build();
        }
    }

    public Response<?> getAllPositionsResponse() {
        try {
            List<PositionDto> positionDtoList = new ArrayList<>();
            for (Position position : positionsRepository.getAllPositions()) {
                List<EmployeeDto> employeeDtoList = new ArrayList<>();
                for (Employee employee : position.getEmployeeList()) {
                    EmployeeDto employeeDto = employeesMapper.mapToEmployeeDtoSimple(employee);
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

    public Response<?> createNewPositionResponse(CreatePositionRequest createPositionRequest) {
        if (createPositionRequest == null) {
            return Response.<String>builder()
                    .data("Нулевой запрос.")
                    .success(false)
                    .build();
        }
        String positionName = createPositionRequest.getPositionName();
        if (positionName == null) {
            return Response.<String>builder()
                    .data("Название не заполнено.")
                    .success(false)
                    .build();
        }
        if (!positionsRepository.savePosition(createPositionRequest)) {
            return Response.<String>builder()
                    .data("Ошибка при создании позиции.")
                    .success(false)
                    .build();
        }
        return Response.<String>builder()
                .data("Позиция успешно создана.")
                .success(true)
                .build();
    }

    public Response<?> updatePositionResponse(UpdatePositionRequest updatePositionRequest,  int id) {
        if (updatePositionRequest == null) {
            return Response.<String>builder()
                    .data("Нулевой запрос.")
                    .success(false)
                    .build();
        }

        if (!positionsRepository.updatePosition(updatePositionRequest, id)) {
            return Response.<String>builder()
                    .data("Ошибка при обновлении позиции.")
                    .success(false)
                    .build();
        }
        return Response.<String>builder()
                .data("Позиция успешно обновлена.")
                .success(true)
                .build();
    }

    public Response<?> deletePositionResponse(int id) {
        if (!positionsRepository.deletePosition(id)) {
            return Response.<String>builder()
                    .data("Ошибка при удалении позиции.")
                    .success(false)
                    .build();
        }
        return Response.<String>builder()
                .data("Позиция успешно удалена.")
                .success(true)
                .build();
    }
}
