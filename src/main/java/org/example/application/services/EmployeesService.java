package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.application.model.Employee;
import org.example.application.repositories.EmployersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    private final EmployersRepository employersRepository;

    public List<Employee> getEmployees() {
        return employersRepository.getAllEmployers();
    }


}
