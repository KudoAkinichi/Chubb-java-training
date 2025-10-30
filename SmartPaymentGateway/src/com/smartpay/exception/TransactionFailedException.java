package com.smartpay.exception;

/**
 * Wrapper exception that encapsulates other payment exceptions
 */
public class TransactionFailedException extends Exception {
    
    public TransactionFailedException(String message) {
        super(message);
    }
    
    public TransactionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Get the root cause of the transaction failure
     */
    public Throwable getRootCause() {
        Throwable cause = getCause();
        while (cause != null && cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause;
    }
}
