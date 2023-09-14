package com.githubuserbranchesresttempapi.controller;

public class InvalidAcceptHeaderException extends RuntimeException {
    public InvalidAcceptHeaderException(String message) {
        super(message);
    }
}
