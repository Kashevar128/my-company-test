package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.exeptions.ResultException;
import org.example.application.mappers.EmployeesMapper;
import org.example.application.model.Employee;
import org.example.application.services.ConnectionService;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployersRepository {

    private final ConnectionService connectionService;
    private final EmployeesMapper employeesMapper;

    public List<Employee> getAllEmployees() throws ResultException {
        String query = "SELECT * FROM employees";

        List<Employee> employeesList;
        try (Connection connection = connectionService.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet == null) throw new ResultException("База данных пуста.");

            employeesList = employeesMapper.createEmployeeList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeesList;
    }

    public Employee getEmployeeById(int id) throws ResultException {
        String query = "SELECT * FROM employees WHERE id = ?";

        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet == null) throw new ResultException("Такого пользователя не существует.");

            return employeesMapper.mapToEmployee(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
