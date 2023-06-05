package org.example.application.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleEmployee {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private int positionId;
    private String positionName;
    private int projectId;
    private String projectName;
}
