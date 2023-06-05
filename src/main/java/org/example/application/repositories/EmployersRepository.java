package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.exeptions.ResultException;
import org.example.application.mappers.EmployeesMapper;
import org.example.application.model.Employee;
import org.example.application.services.ConnectionService;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployersRepository {

    private final ConnectionService connectionService;
    private final EmployeesMapper employeesMapper;

    public List<Employee> getAllEmployers() throws ResultException {
        String query = "SELECT * FROM employees";

        List<Employee> employeesList = new ArrayList<>();
        try (Connection connection = connectionService.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet == null) throw new ResultException("База данных пуста.");

            while (resultSet.next()) {
                Employee employee = employeesMapper.mapToEmployee(resultSet);
                employeesList.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeesList;
    }
}
