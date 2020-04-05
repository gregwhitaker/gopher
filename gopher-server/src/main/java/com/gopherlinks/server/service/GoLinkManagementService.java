package com.gopherlinks.server.service;

import com.gopherlinks.server.data.repository.GoLinkRepository;
import com.gopherlinks.server.error.GoLinkNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

@Component
public class GoLinkManagementService {
    private static final Logger LOG = LoggerFactory.getLogger(GoLinkManagementService.class);

    private final GoLinkRepository goLinkRepo;

    public GoLinkManagementService(GoLinkRepository goLinkRepo) {
        this.goLinkRepo = goLinkRepo;
    }

    public Mono<List<Tuple2<String, String>>> getAllGoLinks(long offset, long limit) {
        return goLinkRepo.findAll()
                .skip(offset)
                .take(limit)
                .collectList();
    }

    public Mono<Tuple2<String, String>> getGoLink(String goLink) {
        return goLinkRepo.findOne(goLink)
                .switchIfEmpty(Mono.error(new GoLinkNotFoundException(goLink)))
                .map(url -> Tuples.of(goLink, url));
    }

    public Mono<Void> createGoLink(String goLink, String url) {
        return goLinkRepo.put(goLink, url);
    }

    public Mono<Void> deleteGoLink(String goLink) {
        return goLinkRepo.findOne(goLink)
                .switchIfEmpty(Mono.error(new GoLinkNotFoundException(goLink)))
                .flatMap(s -> goLinkRepo.remove(goLink));
    }
}
