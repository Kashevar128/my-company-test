package org.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.application.api.Response;
import org.example.application.services.ProjectService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
