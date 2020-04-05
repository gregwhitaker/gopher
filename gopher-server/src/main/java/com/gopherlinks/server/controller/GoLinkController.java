package com.gopherlinks.server.controller;

import com.gopherlinks.server.controller.model.ViewAllGoLinksResponse;
import com.gopherlinks.server.controller.model.ViewGoLinkResponse;
import com.gopherlinks.server.service.GoLinkManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/golinks")
public class GoLinkController {
    private static final Logger LOG = LoggerFactory.getLogger(GoLinkController.class);

    private final GoLinkManagementService goLinkManagementService;

    public GoLinkController(GoLinkManagementService goLinkManagementService) {
        this.goLinkManagementService = goLinkManagementService;
    }

    /**
     *
     * @param offset
     * @param limit
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> getAllGoLinks(@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
                                                 @RequestParam(value = "limit", required = false, defaultValue = "25") long limit) {
        return goLinkManagementService.getAllGoLinks(offset, limit)
                .map(values -> ResponseEntity.ok(ViewAllGoLinksResponse.from(values)));

    }

    /**
     *
     * @param goLink
     * @param url
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> createGoLink(@RequestPart("golink") String goLink, @RequestPart("url") String url) {
        return goLinkManagementService.createGoLink(goLink, url)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    /**
     *
     * @param goLinkId
     * @return
     */
    @GetMapping(value = "/{id}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> getGoLink(@PathVariable("id") String goLinkId) {
        return goLinkManagementService.getGoLink(goLinkId)
                .map(values -> ResponseEntity.ok(ViewGoLinkResponse.from(values)));
    }

    /**
     *
     * @param goLinkId
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Mono<ResponseEntity<?>> deleteGoLink(@PathVariable("id") String goLinkId) {
        return goLinkManagementService.deleteGoLink(goLinkId)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    /**
     *
     * @param goLinkId
     * @return
     */
    @PutMapping(value = "/{id}/active",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> activateGoLink(@PathVariable("id") String goLinkId) {
        throw new RuntimeException("Not Implemented");
    }

    /**
     *
     * @param goLinkId
     * @return
     */
    @DeleteMapping(value = "/{id}/active",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> deactivateGoLink(@PathVariable("id") String goLinkId) {
        throw new RuntimeException("Not Implemented");
    }
}
