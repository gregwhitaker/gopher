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
public class ManageGoLinksTest {

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

    @Test
    public void shouldReturnBadRequestForMissingGoLinkOnCreate() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("golink", "gopher");

        webTestClient.post()
                .uri("/api/v1/golinks")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(form))
                .exchange()
                .expectStatus()
                    .isBadRequest();
    }

    @Test
    public void shouldReturnBadRequestForMissingUrlOnCreate() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("url", "http://www.gopherlinks.com");

        webTestClient.post()
                .uri("/api/v1/golinks")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(form))
                .exchange()
                .expectStatus()
                    .isBadRequest();
    }

    @Test
    public void shouldGetValidGoLink() {
        webTestClient.get()
                .uri("/goog")
                .exchange()
                .expectStatus()
                    .isSeeOther()
                .expectHeader()
                    .value("Location", Matchers.equalToIgnoringCase("https://www.google.com"));
    }

    @Test
    public void shouldReturn404WhenAttemptingToGetInvalidGoLink() {
        webTestClient.get()
                .uri("/foobar")
                .exchange()
                .expectStatus()
                    .isNotFound();
    }

    @Test
    public void shouldDeleteValidGoLink() {
        webTestClient.delete()
                .uri("/api/v1/golinks/work")
                .exchange()
                .expectStatus()
                    .is2xxSuccessful();
    }

    @Test
    public void shouldReturn404WhenAttemptingToDeleteInvalidGoLink() {
        webTestClient.delete()
                .uri("/api/v1/golinks/foobar")
                .exchange()
                .expectStatus()
                    .isNotFound();
    }
}
