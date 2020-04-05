package com.gopherlinks.server.data.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface GoLinkRepository {

    Mono<String> findOne(String goLink);

    CompletableFuture<String> findOne(String goLink, Executor executor);

    Flux<Tuple2<String, String>> findAll();
}
