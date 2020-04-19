package com.gopherlinks.server.config;

import com.gopherlinks.server.config.settings.DatastoreSettings;
import com.gopherlinks.server.data.loader.GoLinkRepositoryLoader;
import com.gopherlinks.server.data.repository.DbGoLinkRepository;
import com.gopherlinks.server.data.repository.GoLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        DatastoreSettings.class
})
@ConditionalOnProperty(value = "gopher.datastore.type", havingValue = DatastoreSettings.DATASTORE_TYPE_DATABASE)
public class DbDatastoreConfiguration {

    @Bean
    public GoLinkRepository goLinkRepository(@Autowired(required = false) GoLinkRepositoryLoader loader) {
        if (loader == null) {
            return new DbGoLinkRepository();
        } else {
            return new DbGoLinkRepository(loader);
        }
    }
}
