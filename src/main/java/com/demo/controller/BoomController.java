package com.demo.controller;

import com.demo.exception.BoomException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BoomController {

    @GetMapping("/boom")
    private Mono<String> boom() throws Exception {
        throw new BoomException("KABOOM!");
    }
}