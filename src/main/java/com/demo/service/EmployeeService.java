package com.demo.service;

import com.demo.client.WebFluxClient;
import com.demo.model.Employee;
import com.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final WebFluxClient client;

    public Mono<Employee> getEmployeeById(String id) {
        return employeeRepository.findEmployeeById(id);
    }

    @SneakyThrows
    public Flux<Employee> getAllEmployees() {
        Thread.sleep(3000);
        return employeeRepository.findAllEmployees();
    }

    public Mono<Employee> updateEmployee(Employee employee) {
        return employeeRepository.updateEmployee(employee);
    }
}
