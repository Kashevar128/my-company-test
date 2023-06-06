package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.example.application.dto.EmployeeDto;
import org.example.application.dto.PositionDto;
import org.example.application.dto.ProjectDto;
import org.example.application.exeptions.ResultException;
import org.example.application.mappers.EmployeesMapper;
import org.example.application.mappers.ProjectsMapper;
import org.example.application.model.Employee;
import org.example.application.model.Project;
import org.example.application.repositories.EmployeesRepository;
import org.example.application.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectsMapper projectsMapper;
    private final EmployeesRepository employeesRepository;
    private final EmployeesMapper employeesMapper;
    private final PositionsService positionsService;

    public List<ProjectDto> getProjectDtoListById(int id) throws ResultException {
        List<Project> projectsEmployeeById = projectRepository.getProjectsEmployeeById(id);
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for (Project project : projectsEmployeeById) {
            ProjectDto projectDto = projectsMapper.mapToProjectDto(project);
            projectDtoList.add(projectDto);
        }
        return projectDtoList;
    }

    public Response<?> getAllProjectsResponse() {
        try {
            List<ProjectDto> projectDtoList = new ArrayList<>();
            for (Project project : projectRepository.getAllProjects()) {
                List<Employee> employeeByProjectId = employeesRepository.getEmployeeByProjectId(project.getId());
                List<EmployeeDto> employeeDtoList = new ArrayList<>();
                for (Employee employee : employeeByProjectId) {
                    PositionDto positionDtoById = positionsService.getPositionDtoById(employee.getIdPosition());
                    EmployeeDto employeeDto = employeesMapper.mapToEmployeeDto(employee, positionDtoById);
                    employeeDtoList.add(employeeDto);
                }
                ProjectDto projectDto = projectsMapper.mapToProjectDto(project, employeeDtoList);
                projectDtoList.add(projectDto);
            }
            return Response.<List<ProjectDto>>builder()
                    .data(projectDtoList)
                    .success(true)
                    .build();
        } catch (ResultException e) {
            return Response.<String>builder()
                    .data(e.getMessage())
                    .success(false)
                    .build();
        }
    }

    public Response<?> getProjectDtoByIdResponse(int id) throws ResultException {
        try {
            Project project = projectRepository.getProjectById(id);
            List<Employee> employeeByProjectId = employeesRepository.getEmployeeByProjectId(project.getId());
            List<EmployeeDto> employeeDtoList = new ArrayList<>();
            for (Employee employee : employeeByProjectId) {
                PositionDto positionDtoById = positionsService.getPositionDtoById(employee.getIdPosition());
                EmployeeDto employeeDto = employeesMapper.mapToEmployeeDto(employee, positionDtoById);
                employeeDtoList.add(employeeDto);
            }
            ProjectDto projectDto = projectsMapper.mapToProjectDto(project, employeeDtoList);
            return Response.<ProjectDto>builder()
                    .data(projectDto)
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
