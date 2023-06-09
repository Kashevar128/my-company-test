package org.example.application.mappers;

import org.example.application.dto.EmployeeDto;
import org.example.application.dto.ProjectDto;
import org.example.application.model.Project;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ProjectsMapper {

    public Project mapToProject(ResultSet resultSet) {
        try {
            return Project.builder()
                    .id(resultSet.getInt("id"))
                    .projectName(resultSet.getString("project_name"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ProjectDto mapToProjectDto(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .build();
    }

    public ProjectDto mapToProjectDto(Project project, List<EmployeeDto> employeeDtoList) {
        return ProjectDto.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .employeeDtoList(employeeDtoList)
                .build();
    }

}
