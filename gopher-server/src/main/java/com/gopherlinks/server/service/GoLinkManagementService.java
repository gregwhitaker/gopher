package com.gopherlinks.server.service;

import com.gopherlinks.server.data.repository.GoLinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GoLinkManagementService {
    private static final Logger LOG = LoggerFactory.getLogger(GoLinkManagementService.class);

    private final GoLinkRepository goLinkRepo;

    public GoLinkManagementService(GoLinkRepository goLinkRepo) {
        this.goLinkRepo = goLinkRepo;
    }


}
