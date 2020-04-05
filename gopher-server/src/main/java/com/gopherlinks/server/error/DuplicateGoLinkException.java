package com.gopherlinks.server.error;

/**
 * Exception thrown on an attempt to create a duplicate GoLink.
 */
public class DuplicateGoLinkException extends RuntimeException {

    private final String goLink;

    public DuplicateGoLinkException(String goLink) {
        super("GoLink already exists: " + goLink);
        this.goLink = goLink;
    }

    public String getGoLink() {
        return goLink;
    }
}
