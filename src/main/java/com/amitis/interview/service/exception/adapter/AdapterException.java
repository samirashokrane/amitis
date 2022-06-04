package com.amitis.interview.service.exception.adapter;


import com.amitis.interview.dto.BaseDto;
import com.amitis.interview.service.exception.AbstractException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;
@AllArgsConstructor
@NoArgsConstructor
public class AdapterException extends AbstractException implements Supplier<BaseDto> {

    private static final long serialVersionUID = 1L;
    private String gracefulMessage;
    private String sourceService;
    private String destinationService;
    private String requestBody;
    private org.springframework.http.HttpStatus httpStatus;


    public static AdapterException interCommunicationException(final String source,
                                                               final String destination,
                                                               final String requestBody,
                                                               final HttpStatus httpStatus) {
        AdapterException exception =
                new AdapterException("error.internal.server",source,destination,requestBody,httpStatus);
        exception.addMessageArg("source",source);
        exception.addMessageArg("destination",destination);
        return exception;
    }


    @Override
    public BaseDto get() {
        return null;
    }

    @Override
    public String getMessage() {
        return this.gracefulMessage;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
