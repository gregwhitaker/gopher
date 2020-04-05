package com.gopherlinks.server.controller;

import com.gopherlinks.server.service.GoLinkResolverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GoLinkResolverController {
    private static final Logger LOG = LoggerFactory.getLogger(GoLinkResolverController.class);

    private GoLinkResolverService goLinkResolverService;

    @Autowired
    public GoLinkResolverController(GoLinkResolverService goLinkResolverService) {
        this.goLinkResolverService = goLinkResolverService;
    }

    @GetMapping(value = "/{golink}")
    public Mono<ResponseEntity<?>> resolveGoLink(@PathVariable("goLink") String goLink) {
        return null;
    }
}
