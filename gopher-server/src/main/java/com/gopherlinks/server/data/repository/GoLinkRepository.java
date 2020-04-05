package com.gopherlinks.server.data.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface GoLinkRepository {

    /**
     * Returns the redirect url for a GoLink.
     *
     * @param goLink golink
     * @return redirect url
     */
    Mono<String> findOne(String goLink);

    /**
     * Returns the redirect url for a GoLink.
     *
     * @param goLink golink
     * @param executor executor on which to run the query
     * @return redirect url
     */
    CompletableFuture<String> findOne(String goLink, Executor executor);

    /**
     * Returns all GoLinks.
     *
     * @return all golinks
     */
    Flux<Tuple2<String, String>> findAll();

    /**
     * Saves a GoLink.
     *
     * @param goLink golink
     * @param url redirect url
     * @return void
     */
    Mono<Void> put(String goLink, String url);

    /**
     * Removes a GoLink.
     *
     * @param goLink golink
     * @return void
     */
    Mono<Void> remove(String goLink);
}
