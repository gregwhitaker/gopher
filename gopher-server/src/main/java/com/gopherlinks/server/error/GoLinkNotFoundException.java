package com.gopherlinks.server.error;

public class GoLinkNotFoundException extends RuntimeException {

    private final String goLink;

    public GoLinkNotFoundException(String goLink) {
        super("GoLink not found: " + goLink);
        this.goLink = goLink;
    }

    public String getGoLink() {
        return goLink;
    }
}
