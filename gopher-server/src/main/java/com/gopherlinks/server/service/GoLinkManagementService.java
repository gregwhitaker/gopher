package com.gopherlinks.server.service;

import com.gopherlinks.server.core.resolver.GoLinkCache;
import com.gopherlinks.server.data.repository.GoLinkRepository;
import com.gopherlinks.server.error.GoLinkNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

/**
 * Service for managing configured GoLinks.
 */
@Component
public class GoLinkManagementService {
    private static final Logger LOG = LoggerFactory.getLogger(GoLinkManagementService.class);

    private final GoLinkRepository goLinkRepo;
    private final GoLinkCache goLinkCache;

    @Autowired
    public GoLinkManagementService(GoLinkRepository goLinkRepo, GoLinkCache goLinkCache) {
        this.goLinkRepo = goLinkRepo;
        this.goLinkCache = goLinkCache;
    }

    /**
     * Gets a paginated list of all GoLinks.
     *
     * @param offset pagination offset
     * @param limit pagination limit
     * @return a list of GoLinks
     */
    public Mono<List<Tuple2<String, String>>> getAllGoLinks(long offset, long limit) {
        return goLinkRepo.findAll()
                .skip(offset)
                .take(limit)
                .collectList();
    }

    /**
     * Returns a single GoLink.
     *
     * @param goLink golink to return
     * @return golink and url
     */
    public Mono<Tuple2<String, String>> getGoLink(String goLink) {
        return goLinkRepo.findOne(goLink)
                .switchIfEmpty(Mono.error(new GoLinkNotFoundException(goLink)))
                .map(url -> Tuples.of(goLink, url));
    }

    /**
     * Creates a new GoLink.
     *
     * @param goLink golink
     * @param url redirect url
     * @return void
     */
    public Mono<Void> createGoLink(String goLink, String url) {
        return Mono.defer(() -> goLinkRepo.put(goLink, url));
    }

    /**
     * Deletes an existing GoLink.
     *
     * @param goLink golink to delete
     * @return void
     */
    public Mono<Void> deleteGoLink(String goLink) {
        return goLinkRepo.findOne(goLink)
                .switchIfEmpty(Mono.error(new GoLinkNotFoundException(goLink)))
                .flatMap(s -> goLinkRepo.remove(goLink))
                .then(Mono.fromFuture(goLinkCache.remove(goLink)));
    }
}
