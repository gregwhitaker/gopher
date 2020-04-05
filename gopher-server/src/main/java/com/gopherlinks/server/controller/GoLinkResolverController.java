package com.gopherlinks.server.controller;

import com.gopherlinks.server.service.GoLinkResolverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    /**
     * Resolves a GoLink to a URL redirect.
     *
     * @param goLink golink to resolve
     * @return a url redirect if the golink exists; otherwise an HTTP 404 NOT FOUND response
     */
    @GetMapping(value = "/{golink}")
    public Mono<ResponseEntity<Object>> resolveGoLink(@PathVariable("golink") String goLink) {
        return goLinkResolverService.resolveGoLink(goLink)
                .map(redirectUrl -> ResponseEntity.status(HttpStatus.SEE_OTHER)
                        .header(HttpHeaders.LOCATION, redirectUrl)
                        .build())
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
