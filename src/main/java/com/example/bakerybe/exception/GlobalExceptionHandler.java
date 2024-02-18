package com.example.bakerybe.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Entity not found error: {}", ex.getMessage());

        ExceptionResponseDto exceptionResponse = new ExceptionResponseDto("404", ex.getMessage(), null);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ShiftReportAlreadyExistsException.class)
    protected ResponseEntity<Object> handleShiftReportAlreadyExists(ShiftReportAlreadyExistsException ex) {
        log.error("Entity not found error: {}", ex.getMessage());

        ExceptionResponseDto exceptionResponse = new ExceptionResponseDto("400", ex.getMessage(), null);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
