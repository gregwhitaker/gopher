package com.gopherlinks.server.data.loader;

import com.gopherlinks.server.data.repository.GoLinkRepository;
import reactor.core.publisher.Mono;

public interface GoLinkRepositoryLoader {

    Mono<Void> load(GoLinkRepository goLinkRepository);
}
