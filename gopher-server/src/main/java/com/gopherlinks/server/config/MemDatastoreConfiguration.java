package com.gopherlinks.server.config;

import com.gopherlinks.server.config.settings.DatastoreSettings;
import com.gopherlinks.server.data.loader.CsvGoLinkRespositoryLoader;
import com.gopherlinks.server.data.loader.GoLinkRepositoryLoader;
import com.gopherlinks.server.data.repository.GoLinkRepository;
import com.gopherlinks.server.data.repository.MemGoLinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableConfigurationProperties({
        DatastoreSettings.class
})
@ConditionalOnProperty(value = "gopher.datastore.type", havingValue = DatastoreSettings.DATASTORE_TYPE_MEMORY)
public class MemDatastoreConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(MemDatastoreConfiguration.class);

    @Profile("local")
    @Bean
    public GoLinkRepositoryLoader localGoLinkRepositoryLoader() throws Exception {
        Path filePath = Paths.get(ClassLoader.getSystemClassLoader().getResource("golinks-local.csv").toURI());
        return new CsvGoLinkRespositoryLoader(filePath);
    }

    @Bean
    public GoLinkRepository goLinkRepository(@Autowired(required = false) GoLinkRepositoryLoader loader) {
        LOG.warn("Configuring Gopher with the in-memory datastore. Any modifications will not be persisted across service restarts.");

        if (loader == null) {
            return new MemGoLinkRepository();
        } else {
            return new MemGoLinkRepository(loader);
        }
    }
}
