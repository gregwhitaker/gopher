package com.gopherlinks.server;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("local")
public class ResolveGoLinksTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldReturnRedirectForValidGoLink() {
        webTestClient.get()
                .uri("/goog")
                .exchange()
                .expectStatus()
                    .isSeeOther()
                .expectHeader()
                    .value("Location", Matchers.equalToIgnoringCase("https://www.google.com"));
    }

    @Test
    public void shouldReturn404ForInvalidGoLink() {
        webTestClient.get()
                .uri("/foobar")
                .exchange()
                .expectStatus()
                    .isNotFound();
    }
}
