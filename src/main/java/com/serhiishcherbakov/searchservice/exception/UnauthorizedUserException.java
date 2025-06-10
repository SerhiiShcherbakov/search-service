package com.serhiishcherbakov.searchservice.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedUserException extends ServiceException {
    public UnauthorizedUserException() {
        super(HttpStatus.UNAUTHORIZED, "User is not authorized");
    }
}
