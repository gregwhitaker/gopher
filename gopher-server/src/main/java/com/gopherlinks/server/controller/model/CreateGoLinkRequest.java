package com.gopherlinks.server.controller.model;

public class CreateGoLinkRequest {

    private String golink;
    private String url;

    public String getGolink() {
        return golink;
    }

    public void setGolink(String golink) {
        this.golink = golink;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
