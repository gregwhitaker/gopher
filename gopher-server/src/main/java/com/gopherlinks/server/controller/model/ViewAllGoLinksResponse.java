package com.gopherlinks.server.controller.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import reactor.util.function.Tuple2;

import java.util.List;

@JsonPropertyOrder({

})
public class ViewAllGoLinksResponse {

    public static ViewAllGoLinksResponse from(List<Tuple2<String, String>> values) {
        ViewAllGoLinksResponse response = new ViewAllGoLinksResponse();

        return response;
    }
}
