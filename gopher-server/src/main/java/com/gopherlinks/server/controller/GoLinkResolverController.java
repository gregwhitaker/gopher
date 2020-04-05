package com.gopherlinks.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GoLinkResolverController {
    private static final Logger LOG = LoggerFactory.getLogger(GoLinkResolverController.class);

    @GetMapping(value = "/{golink}")
    public Mono<ResponseEntity<?>> resolveGoLink(@PathVariable("goLink") String goLink) {
        return null;
    }
}
