package com.gopherlinks.server.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * Global error handler responsible for converting unhandled exceptions to API responses.
 */
@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(GoLinkNotFoundException.class)
    public Mono<ResponseEntity<?>> handleGoLinkNotFoundException(GoLinkNotFoundException e) {
        return Mono.just(ResponseEntity.notFound().build());
    }
}
