package com.gopherlinks.server;

import com.github.gregwhitaker.envopts.EnvOpts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GopherServer {

    public static void main(String... args) {
        EnvOpts.parse();
        SpringApplication.run(GopherServer.class);
    }
}
