package org.example.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private int idPosition;
}
