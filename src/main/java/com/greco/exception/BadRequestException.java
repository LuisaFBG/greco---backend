package com.greco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends APIException{

    public BadRequestException() {
        super("bad.request");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
