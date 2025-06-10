package com.serhiishcherbakov.searchservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ServiceException extends ResponseStatusException {
    public ServiceException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    public ServiceException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
