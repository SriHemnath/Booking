package com.assignment.bookingService.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * global exception handler - handles exceptions in booking application
 */

@ControllerAdvice
public class BookingExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BookingErrorResponse> handleException(PaymentException exc) {
        BookingErrorResponse error = new BookingErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<BookingErrorResponse> handleException(BookingNotFoundException exc) {
        BookingErrorResponse error = new BookingErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
