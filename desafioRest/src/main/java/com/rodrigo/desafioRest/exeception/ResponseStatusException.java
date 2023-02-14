package com.rodrigo.desafioRest.exeception;

import org.springframework.http.HttpStatus;

public class ResponseStatusException extends RuntimeException {

    private final HttpStatus status;
    private final String reason;

    public ResponseStatusException(HttpStatus status, String reason) {
        super(reason);
        this.status = status;
        this.reason = reason;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }
}

