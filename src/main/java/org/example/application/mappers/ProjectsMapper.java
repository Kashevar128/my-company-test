package org.example.application.mappers;

import org.example.application.dto.EmployeeDto;
import org.example.application.dto.ProjectDto;
import org.example.application.model.Employee;
import org.example.application.model.Project;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectsMapper {


    public ProjectDto mapToProjectDtoSimple(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .build();
    }

    public ProjectDto mapToProjectDtoSimple(Project project, List<EmployeeDto> employeeDtoList) {
        return ProjectDto.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .employeeDtoList(employeeDtoList)
                .build();
    }

    public List<ProjectDto> mapToProjectDtoList(Employee employee) {
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for (Project project : employee.getProjects()) {
            ProjectDto projectDto = mapToProjectDtoSimple(project);
            projectDtoList.add(projectDto);
        }
        return projectDtoList;
    }
}
