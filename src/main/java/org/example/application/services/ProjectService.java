package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.dto.ProjectDto;
import org.example.application.exeptions.ResultException;
import org.example.application.mappers.ProjectsMapper;
import org.example.application.model.Project;
import org.example.application.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectsMapper projectsMapper;

    public List<ProjectDto> getProjectDtoListById(int id) throws ResultException {
        List<Project> projectsEmployeeById = projectRepository.getProjectsEmployeeById(id);
        return projectsMapper.createProjectDtoList(projectsEmployeeById);
    }
}
