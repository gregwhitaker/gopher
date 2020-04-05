package com.gopherlinks.server.data.loader;

import com.gopherlinks.server.data.repository.GoLinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class FileGoLinkRepositoryLoader implements GoLinkRepositoryLoader {
    private static final Logger LOG = LoggerFactory.getLogger(FileGoLinkRepositoryLoader.class);

    @Override
    public Mono<Void> load(GoLinkRepository goLinkRepository) {
        return null;
    }
}
