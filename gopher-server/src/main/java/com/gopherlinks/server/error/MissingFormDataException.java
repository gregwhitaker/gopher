package com.gopherlinks.server.error;

public class MissingFormDataException extends RuntimeException {

    private final String name;

    public MissingFormDataException(String name) {
        super("Missing form parameter: " + name);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
