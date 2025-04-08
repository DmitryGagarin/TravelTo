package com.travel.to.travel_to.exception.exception;

import java.io.Serial;

public class UserNotVerifiedException extends RuntimeException {
    
    @Serial
    private static final long serialVersionUID = 619076403589759473L;

    public UserNotVerifiedException(String message) {
        super(message);
    }
}
