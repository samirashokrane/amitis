package com.amitis.interview.service.exception.logic;


import com.amitis.interview.dto.BaseDto;
import com.amitis.interview.service.exception.AbstractException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

@AllArgsConstructor
@NoArgsConstructor
public class WritableServiceException extends AbstractException implements Supplier<BaseDto>  {


	private static final long serialVersionUID = 1L;
	private String gracefulMessage;
    private HttpStatus httpStatus;

    public static WritableServiceException alreadyExistException(final BaseDto baseDto, final String fieldName, final HttpStatus httpStatus) {
        WritableServiceException exception =
                new WritableServiceException("error." + baseDto.getClass().getName().toLowerCase() + "." + fieldName.trim() + ".already-exist",httpStatus);
        exception.addMessageArg(fieldName, getFieldValue(fieldName, baseDto));
        return exception;
    }

    public static WritableServiceException alreadyExistException(final String parameterValue, final String parameterName,final HttpStatus httpStatus) {
        WritableServiceException exception =
                new WritableServiceException("error." + parameterValue + "." + parameterName.trim() + ".already-exist",httpStatus);
        exception.addMessageArg(parameterName, parameterValue);
        return exception;
    }

    public static WritableServiceException fileUploadException(final String message, final String parameter,final HttpStatus httpStatus ){
        WritableServiceException exception = new WritableServiceException(message,httpStatus);
        exception.addMessageArg(parameter.toLowerCase(), message);
        return exception;
    }

    public static WritableServiceException invalidStateException(final String id, final String state, final HttpStatus httpStatus) {
        return new WritableServiceException("error."+id+".invalid.state.request."+state,httpStatus);
    }

    public static WritableServiceException tooManyRequestException(final String parameterValue, final String parameterName, final HttpStatus httpStatus){
        WritableServiceException exception =
                new WritableServiceException("error." + parameterValue + "." + parameterName.trim() + ".too-many-request",httpStatus);
        return exception;
    }

    @Override
    public BaseDto get() {
        return null;
    }

    @Override
    public String getMessage(){
        return this.gracefulMessage;
    }

    public HttpStatus getHttpStatus(){
        return this.httpStatus;
    }
}
