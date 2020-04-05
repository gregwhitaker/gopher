package com.gopherlinks.server.controller;

import com.gopherlinks.server.service.GoLinkManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/golinks")
public class GoLinkController {
    private static final Logger LOG = LoggerFactory.getLogger(GoLinkController.class);

    private final GoLinkManagementService goLinkManagementService;

    public GoLinkController(GoLinkManagementService goLinkManagementService) {
        this.goLinkManagementService = goLinkManagementService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> listGoLinks(@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
                                               @RequestParam(value = "limit", required = false, defaultValue = "25") long limit) {
        return null;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> createGoLink() {
        return null;
    }

    @GetMapping(value = "/{id}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> viewGoLink(@PathVariable("id") String goLinkId) {
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public Mono<ResponseEntity<?>> deleteGoLink(@PathVariable("id") String goLinkId) {
        return null;
    }

    @PutMapping(value = "/{id}/active",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> activateGoLink(@PathVariable("id") String goLinkId) {
        return null;
    }

    @DeleteMapping(value = "/{id}/active",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> deactivateGoLink(@PathVariable("id") String goLinkId) {
        return null;
    }
}
