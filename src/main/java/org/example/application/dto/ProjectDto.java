package org.example.application.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProjectDto {
    private int id;
    private String projectName;
}
