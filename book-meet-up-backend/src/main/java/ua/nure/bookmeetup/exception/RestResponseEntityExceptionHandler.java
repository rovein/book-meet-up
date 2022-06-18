package ua.nure.bookmeetup.exception;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
@Log4j2
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundException(RuntimeException ex, WebRequest request) {
        log.error("Entity was not found", ex);
        HttpMethod httpMethod = Objects.requireNonNull(((ServletWebRequest) request).getHttpMethod());
        HttpStatus httpStatus = httpMethod.matches("DELETE") ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(value = {BookingCreationException.class})
    @SneakyThrows
    protected ResponseEntity<Object> handleBookingCreationException(RuntimeException ex, WebRequest request) {
        log.error("Error while creating a new booking", ex);
        String errorJsonBody = ex.getMessage();
        return handleExceptionInternal(ex, errorJsonBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
