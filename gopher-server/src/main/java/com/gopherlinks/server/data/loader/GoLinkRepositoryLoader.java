package com.gopherlinks.server.data.loader;

import com.gopherlinks.server.data.repository.GoLinkRepository;
import reactor.core.publisher.Mono;

/**
 * Loads a {@link GoLinkRepository} with GoLink mappings.
 */
public interface GoLinkRepositoryLoader {

    /**
     * Loads a {@link GoLinkRepository} with GoLink mappings.
     *
     * @param goLinkRepository repository to load
     * @return complete signal
     */
    Mono<Void> load(GoLinkRepository goLinkRepository);
}
