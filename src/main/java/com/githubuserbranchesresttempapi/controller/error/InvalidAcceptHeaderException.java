package com.githubuserbranchesresttempapi.controller.error;

public class InvalidAcceptHeaderException extends RuntimeException {
    public InvalidAcceptHeaderException(String message) {
        super(message);
    }
}
