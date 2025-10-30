package com.smartpay.payment;

import com.smartpay.exception.*;

// This is the CONCRETE class that extends the abstract Payment class
public class CreditCardPayment extends Payment {
    
    private String cardNumber;
    private String cvv;

    // Constructor that matches Main.java
    public CreditCardPayment(String cardNumber, String cvv) {
        super("Credit Card"); // Calls the parent constructor
        
        // Basic validation
        if (cardNumber == null || cardNumber.length() != 16) {
            throw new IllegalArgumentException("Invalid card number. Must be 16 digits.");
        }
        if (cvv == null || cvv.length() != 3) {
            throw new IllegalArgumentException("Invalid CVV. Must be 3 digits.");
        }
        
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    @Override
    protected void authenticate() throws InvalidCredentialsException {
        // Real-world: Call a bank API
        System.out.println("Authenticating Credit Card ending in " + cardNumber.substring(12));
        if (cvv.equals("000")) { // Dummy check for failure
            throw new InvalidCredentialsException("Invalid CVV or Card Number.");
        }
        // Simulate a 1-second auth delay
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    @Override
    public void processPayment(double amount) 
        throws InsufficientBalanceException, PaymentGatewayTimeoutException {
        // Real-world: Connect to a payment gateway
        
        // Simulate a timeout
        if (cardNumber.endsWith("1234")) {
             throw new PaymentGatewayTimeoutException("Gateway timed out.");
        }
        
        // Simulate insufficient funds
        if (amount > 20000) { // Dummy limit
            throw new InsufficientBalanceException("Credit limit exceeded.");
        }
        
        System.out.println("Processing ₹" + amount + " from card to " + beneficiary.getAccountNumber());
    }
}