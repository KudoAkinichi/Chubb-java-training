package com.smartpay.exception;

/**
 * Custom checked exception when beneficiary is not found
 */
public class BeneficiaryNotFoundException extends Exception {
    
    public BeneficiaryNotFoundException(String message) {
        super(message);
    }
    
    public BeneficiaryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
