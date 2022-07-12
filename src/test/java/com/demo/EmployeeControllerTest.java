package com.demo;

import com.demo.controller.EmployeeController;
import com.demo.model.Employee;
import com.demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
@Import(EmployeeService.class)
public class EmployeeControllerTest {

    @MockBean
    EmployeeService service;

    @Autowired
    private WebTestClient webClient;

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee();
        employee.setId("100");
        employee.setName("Test");

        Mockito.when(service.getEmployeeById("100")).thenReturn(Mono.just(employee));

        webClient.get().uri("/employees/{id}", 100)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(100)
                .jsonPath("$.name").isEqualTo("Test");

        Mockito.verify(service, times(1)).getEmployeeById("100");
    }

    @Test
    void testGetEmployees() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setName("Test");
        List<Employee> list = new ArrayList<>();
        list.add(employee);
        Flux<Employee> employeeFlux = Flux.fromIterable(list);

        Mockito.when(service.getAllEmployees()).thenReturn(employeeFlux);

        webClient.get().uri("/employees")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Employee.class);

        Mockito.verify(service, times(1)).getAllEmployees();
    }
}
