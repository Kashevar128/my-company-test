package org.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.CreateProjectRequest;
import org.example.application.api.Response;
import org.example.application.services.ProjectService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> getAllProjects() {
        return projectService.getAllProjectsResponse();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> getProjectById(@PathVariable int id) {
        return projectService.getProjectDtoByIdResponse(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> createNewProject(@RequestBody CreateProjectRequest createProjectRequest) {
        return projectService.createNewProjectResponse(createProjectRequest);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> updateProject(@RequestBody CreateProjectRequest createProjectRequest, @PathVariable int id) {
        return projectService.updateProjectResponse(createProjectRequest, id);
    }

    @DeleteMapping(value = "/{id}")
    public Response<?> deletePosition(@PathVariable int id) {
        return projectService.deleteProjectResponse(id);
    }
}
