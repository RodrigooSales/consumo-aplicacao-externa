package com.rodrigo.desafioRest.exeception;

import org.springframework.http.HttpStatus;

public class ResponseStatusException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

