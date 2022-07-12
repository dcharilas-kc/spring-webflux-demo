package com.demo.controller;

import com.demo.client.RestTemplateClient;
import com.demo.client.WebFluxClient;
import com.demo.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final RestTemplateClient restTemplateClient;
    private final WebFluxClient webFluxClient;

    @GetMapping("/async")
    private Mono<String> callSlowServiceAsyncMode() {
        return webFluxClient.callSlowService();
    }

    @GetMapping("/sync")
    private Mono<String> callSlowServiceSyncMode() {
        return Mono.just(restTemplateClient.callSlowService());
    }

    @PostMapping("/batch-update")
    private Flux<Employee> updateAllEmployees() {
        return webFluxClient.updateAllEmployees();
    }

}