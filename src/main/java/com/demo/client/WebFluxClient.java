package com.demo.client;

import com.demo.model.Employee;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.List;
import java.util.Random;

@Component
@Log4j2
public class WebFluxClient {

    public Mono<String> callSlowService() {
        log.info("Starting NON-BLOCKING Controller!");
        ClientHttpConnector httpClient = new ReactorClientHttpConnector(HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE));
        WebClient webClient = WebClient.builder().clientConnector(httpClient).build();

        Mono<String> myMono = webClient
                .get()
                .uri("http://localhost:8080/employees")
                .retrieve()
                .bodyToMono(String.class);

        myMono.subscribe(
                s -> log.info(s),
                error -> log.error("Error: ", error)
        );
        log.info("Exiting NON-BLOCKING Controller!");
        return myMono;
    }

    public Flux<Employee> updateAllEmployees() {
        log.info("Starting NON-BLOCKING Controller!");
        ClientHttpConnector httpClient = new ReactorClientHttpConnector(HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE));
        WebClient webClient = WebClient.builder().clientConnector(httpClient).build();

        Flux<Employee> myFlux = webClient
                .get()
                .uri("http://localhost:8080/employees")
                .retrieve()
                .bodyToFlux(Employee.class);

        myFlux = myFlux.flatMap(employee -> {
            employee.setRandomNumber(generateRandomNumber());
            log.info("Generated random number: " + employee.getRandomNumber());

            Mono<Employee> myMono = webClient
                    .post()
                    .uri("http://localhost:8080/employees/update")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(employee), Employee.class)
                    .retrieve()
                    .bodyToMono(Employee.class)
                    .doOnError(error -> log.error("Error updating employee with id: " + employee.getId(), error));
            return myMono;
        }).doOnError(error -> log.error("Error updating employees: ", error));

        log.info("Exiting NON-BLOCKING Controller!");
        return myFlux;
    }

    private int generateRandomNumber() {
        return new Random().nextInt(1000);
    }
}
