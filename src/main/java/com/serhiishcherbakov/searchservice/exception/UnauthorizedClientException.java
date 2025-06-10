package com.serhiishcherbakov.searchservice.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedClientException extends ServiceException {
    public UnauthorizedClientException() {
        super(HttpStatus.UNAUTHORIZED, "Missing-Api-Key");
    }
}
