package org.example.application.test;

import org.example.application.api.EmployeeRequest;
import org.example.application.hibernate.HibernateManagerFactory;
import org.example.application.repositories.EmployeesRepository;
import org.example.application.services.ConnectionService;
import org.example.application.services.HibernateService;

public class Test {

    public static void main(String[] args) {
        ConnectionService connectionService = new ConnectionService();
        HibernateManagerFactory hibernateManagerFactory = new HibernateManagerFactory();
        HibernateService hibernateService = new HibernateService(hibernateManagerFactory);
        EmployeesRepository employeesRepository = new EmployeesRepository(connectionService, hibernateService);

        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .firstName("sdfsdf")
                .lastName("sefsdf")
                .email("sdfdsffd")
                .age(45)
                .idPosition(2)
                .build();
        employeesRepository.saveEmployee(employeeRequest);
    }
}
