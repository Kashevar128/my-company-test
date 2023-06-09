package org.example.application.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private Integer positionId;
    private List<Integer> projectsId;
}
