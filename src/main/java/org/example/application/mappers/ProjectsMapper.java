package org.example.application.mappers;

import org.example.application.dto.ProjectDto;
import org.example.application.model.Project;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectsMapper {


    public List<Project> createProjectList(ResultSet resultSet) {
        List<Project> projectList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Project project = mapToProject(resultSet);
                projectList.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projectList;
    }

    public List<ProjectDto> createProjectDtoList(List<Project> projectList) {
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for (Project project : projectList) {
            ProjectDto projectDto = mapToProjectDto(project);
            projectDtoList.add(projectDto);
        }
        return projectDtoList;
    }

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

}
