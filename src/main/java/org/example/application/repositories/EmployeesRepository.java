package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.api.CreateEmployeeRequest;
import org.example.application.api.UpdateEmployeeRequest;
import org.example.application.exeptions.ResultException;
import org.example.application.interfaces.MyCallback;
import org.example.application.model.Employee;
import org.example.application.model.Position;
import org.example.application.model.Project;
import org.example.application.services.HibernateService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeesRepository {

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

    public boolean saveEmployee(CreateEmployeeRequest createEmployeeRequest) {
        EntityManager entityManager = hibernateService.getEntityManager();
        MyCallback<Boolean> createEmployeeCallback = () -> {
            try {
                Employee employee = new Employee();
                employee.setFirstName(createEmployeeRequest.getFirstName());
                employee.setLastName(createEmployeeRequest.getLastName());
                employee.setEmail(createEmployeeRequest.getEmail());
                employee.setAge(createEmployeeRequest.getAge());
                entityManager.persist(employee);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        };
        return hibernateService.executeQuery(createEmployeeCallback);
    }

    public boolean updateEmployee(UpdateEmployeeRequest updateEmployeeRequest, int id) {
        try {
            String firstName = updateEmployeeRequest.getFirstName();
            String lastname = updateEmployeeRequest.getLastName();
            String email = updateEmployeeRequest.getEmail();
            Integer age = updateEmployeeRequest.getAge();
            Integer positionId = updateEmployeeRequest.getPositionId();
            Position positionById;
            if (positionId != null) {
                positionById = positionsRepository.getPositionById(positionId);
            } else {
                positionById = null;
            }
            List<Integer> projectsId = updateEmployeeRequest.getProjectsId();
            List<Project> projectList = new ArrayList<>();
            if (projectsId != null) {
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
        try {
            EntityManager entityManager = hibernateService.getEntityManager();
            MyCallback<Boolean> deleteEmployeeCallback = () -> {
                Optional<Employee> employeeOpt = Optional.ofNullable(entityManager.find(Employee.class, id));
                if (!employeeOpt.isPresent()) return false;
                entityManager.remove(employeeOpt.get());
                return true;
            };
            return hibernateService.executeQuery(deleteEmployeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
