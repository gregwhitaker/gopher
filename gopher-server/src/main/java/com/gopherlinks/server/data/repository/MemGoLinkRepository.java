package com.gopherlinks.server.data.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public class MemGoLinkRepository implements GoLinkRepository {
    private static final Logger LOG = LoggerFactory.getLogger(MemGoLinkRepository.class);

    private final Map<String, String> entries = new ConcurrentHashMap<>();

    @Override
    public Mono<String> findOne(String goLink) {
        return Mono.justOrEmpty(entries.get(goLink));
    }

    @Override
    public CompletableFuture<String> findOne(String goLink, Executor executor) {
        return null;
    }

    @Override
    public Flux<Tuple2<String, String>> findAll() {
        return Flux.fromIterable(entries.entrySet())
                .map(entry -> Tuples.of(entry.getKey(), entry.getValue()));
    }
}
