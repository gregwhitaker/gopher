package com.gopherlinks.server.service;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.gopherlinks.server.data.repository.GoLinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
public class GoLinkResolverService {
    private static final Logger LOG = LoggerFactory.getLogger(GoLinkResolverService.class);

    private final GoLinkRepository goLinkRepo;
    private final AsyncLoadingCache<String, String> resolverCache;

    @Autowired
    public GoLinkResolverService(GoLinkRepository goLinkRepo) {
        this.goLinkRepo = goLinkRepo;
        this.resolverCache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterAccess(Duration.ofHours(24))
                .buildAsync(this::loadKey);

        // Populate cache with all active GoLinks in the datastore
        loadAll()
                .publishOn(Schedulers.boundedElastic())
                .subscribe();
    }

    /**
     * Resolves the supplied GoLink to a URL.
     *
     * @param goLink GoLink to resolve
     * @return resolved URL
     */
    public Mono<String> resolveGoLink(String goLink) {
        return Mono.fromFuture(resolverCache.get(goLink));
    }

    /**
     * Loads a single GoLink into the cache asynchronously.
     *
     * @param key cache key to load
     * @param executor executor on which to run the cache load
     * @return resolved GoLink value to cache
     */
    private CompletableFuture<String> loadKey(String key, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Key not found in cache! Loading from datastore [key: '{}']", key);
            }

            return "http://www.google.com";
        }, executor);
    }

    /**
     * Loads the cache with all GoLinks in the datastore.
     */
    private Mono<Void> loadAll() {
        return goLinkRepo.findAll()
                .doOnNext(o -> resolverCache.put(o.getT1(), CompletableFuture.completedFuture(o.getT2())))
                .then();
    }
}
