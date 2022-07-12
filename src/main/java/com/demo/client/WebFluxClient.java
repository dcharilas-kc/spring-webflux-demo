package com.demo.client;

import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Component
@Log4j2
public class WebFluxClient {

    public Mono<String> callSlowService() {
        log.info("Starting NON-BLOCKING Controller!");
        ClientHttpConnector httpClient = new ReactorClientHttpConnector(HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE));
        WebClient webClient = WebClient.builder().clientConnector(httpClient).build();

        Mono<String> myFlux = webClient
                .get()
                .uri("http://localhost:8080/employees")
                .retrieve()
                .bodyToMono(String.class);

        myFlux.subscribe(
                s -> log.info(s),
                error -> log.error("Error: ", error)
        );
        log.info("Exiting NON-BLOCKING Controller!");
        return myFlux;
    }
}
