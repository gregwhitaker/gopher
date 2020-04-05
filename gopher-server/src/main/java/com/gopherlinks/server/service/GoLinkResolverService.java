package com.gopherlinks.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GoLinkResolverService {
    private static final Logger LOG = LoggerFactory.getLogger(GoLinkResolverService.class);

    public Mono<String> resolveGoLink(String goLink) {
        return Mono.fromSupplier(() -> {
            return "https://www.google.com";
        });
    }
}
