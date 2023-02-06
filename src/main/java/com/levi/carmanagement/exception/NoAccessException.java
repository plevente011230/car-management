package com.levi.carmanagement.exception;

public class NoAccessException extends Exception {

    public static final String NO_ACCESS_EXCEPTION_MESSAGE = "No access granted to this resource";
    public NoAccessException() {
        super(NO_ACCESS_EXCEPTION_MESSAGE);
    }
}
