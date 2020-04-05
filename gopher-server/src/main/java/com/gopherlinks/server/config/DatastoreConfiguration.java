package com.gopherlinks.server.config;

import com.gopherlinks.server.data.repository.GoLinkRepository;
import com.gopherlinks.server.data.repository.MemGoLinkRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatastoreConfiguration {

    @Bean
    public GoLinkRepository goLinkRepository() {
        return new MemGoLinkRepository();
    }
}
