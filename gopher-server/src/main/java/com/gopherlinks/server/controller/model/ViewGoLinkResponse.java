package com.gopherlinks.server.controller.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import reactor.util.function.Tuple2;

@JsonPropertyOrder({
        "golink",
        "url"
})
public class ViewGoLinkResponse {

    public static ViewGoLinkResponse from(Tuple2<String, String> values) {
        ViewGoLinkResponse response = new ViewGoLinkResponse();
        response.golink = values.getT1();
        response.url = values.getT2();

        return response;
    }

    private String golink;
    private String url;

    public String getGolink() {
        return golink;
    }

    public String getUrl() {
        return url;
    }
}
