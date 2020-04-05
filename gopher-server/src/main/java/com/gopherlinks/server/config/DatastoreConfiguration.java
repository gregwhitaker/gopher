package com.gopherlinks.server.config;

import com.gopherlinks.server.data.loader.CsvGoLinkRespositoryLoader;
import com.gopherlinks.server.data.loader.GoLinkRepositoryLoader;
import com.gopherlinks.server.data.repository.GoLinkRepository;
import com.gopherlinks.server.data.repository.MemGoLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class DatastoreConfiguration {

    @Profile("local")
    @Bean
    public GoLinkRepositoryLoader localGoLinkRepositoryLoader() throws Exception {
        Path filePath = Paths.get(ClassLoader.getSystemClassLoader().getResource("golinks-local.csv").toURI());
        return new CsvGoLinkRespositoryLoader(filePath);
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
