package test;

import org.example.application.mappers.EmployersMapper;
import org.example.application.model.Employee;
import org.example.application.repositories.EmployersRepository;
import org.example.application.services.ConnectionService;
import org.example.application.services.EmployeesService;

import java.sql.Connection;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ConnectionService connectionService = new ConnectionService();
        EmployersMapper employersMapper = new EmployersMapper();
        EmployersRepository employersRepository = new EmployersRepository(connectionService, employersMapper);
        EmployeesService employeesService = new EmployeesService(employersRepository);
        List<Employee> employees = employeesService.getEmployees();
        System.out.println(employees);
    }
}
