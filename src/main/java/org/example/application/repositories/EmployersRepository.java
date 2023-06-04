package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.mappers.EmployersMapper;
import org.example.application.model.Employee;
import org.example.application.services.ConnectionService;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployersRepository {

    private final ConnectionService connectionService;
    private final EmployersMapper employersMapper;

    public List<Employee> getAllEmployers() {
        String query = "SELECT e.id employeeId, e.first_name firstName, e.last_name lastName, e.email, e.age," +
                "p.id positionId, p.position_name positionName, j.id projectId, j.project_name projectName " +
                "FROM employees e " +
                "LEFT JOIN positions p ON e.id_position = p.id " +
                "LEFT JOIN employees_to_projects t ON t.id_employee = e.id " +
                "LEFT JOIN projects j ON t.id_project = j.id " +
                "ORDER BY e.id, j.id";
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = connectionService.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int previousId, currentId = 0;
            Employee employee;
            while (resultSet.next()) {
                previousId = currentId;
                currentId = resultSet.getInt("employeeId");
                if (currentId != previousId) {
                    employee = employersMapper.mapToEmployee(resultSet);
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }
}
