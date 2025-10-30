package com.smartpay.exception;

/**
 * Custom checked exception for insufficient balance scenarios
 */
public class InsufficientBalanceException extends Exception {
    
    public InsufficientBalanceException(String message) {
        super(message);
    }
    
    public InsufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
