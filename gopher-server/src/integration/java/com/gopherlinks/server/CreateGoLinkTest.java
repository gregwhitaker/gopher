package com.gopherlinks.server;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("local")
public class CreateGoLinkTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldCreateGoLink() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("golink", "gopher");
        form.add("url", "http://www.gopherlinks.com");

        webTestClient.post()
                .uri("/api/v1/golinks")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(form))
                .exchange()
                .expectStatus()
                    .is2xxSuccessful();

        webTestClient.get()
                .uri("/gopher")
                .exchange()
                .expectStatus()
                    .isSeeOther()
                .expectHeader()
                    .value("Location", Matchers.equalToIgnoringCase("http://www.gopherlinks.com"));
    }
}
