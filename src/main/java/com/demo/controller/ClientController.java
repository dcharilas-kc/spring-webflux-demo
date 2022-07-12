package com.demo.controller;

import com.demo.client.RestTemplateClient;
import com.demo.client.WebFluxClient;
import com.demo.exception.BoomException;
import com.demo.model.Employee;
import com.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/boom")
    private Mono<String> boom() throws Exception {
        throw new BoomException("KABOOM!");
    }
}