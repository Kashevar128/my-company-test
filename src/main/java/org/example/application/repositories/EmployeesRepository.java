package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.api.EmployeeRequest;
import org.example.application.exeptions.ResultException;
import org.example.application.interfaces.MyCallback;
import org.example.application.mappers.EmployeesMapper;
import org.example.application.model.Employee;
import org.example.application.services.ConnectionService;
import org.example.application.services.HibernateService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
@RequiredArgsConstructor
public class EmployeesRepository {

    private final ConnectionService connectionService;
    private final HibernateService hibernateService;

    public List<Employee> getAllEmployees() throws ResultException {
        EntityManager entityManager = hibernateService.getEntityManager();
        MyCallback<Optional<List<Employee>>> employeesCallback = () ->
                Optional
                        .ofNullable(entityManager
                                .createQuery("SELECT e FROM Employee e", Employee.class)
                                .getResultList());
        Optional<List<Employee>> employees = hibernateService.executeQuery(employeesCallback);
        if (employees.isPresent()) return employees.get();
        else throw new ResultException("База данных пуста");
    }

    public Employee getEmployeeById(int id) throws ResultException {
        String query = "SELECT * FROM employees WHERE id = ?";

        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet == null) throw new ResultException("Такого пользователя не существует.");

            Employee employee = null;

            return employee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean saveEmployee(EmployeeRequest employeeRequest) {
        String query = "INSERT INTO employees (first_name, last_name, email, age, id_position) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, employeeRequest.getFirstName());
            statement.setString(2, employeeRequest.getLastName());
            statement.setString(3, employeeRequest.getEmail());
            statement.setInt(4, employeeRequest.getAge());
            statement.setInt(5, employeeRequest.getIdPosition());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEmployee(EmployeeRequest employeeRequest, int id) {

        String firstNameSQL = "UPDATE  employees SET first_name = ? WHERE id = ?";
        String lastNameSQl = "UPDATE  employees SET last_name = ? WHERE id = ?";
        String emailSQL = "UPDATE  employees SET email = ? WHERE id = ?";
        String ageSQL = "UPDATE  employees SET age = ? WHERE id = ?";
        String idPositionSQL = "UPDATE  employees SET id_position = ? WHERE id = ?";

        try (Connection connection = connectionService.getConnection()) {

            Consumer<String> firstNameConsumer = (firstName) -> {
                PreparedStatement statement;
                try {
                    statement = connection.prepareStatement(firstNameSQL);
                    statement.setString(1, firstName);
                    statement.setInt(2, id);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            };
            Consumer<String> lastNameConsumer = (lastName) -> {
                PreparedStatement statement;
                try {
                    statement = connection.prepareStatement(lastNameSQl);
                    statement.setString(1, lastName);
                    statement.setInt(2, id);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            };
            Consumer<String> emailConsumer = (email) -> {
                PreparedStatement statement;
                try {
                    statement = connection.prepareStatement(emailSQL);
                    statement.setString(1, email);
                    statement.setInt(2, id);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            };
            Consumer<Integer> ageConsumer = (age) -> {
                PreparedStatement statement;
                try {
                    statement = connection.prepareStatement(ageSQL);
                    statement.setInt(1, age);
                    statement.setInt(2, id);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            };
            Consumer<Integer> idPositionConsumer = (idPosition) -> {
                PreparedStatement statement;
                try {
                    statement = connection.prepareStatement(idPositionSQL);
                    statement.setInt(1, idPosition);
                    statement.setInt(2, id);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            };

            String firstName = employeeRequest.getFirstName();
            String lastname = employeeRequest.getLastName();
            String email = employeeRequest.getEmail();
            Integer age = employeeRequest.getAge();
            Integer idPosition = employeeRequest.getIdPosition();

            Optional.ofNullable(firstName).ifPresent(firstNameConsumer);
            Optional.ofNullable(lastname).ifPresent(lastNameConsumer);
            Optional.ofNullable(email).ifPresent(emailConsumer);
            Optional.ofNullable(age).ifPresent(ageConsumer);
            Optional.ofNullable(idPosition).ifPresent(idPositionConsumer);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteEmployee(int id) {
        String query = "DELETE FROM employees WHERE id = ?";
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Employee> getEmployeeListByPositionId(int id) {
        String query = "SELECT * FROM employees e WHERE e.id_position = ?";

        List<Employee> employeeList = new ArrayList<>();
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public List<Employee> getEmployeeByProjectId(int id) {
        String query = "SELECT * FROM employees e\n" +
                "LEFT JOIN employees_to_projects t ON t.id_employee = e.id\n" +
                "LEFT JOIN projects pr ON t.id_project = pr.id\n" +
                "WHERE pr.id = ?";

        List<Employee> employeeList = new ArrayList<>();
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }
}
