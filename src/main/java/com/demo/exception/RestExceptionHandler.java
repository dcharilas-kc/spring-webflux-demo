package com.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    ResponseEntity boom(BoomException ex) {
        log.debug("handling exception::" + ex);
        return ResponseEntity.internalServerError().body("This message was added by the global handler");
    }

}
