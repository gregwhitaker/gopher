package com.gopherlinks.server.data.repository;

import com.gopherlinks.server.data.loader.GoLinkRepositoryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/**
 * A {@link GoLinkRepository} implementation that stores the records in memory.
 */
public class MemGoLinkRepository implements GoLinkRepository {
    private static final Logger LOG = LoggerFactory.getLogger(MemGoLinkRepository.class);

    private final Map<String, String> entries = new ConcurrentHashMap<>();

    public MemGoLinkRepository() {
        LOG.info("No loader configured for initial record population");
    }

    public MemGoLinkRepository(GoLinkRepositoryLoader loader) {
        // Load this repository asynchronously
        loader.load(this)
                .publishOn(Schedulers.boundedElastic())
                .subscribe();
    }

    @Override
    public Mono<String> findOne(String goLink) {
        return Mono.justOrEmpty(entries.get(goLink));
    }

    @Override
    public CompletableFuture<String> findOne(String goLink, Executor executor) {
        return CompletableFuture.supplyAsync(() -> entries.get(goLink), executor);
    }

    @Override
    public Flux<Tuple2<String, String>> findAll() {
        return Flux.fromIterable(entries.entrySet())
                .map(entry -> Tuples.of(entry.getKey(), entry.getValue()));
    }

    @Override
    public Mono<Void> put(String goLink, String url) {
        return Mono.defer(() -> {
            entries.put(goLink, url);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> remove(String goLink) {
        return Mono.defer(() -> {
            entries.remove(goLink);
            return Mono.empty();
        });
    }
}
