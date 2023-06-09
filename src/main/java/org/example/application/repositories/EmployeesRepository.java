package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.api.EmployeeCreateRequest;
import org.example.application.api.EmployeeUpdateRequest;
import org.example.application.exeptions.ResultException;
import org.example.application.interfaces.MyCallback;
import org.example.application.model.Employee;
import org.example.application.model.Position;
import org.example.application.model.Project;
import org.example.application.services.ConnectionService;
import org.example.application.services.HibernateService;
import org.example.application.services.ProjectService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeesRepository {

    private final ConnectionService connectionService;
    private final PositionsRepository positionsRepository;
    private final ProjectRepository projectRepository;
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
        else throw new ResultException("База данных пуста.");
    }

    public Employee getEmployeeById(int id) throws ResultException {
        EntityManager entityManager = hibernateService.getEntityManager();
        MyCallback<Optional<Employee>> employeeCallback = () ->
                Optional.ofNullable(entityManager.find(Employee.class, id));
        Optional<Employee> employee = hibernateService.executeQuery(employeeCallback);
        if (employee.isPresent()) return employee.get();
        else throw new ResultException("Такого пользователя не существует.");

    }

    public boolean saveEmployee(EmployeeCreateRequest employeeCreateRequest) {
        EntityManager entityManager = hibernateService.getEntityManager();
        MyCallback<Boolean> createEmployeeCallback = () -> {
            try {
                Employee employee = new Employee();
                employee.setFirstName(employeeCreateRequest.getFirstName());
                employee.setLastName(employeeCreateRequest.getLastName());
                employee.setEmail(employeeCreateRequest.getEmail());
                employee.setAge(employeeCreateRequest.getAge());
                entityManager.persist(employee);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        };
        return hibernateService.executeQuery(createEmployeeCallback);
    }

    public boolean updateEmployee(EmployeeUpdateRequest employeeUpdateRequest, int id) {
        try {
            String firstName = employeeUpdateRequest.getFirstName();
            String lastname = employeeUpdateRequest.getLastName();
            String email = employeeUpdateRequest.getEmail();
            Integer age = employeeUpdateRequest.getAge();
            Integer positionId = employeeUpdateRequest.getPositionId();
            Position positionById;
            if(positionId != null) {
                positionById = positionsRepository.getPositionById(positionId);
            } else {
                positionById = null;
            }
            List<Integer> projectsId = employeeUpdateRequest.getProjectsId();
            List<Project> projectList = new ArrayList<>();
            if(projectsId != null) {
                for (Integer i : projectsId) {
                    Project projectById = projectRepository.getProjectById(i);
                    projectList.add(projectById);
                }
            }

            EntityManager entityManager = hibernateService.getEntityManager();
            MyCallback<Boolean> updateCallable = () -> {
                Optional<Employee> employeeOpt = Optional.ofNullable(entityManager.find(Employee.class, id));
                Employee employee;
                if (!employeeOpt.isPresent()) return false;
                else employee = employeeOpt.get();

                Optional.ofNullable(firstName).ifPresent(employee::setFirstName);
                Optional.ofNullable(lastname).ifPresent(employee::setLastName);
                Optional.ofNullable(email).ifPresent(employee::setEmail);
                Optional.ofNullable(age).ifPresent(employee::setAge);
                Optional.ofNullable(positionById).ifPresent(employee::setPosition);
                Optional.ofNullable(projectList).ifPresent(employee::setProjects);
                return true;
            };
            return hibernateService.executeQuery(updateCallable);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
