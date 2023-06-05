package org.example.application.mappers;

import org.example.application.api.SimpleEmployee;
import org.example.application.model.Employee;
import org.example.application.model.Position;
import org.example.application.model.Project;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeesMapper {

    public SimpleEmployee mapToSimpleEmployee(ResultSet resultSet) {
        try {
            SimpleEmployee simpleEmployee;
            simpleEmployee =  SimpleEmployee.builder()
                    .id(resultSet.getInt("employeeId"))
                    .firstName(resultSet.getString("firstName"))
                    .lastName(resultSet.getString("lastName"))
                    .email(resultSet.getString("email"))
                    .age(resultSet.getInt("age"))
                    .positionId(resultSet.getInt("positionId"))
                    .positionName(resultSet.getString("positionName"))
                    .projectId(resultSet.getInt("projectId"))
                    .projectName(resultSet.getString("projectName"))
                    .build();
            return simpleEmployee;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<Employee> creatEmployeesList(List<SimpleEmployee> simpleEmployeeList) {
        int previousId, currentId = 0;
        Employee employee;
        List<Employee> employeeList = new ArrayList<>();
        for (SimpleEmployee simpleEmployee : simpleEmployeeList) {
            previousId = currentId;
            currentId = simpleEmployee.getId();
            if (currentId != previousId) {
                employee = mapToEmployee(simpleEmployee, simpleEmployeeList);
                employeeList.add(employee);
            }
        }
        return employeeList;
    }

    public Employee mapToEmployee(SimpleEmployee simpleEmployee, List<SimpleEmployee> simpleEmployeeList) {
        return Employee.builder()
                .id(simpleEmployee.getId())
                .firstName(simpleEmployee.getFirstName())
                .lastName(simpleEmployee.getLastName())
                .email(simpleEmployee.getEmail())
                .age(simpleEmployee.getAge())
                .projects(createProjectsList(simpleEmployeeList, simpleEmployee.getId()))
                .position(mapToPosition(simpleEmployee))
                .build();
    }

    public Position mapToPosition(SimpleEmployee simpleEmployee) {
            return Position.builder()
                    .id(simpleEmployee.getPositionId())
                    .positionName(simpleEmployee.getPositionName())
                    .build();
    }

    public Project mapToProject(SimpleEmployee simpleEmployee) {
        return Project.builder()
                .id(simpleEmployee.getProjectId())
                .projectName(simpleEmployee.getProjectName())
                .build();
    }

    private List<Project> createProjectsList(List<SimpleEmployee> simpleEmployeeList, int id) {
        boolean findId = false;
        List<Project> projectList = new ArrayList<>();
        for (SimpleEmployee simpleEmployee : simpleEmployeeList) {
            if (simpleEmployee.getId() == id) {
                projectList.add(mapToProject(simpleEmployee));
                findId = true;
            } else {
                if (findId) return projectList;
            }
        }
        return projectList;
    }
}
