package com.gopherlinks.server.data.repository;

import com.gopherlinks.server.data.loader.GoLinkRepositoryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * A {@link GoLinkRepository} implementation that stores the records in a RDBMS.
 */
public class DbGoLinkRepository implements GoLinkRepository {
    private static final Logger LOG = LoggerFactory.getLogger(DbGoLinkRepository.class);

    public DbGoLinkRepository() {
        LOG.info("No loader configured for initial record population");
    }

    public DbGoLinkRepository(GoLinkRepositoryLoader loader) {
        // Load this repository asynchronously
        loader.load(this)
                .publishOn(Schedulers.boundedElastic())
                .subscribe();
    }

    @Override
    public Mono<String> findOne(String goLink) {
        return null;
    }

    @Override
    public CompletableFuture<String> findOne(String goLink, Executor executor) {
        return null;
    }

    @Override
    public Flux<Tuple2<String, String>> findAll() {
        return null;
    }

    @Override
    public Mono<Void> put(String goLink, String url) {
        return null;
    }

    @Override
    public Mono<Void> remove(String goLink) {
        return null;
    }
}
