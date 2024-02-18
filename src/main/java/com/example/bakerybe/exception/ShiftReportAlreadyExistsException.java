package com.example.bakerybe.exception;

public class ShiftReportAlreadyExistsException extends RuntimeException{

    public ShiftReportAlreadyExistsException(String message) {
        super(message);
    }
}
