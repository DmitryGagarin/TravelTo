package com.travel.to.travel_to.exception.exception;

import java.io.Serial;

public class FileExtensionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7981882237548094861L;

    public FileExtensionException(String message) {
        super(message);
    }
}
