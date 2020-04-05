package com.gopherlinks.server.service;

import com.gopherlinks.server.core.resolver.GoLinkCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Service for resolving GoLinks.
 */
@Component
public class GoLinkResolverService {
    private static final Logger LOG = LoggerFactory.getLogger(GoLinkResolverService.class);

    private final GoLinkCache goLinkCache;

    @Autowired
    public GoLinkResolverService(GoLinkCache goLinkCache) {
        this.goLinkCache = goLinkCache;
    }

    /**
     * Resolves the supplied GoLink to a URL.
     *
     * @param goLink GoLink to resolve
     * @return resolved URL
     */
    public Mono<String> resolveGoLink(String goLink) {
        return Mono.fromFuture(goLinkCache.get(goLink));
    }
}
