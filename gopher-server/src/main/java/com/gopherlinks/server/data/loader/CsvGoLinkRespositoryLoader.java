package com.gopherlinks.server.data.loader;

import com.gopherlinks.server.data.repository.GoLinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

/**
 * Loads a {@link GoLinkRepository} with GoLink mappings from a CSV file.
 */
public class CsvGoLinkRespositoryLoader implements GoLinkRepositoryLoader {
    private static final Logger LOG = LoggerFactory.getLogger(CsvGoLinkRespositoryLoader.class);

    private final Path filePath;

    public CsvGoLinkRespositoryLoader(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Mono<Void> load(GoLinkRepository goLinkRepository) {
        return Flux.create((Consumer<FluxSink<Tuple2<String, String>>>) fluxSink -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
                LOG.info("Loading GoLink repository from CSV file [file: '{}']", filePath);

                String line;
                int rowCount = 0;
                while ((line = reader.readLine()) != null) {
                    // Skip the header row
                    if (rowCount > 0) {
                        if (!line.isEmpty()) {
                            String[] parts = line.split(",");

                            if (parts.length == 2) {
                                fluxSink.next(Tuples.of(parts[0], parts[1]));
                                LOG.debug("Loading GoLink [golink: '{}', url: '{}'", parts[0], parts[1]);
                            }
                        }
                    }

                    rowCount++;
                }

                fluxSink.complete();
            } catch (IOException e) {
                fluxSink.error(e);
            }
        })
        .onErrorResume(throwable -> {
            LOG.error("Error occurred loading csv file [file: '{}']", filePath);
            return Mono.empty();
        })
        .flatMap(objects -> goLinkRepository.put(objects.getT1(), objects.getT2()))
        .then();
    }
}
