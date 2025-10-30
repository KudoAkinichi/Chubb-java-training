package com.smartpay.exception;

/**
 * Custom checked exception for gateway timeout scenarios
 */
public class PaymentGatewayTimeoutException extends Exception {
    
    public PaymentGatewayTimeoutException(String message) {
        super(message);
    }
    
    public PaymentGatewayTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
