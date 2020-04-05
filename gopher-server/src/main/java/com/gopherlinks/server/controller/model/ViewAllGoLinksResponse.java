package com.gopherlinks.server.controller.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({
        "count",
        "golinks"
})
public class ViewAllGoLinksResponse {

    public static ViewAllGoLinksResponse from(List<Tuple2<String, String>> values) {
        ViewAllGoLinksResponse response = new ViewAllGoLinksResponse();
        response.count = values.size();
        values.forEach(objects -> response.golinks.add(GoLinkEntry.from(objects)));

        return response;
    }

    private int count;
    private List<GoLinkEntry> golinks = new ArrayList<>();

    public int getCount() {
        return count;
    }

    public List<GoLinkEntry> getGolinks() {
        return golinks;
    }

    /**
     * GoLink entry to return in the list of links.
     */
    static class GoLinkEntry {

        static GoLinkEntry from(Tuple2<String, String> value) {
            return new GoLinkEntry(value.getT1(), value.getT2());
        }

        private final String golink;
        private final String url;

        GoLinkEntry(String golink, String url) {
            this.golink = golink;
            this.url = url;
        }

        public String getGolink() {
            return golink;
        }

        public String getUrl() {
            return url;
        }
    }
}
