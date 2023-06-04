package org.example.application.mappers;

import org.example.application.model.Employee;
import org.example.application.model.Position;
import org.example.application.model.Project;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployersMapper {

    public Employee mapToEmployee(ResultSet resultSet) {
        try {
            int empId = resultSet.getInt("employeeId");
            return Employee.builder()
                    .id(empId)
                    .firstName(resultSet.getString("firstName"))
                    .lastName(resultSet.getString("lastName"))
                    .position(mapToPosition(resultSet))
                    .projects(createProjectsList(resultSet, empId))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Position mapToPosition(ResultSet resultSet) {
        try {
            return Position.builder()
                    .id(resultSet.getInt("positionId"))
                    .positionName(resultSet.getString("positionName"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Project mapToProject(ResultSet resultSet) {
        try {
            return Project.builder()
                    .id(resultSet.getInt("projectId"))
                    .projectName(resultSet.getString("projectName"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Project> createProjectsList(ResultSet resultSet, int id) {
        List<Project> projects = new ArrayList<>();
        int empId;
        try {
            while (resultSet.next()) {
                empId = resultSet.getInt("employeeId");
                if (empId == id) {
                    Project project = mapToProject(resultSet);
                    projects.add(project);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return projects;
    }
}
