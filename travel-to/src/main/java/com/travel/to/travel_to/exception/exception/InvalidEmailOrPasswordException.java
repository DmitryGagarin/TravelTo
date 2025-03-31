package com.travel.to.travel_to.exception.exception;

import java.io.Serial;

public class InvalidEmailOrPasswordException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6179300902637500915L;

    public InvalidEmailOrPasswordException(String message) {
        super(message);
    }
}
