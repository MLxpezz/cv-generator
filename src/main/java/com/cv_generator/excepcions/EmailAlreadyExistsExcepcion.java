package com.cv_generator.excepcions;

public class EmailAlreadyExistsExcepcion extends RuntimeException {

    public EmailAlreadyExistsExcepcion(String message) {
        super(message);
    }
}
