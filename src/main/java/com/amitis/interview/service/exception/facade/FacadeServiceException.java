package com.amitis.interview.service.exception.facade;


import com.amitis.interview.dto.BaseDto;
import com.amitis.interview.service.exception.AbstractException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

@AllArgsConstructor
@NoArgsConstructor
public class FacadeServiceException extends AbstractException implements Supplier<BaseDto> {


    private static final long serialVersionUID = 1L;
    private String gracefulMessage;
    private org.springframework.http.HttpStatus httpStatus;


    public static FacadeServiceException notValidException(final BaseDto baseDto, final String fieldName, final HttpStatus httpStatus) {
        FacadeServiceException exception =
                new FacadeServiceException("error." + baseDto.getClass().getName().toLowerCase() + "." + fieldName.trim().toLowerCase() + ".not-valid", httpStatus);
        exception.addMessageArg(fieldName.trim().toLowerCase(), baseDto.toString());
        return exception;
    }

    public static FacadeServiceException notValidException(final String parameterName, final String parameterValue, final HttpStatus httpStatus) {
        FacadeServiceException exception =
                new FacadeServiceException("error." + parameterValue + "." + parameterName + ".not-valid", httpStatus);
        exception.addMessageArg(parameterName, parameterValue);
        exception.setStackTrace(exception.getStackTrace());
        return exception;
    }

    public static FacadeServiceException notValidException( final String parameterValue, final HttpStatus httpStatus) {
        FacadeServiceException exception =
                new FacadeServiceException(parameterValue , httpStatus);
        exception.setStackTrace(exception.getStackTrace());
        return exception;
    }

    public static FacadeServiceException internalServerException(final String parameterName, final String parameterValue, final HttpStatus httpStatus) {
        FacadeServiceException exception =
                new FacadeServiceException("error." + parameterValue + "." + parameterName + ".not-reachable", httpStatus);
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
