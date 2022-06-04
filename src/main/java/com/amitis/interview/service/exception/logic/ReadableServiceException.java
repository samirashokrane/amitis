package com.amitis.interview.service.exception.logic;


import com.amitis.interview.dto.BaseDto;
import com.amitis.interview.service.exception.AbstractException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;


@AllArgsConstructor
@NoArgsConstructor
public class ReadableServiceException extends AbstractException implements Supplier<BaseDto> {


    private static final long serialVersionUID = 1L;
    private String gracefulMessage;
    private org.springframework.http.HttpStatus httpStatus;


    public static ReadableServiceException notFoundException(final BaseDto baseDto, final String fieldName, final HttpStatus httpStatus) {
        ReadableServiceException exception =
                new ReadableServiceException("error." + baseDto.getClass().getName().toLowerCase() + "." + fieldName.trim().toLowerCase() + ".not-found", httpStatus);
        exception.addMessageArg(fieldName.trim().toLowerCase(), baseDto.toString());
        return exception;
    }


    public static ReadableServiceException noResultException(final BaseDto baseDto, final HttpStatus httpStatus) {
        return new ReadableServiceException("error." + baseDto.getClass().getName().toLowerCase() + ".not-found", httpStatus);
    }


    public static ReadableServiceException notFoundException(final String parameterValue, final String parameterName, final HttpStatus httpStatus) {
        ReadableServiceException exception =
                new ReadableServiceException("error." + parameterValue + "." + parameterName + ".not-found", httpStatus);
        exception.addMessageArg(parameterName, parameterValue);

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
