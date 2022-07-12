package com.demo.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
@Log4j2
public class RestTemplateClient {

    public String callSlowService() {
        log.info("Starting BLOCKING Controller!");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/employees",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>(){});

        log.info(response.getBody());
        log.info("Exiting BLOCKING Controller!");
        return response.getBody();
    }
}
