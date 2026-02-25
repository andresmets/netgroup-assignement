package com.netgroup.netgroup_server.exception;

public class UserAlreadyBookedException extends RuntimeException{
    public UserAlreadyBookedException(String message) {
        super(message);
    }
}
