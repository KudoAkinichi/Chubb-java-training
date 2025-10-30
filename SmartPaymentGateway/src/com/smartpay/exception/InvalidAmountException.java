package com.smartpay.exception;

/**
 * Custom checked exception for invalid payment amounts
 */
public class InvalidAmountException extends Exception {
    
    public InvalidAmountException(String message) {
        super(message);
    }
    
    public InvalidAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}
