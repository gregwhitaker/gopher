package com.gopherlinks.server.error;

import com.gopherlinks.server.error.model.ErrorResponse;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(MissingFormDataException.class)
    public Mono<ResponseEntity<?>> handleMissingFormDataException(MissingFormDataException e) {
        return Mono.fromSupplier(() -> {
           ErrorResponse response = new ErrorResponse();
           response.setStatus(HttpStatus.BAD_REQUEST.value());
           response.setMessage(e.getMessage());

           return ResponseEntity.badRequest()
                   .body(response);
        });
    }

    @ExceptionHandler(DuplicateGoLinkException.class)
    public Mono<ResponseEntity<?>> handleDuplicateGoLinkException(DuplicateGoLinkException e) {
        return Mono.fromSupplier(() -> {
            ErrorResponse response = new ErrorResponse();
            response.setStatus(HttpStatus.CONFLICT.value());
            response.setMessage(e.getMessage());

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(response);
        });
    }
}
