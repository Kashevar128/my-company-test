package org.example.application.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "projects_to_employees",
            joinColumns = @JoinColumn(name = "id_project"),
            inverseJoinColumns = @JoinColumn(name = "id_employee")
    )
    private List<Employee> employees;

}
