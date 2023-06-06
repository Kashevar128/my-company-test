package org.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.ProjectRequest;
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
    public Response<?> createNewProject(@RequestBody ProjectRequest projectRequest) {
        return projectService.createNewProjectResponse(projectRequest);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<?> updateProject(@RequestBody ProjectRequest projectRequest, @PathVariable int id) {
        return projectService.updateProjectResponse(projectRequest, id);
    }

    @DeleteMapping(value = "/{id}")
    public Response<?> deletePosition(@PathVariable int id) {
        return projectService.deleteProjectResponse(id);
    }
}
