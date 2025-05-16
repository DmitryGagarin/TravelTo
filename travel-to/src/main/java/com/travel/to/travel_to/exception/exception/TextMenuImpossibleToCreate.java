package com.travel.to.travel_to.exception.exception;

import java.io.Serial;

public class TextMenuImpossibleToCreate extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1806414921194457348L;

    public TextMenuImpossibleToCreate() {}
    public TextMenuImpossibleToCreate(String message) {
        super(message);
    }
}
