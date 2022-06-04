package com.amitis.interview.controller.exception;

import com.amitis.interview.service.exception.core.CoreServiceException;
import com.amitis.interview.service.exception.facade.FacadeServiceException;
import com.amitis.interview.service.exception.logic.ReadableServiceException;
import com.amitis.interview.service.exception.logic.WritableServiceException;
import com.amitis.interview.service.exception.AbstractException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@ControllerAdvice
@Log4j2
public class ControllerException extends ResponseEntityExceptionHandler implements ErrorController {

    private final MessageSource messageSource;

    @Value("${exception.log.mode}")
    private String exceptionLogMode;

    @Autowired
    public ControllerException(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler(AbstractException.class)
    public ResponseEntity<Object> handleBaseBusinessException(
            final AbstractException exception,
            final HttpServletRequest request
    ) {
        GracefulError error = GracefulError.builder()
                .occurrenceTime(LocalDateTime.now())
                .statusCode(exception.getHttpStatusValue())
                .statusMessage(exception.getHttpStatusMessage())
                .build();
        logPrintStackTrace(exception);
        log.error(exceptionLogMode.equals("active") ? exception.getStringStackTrack() : "abstract-exception" );
        return buildResponseEntity(error);
    }

    @ExceptionHandler(ReadableServiceException.class)
    public ResponseEntity<Object> handleReadableException(
            final ReadableServiceException exception,
            final HttpServletRequest request
    ) {
        GracefulError error = GracefulError.builder()
                .occurrenceTime(LocalDateTime.now())
                .statusCode(exception.getHttpStatus().value())
                .statusMessage(exception.getHttpStatus().getReasonPhrase())
                .gracefulMessage(exception.getMessage())
                .build();
        logPrintStackTrace(exception);
        log.error(exceptionLogMode.equals("active") ? exception.getStringStackTrack() : "readable-exception" );
        return buildResponseEntity(error);
    }

    @ExceptionHandler(WritableServiceException.class)
    public ResponseEntity<Object> handleWritableException(
            final WritableServiceException exception,
            final HttpServletRequest request
    ) {
        GracefulError error = GracefulError.builder()
                .occurrenceTime(LocalDateTime.now())
                .statusCode(exception.getHttpStatus().value())
                .statusMessage(exception.getHttpStatus().getReasonPhrase())
                .gracefulMessage(exception.getMessage())
                .build();
        logPrintStackTrace(exception);
        log.error(exceptionLogMode.equals("active") ? exception.getStringStackTrack() : "writable-exception" );
        return buildResponseEntity(error);
    }

    @ExceptionHandler(FacadeServiceException.class)
    public ResponseEntity<Object> handleFacadeException(
            final FacadeServiceException exception,
            final HttpServletRequest request
    ) {
        GracefulError error = GracefulError.builder()
                .occurrenceTime(LocalDateTime.now())
                .statusCode(exception.getHttpStatus().value())
                .statusMessage(exception.getHttpStatus().getReasonPhrase())
                .gracefulMessage(exception.getMessage())
                .build();
        logPrintStackTrace(exception);
        log.error(exceptionLogMode.equals("active") ? exception.getStringStackTrack() : "facade-exception" );
        return buildResponseEntity(error);
    }

    @ExceptionHandler(CoreServiceException.class)
    public ResponseEntity<Object> handleCoreException(
            final CoreServiceException exception,
            final HttpServletRequest request
    ) {
        GracefulError error = GracefulError.builder()
                .occurrenceTime(LocalDateTime.now())
                .statusCode(exception.getHttpStatus().value())
                .statusMessage(exception.getHttpStatus().getReasonPhrase())
                .gracefulMessage(exception.getMessage())
                .build();
        logPrintStackTrace(exception);
        log.error(exceptionLogMode.equals("active") ? exception.getStringStackTrack() : "core-exception" );
        return buildResponseEntity(error);
    }


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException exception,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        String message = exception.getParameterName() + " parameter is missing";
        GracefulError gracefulError = GracefulError.builder()
                .occurrenceTime(LocalDateTime.now())
                .statusCode(BAD_REQUEST.value())
                .statusMessage(exception.getMessage())
                .gracefulMessage(message)
                .build();
        logPrintStackTrace(exception);
        log.error(exceptionLogMode.equals("active") ? getStringStackTrack(exception.getStackTrace()) : "missing-servlet-param-exception" );
        return buildResponseEntity(gracefulError);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            final HttpMediaTypeNotSupportedException exception,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        String gracefulMessage = " media type is not supported. Supported media types are ";
        GracefulError gracefulError = GracefulError.builder()
                .occurrenceTime(LocalDateTime.now())
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .statusMessage(exception.getMessage())
                .gracefulMessage(gracefulMessage)
                .build();
        logPrintStackTrace(exception);
        log.error(exceptionLogMode.equals("active") ? getStringStackTrack(exception.getStackTrace()) : "http-media-type-exception" );
        return buildResponseEntity(gracefulError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException exception,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {

        String gracefulMessage = "Validation error";
        GracefulError gracefulError = GracefulError.builder()
                .occurrenceTime(LocalDateTime.now())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .statusMessage(exception.getMessage())
                .gracefulMessage(gracefulMessage)
                .build();
        logPrintStackTrace(exception);
        log.error(exceptionLogMode.equals("active") ? getStringStackTrack(exception.getStackTrace()) : "method-argument-exception" );
        return buildResponseEntity(gracefulError);
    }

    @ExceptionHandler(value = {javax.validation.ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolation(
            javax.validation.ConstraintViolationException exception, final HttpServletRequest request
    ) {

        String gracefulMessage = "Validation error";
        GracefulError gracefulError = GracefulError.builder()
                .occurrenceTime(LocalDateTime.now())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .statusMessage(exception.getMessage())
                .gracefulMessage(gracefulMessage)
                .build();
        logPrintStackTrace(exception);
        log.error(exceptionLogMode.equals("active") ? getStringStackTrack(exception.getStackTrace()) : "constraint-violation-exception" );
        return buildResponseEntity(gracefulError);
    }


    @ExceptionHandler(value = {org.springframework.web.client.HttpClientErrorException.class})
    protected ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException exception, final HttpServletRequest request) {
        String gracefulMessage = "Validation error";
        final GracefulError error = GracefulError.builder()
                .statusMessage(exception.getStatusCode().getReasonPhrase())
                .statusCode(exception.getRawStatusCode())
                .occurrenceTime(LocalDateTime.now())
                .gracefulMessage(gracefulMessage)
                .build();
        logPrintStackTrace(exception);
        log.error(exceptionLogMode.equals("active") ? getStringStackTrack(exception.getStackTrace()) : "http-client-exception" );
        return buildResponseEntity(error);
    }


    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleHibernateConstraintViolation(
            ConstraintViolationException exception,
            final HttpServletRequest request
    ) {
        GracefulError error = GracefulError.builder()
                .statusMessage(BAD_REQUEST.getReasonPhrase())
                .statusCode(BAD_REQUEST.value())
                .occurrenceTime(LocalDateTime.now())
                .gracefulMessage("Validation error: " + exception.getConstraintName())
                .build();
        logPrintStackTrace(exception);
        log.error(exceptionLogMode.equals("active") ? getStringStackTrack(exception.getStackTrace()) : "hibernate-constraint-exception" );
        return buildResponseEntity(error);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            final HttpMessageNotReadableException exception,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        String message = "Malformed JSON request";
        GracefulError error = GracefulError
                .builder()
                .statusCode(BAD_REQUEST.value())
                .statusMessage(BAD_REQUEST.getReasonPhrase())
                .occurrenceTime(LocalDateTime.now())
                .gracefulMessage(message)
                .build();
        logPrintStackTrace(exception);
        log.error(exceptionLogMode.equals("active") ? getStringStackTrack(exception.getStackTrace()) : "http-message-not-readable-exception" );
        return buildResponseEntity(error);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            final HttpMessageNotWritableException exception,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        String message = "Error writing JSON output";

        GracefulError error = GracefulError
                .builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .statusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .occurrenceTime(LocalDateTime.now())
                .gracefulMessage(message)
                .build();
        logPrintStackTrace(exception);

        log.error(exceptionLogMode.equals("active") ? getStringStackTrack(exception.getStackTrace()) : "http-message-not-writable-exception" );
        return buildResponseEntity(error);
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            final NoHandlerFoundException exception,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {

        GracefulError error = GracefulError
                .builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .statusMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .occurrenceTime(LocalDateTime.now())
                .gracefulMessage(String.format("Could not find the %s method for URL %s", exception.getHttpMethod(), exception.getRequestURL()))
                .build();
        logPrintStackTrace(exception);
        //log.error(exceptionLogMode.equals("active") ? getStringStackTrack(exception.getStackTrace()) : "http-message-not-found-exception" );
        return buildResponseEntity(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            final MethodArgumentTypeMismatchException exception,
            final HttpServletRequest request
    ) {

        GracefulError error = GracefulError
                .builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .statusMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .occurrenceTime(LocalDateTime.now())
                .gracefulMessage(String.format(
                        "The parameter '%s' of value '%s' could not be converted to type '%s'",
                        exception.getName(), exception.getValue(), exception.getRequiredType().getSimpleName()))
                .build();
        logPrintStackTrace(exception);
        log.error(exceptionLogMode.equals("active") ? getStringStackTrack(exception.getStackTrace()) : "method-argument-type-mismatch-exception" );
        return buildResponseEntity(error);
    }


    protected ResponseEntity<Object> buildResponseEntity(final GracefulError error) {
        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getStatusCode()));
    }


    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleValidationException(final HttpServletRequest request, final ValidationException exception) {

        GracefulError error = GracefulError
                .builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .statusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .occurrenceTime(LocalDateTime.now())
                .gracefulMessage(String.format(
                        "The parameter '%s' of value '%s' could not be converted to type '%s'",
                        exception.getCause(), exception.getMessage(), exception.getSuppressed()))
                .build();
        logPrintStackTrace(exception);
        log.error(exceptionLogMode.equals("active") ? getStringStackTrack(exception.getStackTrace()) : "validation-exception" );
        return buildResponseEntity(error);

    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFound(final HttpServletRequest request, final EntityNotFoundException entityNotFoundException) {

        final GracefulError error = new GracefulError();

        return buildResponseEntity(error);

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleUnknownException(final HttpServletRequest request, Exception exception) {

        GracefulError error = GracefulError
                .builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .statusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .occurrenceTime(LocalDateTime.now())
                .gracefulMessage(String.format(
                        "The parameter '%s' of value '%s' could not be converted to type '%s'",
                        exception.getCause(), exception.getMessage(), exception.getSuppressed()))
                .build();
        logPrintStackTrace(exception);
        log.error(exceptionLogMode.equals("active") ? getStringStackTrack(exception.getStackTrace()) : "unknown-exception" );
        return buildResponseEntity(error);
    }

    public static String getStringStackTrack(StackTraceElement[] stackTraceElements) {
        String stackTrace = "";
        for (int i = 0; i < stackTraceElements.length; i++) {
            StackTraceElement element = stackTraceElements[i];
            stackTrace = stackTrace + "\n" + element.toString();
        }
        stackTrace =  stackTrace + "\n";
        stackTrace =  stackTrace + "\n"+"***********************************************************************";
        stackTrace =  stackTrace + "\n"+"***********************************************************************";
        stackTrace =  stackTrace + "\n";

        return stackTrace;
    }

    public void logPrintStackTrace(Exception exception) {
        exception.printStackTrace();
    }

}
