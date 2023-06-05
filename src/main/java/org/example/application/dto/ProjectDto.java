package org.example.application.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Builder
@Data
public class ProjectDto {
    private int id;
    private String projectName;

}
