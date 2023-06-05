package org.example.application.mappers;

import org.example.application.dto.EmployeeDto;
import org.example.application.dto.PositionDto;
import org.example.application.dto.ProjectDto;
import org.example.application.model.Employee;
import org.example.application.model.Position;
import org.example.application.model.Project;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeesMapperDto {

    public EmployeeDto mapToEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .age(employee.getAge())
                .position(mapToPositionDto(employee.getPosition()))
                .projects(createProjectDtoList(employee.getProjects()))
                .build();
    }

    public PositionDto mapToPositionDto(Position position) {
        return PositionDto.builder()
                .id(position.getId())
                .positionName(position.getPositionName())
                .build();
    }

    public ProjectDto mapToProjectDto(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .build();
    }

    public List<ProjectDto> createProjectDtoList(List<Project> projectList) {
        List <ProjectDto> projectDtoList = new ArrayList<>();
        for (Project p: projectList) {
            projectDtoList.add(mapToProjectDto(p));
        }
        return projectDtoList;
    }

}
