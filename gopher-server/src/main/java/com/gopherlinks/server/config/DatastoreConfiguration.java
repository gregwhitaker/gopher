package com.gopherlinks.server.config;

import com.gopherlinks.server.data.loader.FileGoLinkRepositoryLoader;
import com.gopherlinks.server.data.loader.GoLinkRepositoryLoader;
import com.gopherlinks.server.data.repository.GoLinkRepository;
import com.gopherlinks.server.data.repository.MemGoLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DatastoreConfiguration {

    @Profile("local")
    @Bean
    public GoLinkRepositoryLoader localGoLinkRepositoryLoader() {
        return new FileGoLinkRepositoryLoader();
    }

    @Bean
    public GoLinkRepository goLinkRepository(@Autowired(required = false) GoLinkRepositoryLoader loader) {
        if (loader == null) {
            return new MemGoLinkRepository();
        } else {
            return new MemGoLinkRepository(loader);
        }
    }
}
