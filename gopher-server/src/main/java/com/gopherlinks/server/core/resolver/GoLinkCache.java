package com.gopherlinks.server.core.resolver;

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

/**
 * Cache that holds the GoLinks for resolution.
 */
@Component
public class GoLinkCache {
    private static final Logger LOG = LoggerFactory.getLogger(GoLinkCache.class);

    private final GoLinkRepository goLinkRepo;
    private final AsyncLoadingCache<String, String> resolverCache;

    @Autowired
    public GoLinkCache(GoLinkRepository goLinkRepo) {
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
     * Gets a GoLink from the cache.
     *
     * @param goLink golink to retrieve
     * @return async url
     */
    public CompletableFuture<String> get(String goLink) {
        return resolverCache.get(goLink);
    }

    /**
     * Removes a GoLink from the cache.
     *
     * @param goLink golink to remove
     * @return async void
     */
    public CompletableFuture<Void> remove(String goLink) {
        return CompletableFuture.runAsync(() -> resolverCache.synchronous().invalidate(goLink));
    }

    /**
     * Loads a single GoLink into the cache asynchronously.
     *
     * @param goLink cache key to load
     * @param executor executor on which to run the cache load
     * @return resolved GoLink value to cache
     */
    private CompletableFuture<String> loadKey(String goLink, Executor executor) {
        return goLinkRepo.findOne(goLink, executor);
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
