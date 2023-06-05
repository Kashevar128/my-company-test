package test;

import org.example.application.api.EmployeeRequest;
import org.example.application.mappers.EmployeesMapper;
import org.example.application.repositories.EmployeesRepository;
import org.example.application.services.ConnectionService;

public class Main {

    public static void main(String[] args) {

        ConnectionService connectionService = new ConnectionService();
        EmployeesMapper employeesMapper = new EmployeesMapper();
        EmployeesRepository employeesRepository = new EmployeesRepository(connectionService, employeesMapper);
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .firstName("Бум")
                .lastName("БумБум")
                .email(null)
                .age(null)
                .idPosition(null)
                .build();
        employeesRepository.updateEmployee(employeeRequest, 3);
    }
}
