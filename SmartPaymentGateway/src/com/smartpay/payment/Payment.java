package com.smartpay.payment;

import com.smartpay.exception.*;
import com.smartpay.model.Beneficiary;

// This is your abstract base class
public abstract class Payment {
    
    protected String paymentMethod;
    protected Beneficiary beneficiary;
    
    public Payment(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    // TEMPLATE METHOD
    // THE FIX IS HERE:
    // We now declare ALL exceptions that can be thrown in the 'throws' clause.
    // This allows PaymentGateway to catch the specific ones.
    public final void initiatePayment(double amount, Beneficiary beneficiary) 
            throws InvalidAmountException, InsufficientBalanceException, 
                   InvalidCredentialsException, PaymentGatewayTimeoutException, 
                   TransactionFailedException {
        
        try {
            this.beneficiary = beneficiary;

            // These protected/abstract methods are declared to throw
            // the specific exceptions. They will now propagate up
            // to the caller (PaymentGateway)
            validateAmount(amount);
            authenticate();
            processPayment(amount);
            
            System.out.println("✓ Payment successful to " + beneficiary.getName());
        
        } 
        // We NO LONGER catch the 4 specific exceptions here.
        // We let them propagate up to PaymentGateway.
        
        // We ONLY catch UNEXPECTED exceptions
        catch (Exception e) {
            
            // Check if it's one of the exceptions we expect the gateway to handle.
            // If so, re-throw it as-is.
            if (e instanceof InvalidAmountException ||
                e instanceof InsufficientBalanceException ||
                e instanceof InvalidCredentialsException ||
                e instanceof PaymentGatewayTimeoutException) {
                
                // Re-throw the specific exception
                throw e; 
            }
            
            // If it's a truly UNKNOWN exception (e.g., NullPointerException),
            // wrap this one in a TransactionFailedException.
            System.out.println("✗ Unexpected failure in payment processing: " + e.getMessage());
            throw new TransactionFailedException("Unexpected failure: " + e.getMessage(), e);
        }
    }
    
    // Common validation
    protected void validateAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be positive. Provided: ₹" + amount);
        }
        if (amount > 100000) {
            throw new InvalidAmountException("Amount exceeds maximum limit of ₹100,000");
        }
    }
    
    // Abstract methods - to be implemented by subclasses
    protected abstract void authenticate() throws InvalidCredentialsException;
    
    public abstract void processPayment(double amount) 
        throws InsufficientBalanceException, PaymentGatewayTimeoutException;
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
}