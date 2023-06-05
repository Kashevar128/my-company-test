package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.Exeptions.ResultException;
import org.example.application.api.SimpleEmployee;
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
        String query = "SELECT e.id employeeId, e.first_name firstName, e.last_name lastName, e.email email, e.age age," +
                "p.id positionId, p.position_name positionName, j.id projectId, j.project_name projectName " +
                "FROM employees e " +
                "LEFT JOIN positions p ON e.id_position = p.id " +
                "LEFT JOIN employees_to_projects t ON t.id_employee = e.id " +
                "LEFT JOIN projects j ON t.id_project = j.id " +
                "ORDER BY e.id, j.id";
        List<SimpleEmployee> simpleEmployees = new ArrayList<>();
        try (Connection connection = connectionService.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            SimpleEmployee simpleEmployee;

            if (resultSet == null) throw new ResultException("База данных пуста");

            while (resultSet.next()) {
                simpleEmployee = employeesMapper.mapToSimpleEmployee(resultSet);
                simpleEmployees.add(simpleEmployee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeesMapper.creatEmployeesList(simpleEmployees);
    }

    public Employee getEmployeeById(int id) throws ResultException {
        String query = "SELECT e.id employeeId, e.first_name firstName, e.last_name lastName, e.email email, e.age age," +
                "p.id positionId, p.position_name positionName, j.id projectId, j.project_name projectName " +
                "FROM employees e " +
                "LEFT JOIN positions p ON e.id_position = p.id " +
                "LEFT JOIN employees_to_projects t ON t.id_employee = e.id " +
                "LEFT JOIN projects j ON t.id_project = j.id " +
                "WHERE e.id = ?" +
                "ORDER BY j.id";
        List<SimpleEmployee> simpleEmployees = new ArrayList<>();
        SimpleEmployee simpleEmployee = null;
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet == null) throw new ResultException("Нет такого пользователя");

            while (resultSet.next()) {
                simpleEmployee = employeesMapper.mapToSimpleEmployee(resultSet);
                simpleEmployees.add(simpleEmployee);
            }
            if (simpleEmployee == null) throw new ResultException("Недопуситмое значение");
            return employeesMapper.mapToEmployee(simpleEmployee, simpleEmployees);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
