package com.smartpay.payment;

import com.smartpay.exception.*;

public class UPIPayment extends Payment {
    
    private String upiId;
    private String upiPin;

    public UPIPayment(String upiId, String upiPin) {
        super("UPI");
        this.upiId = upiId;
        this.upiPin = upiPin;
    }

    @Override
    protected void authenticate() throws InvalidCredentialsException {
        System.out.println("Authenticating UPI ID: " + upiId);
        if (upiPin == null || upiPin.length() != 4) {
            throw new InvalidCredentialsException("Invalid UPI PIN. Must be 4 digits.");
        }
        if (upiPin.equals("0000")) {
             throw new InvalidCredentialsException("Invalid UPI PIN.");
        }
        System.out.println("✓ UPI PIN accepted.");
    }

    @Override
    public void processPayment(double amount) throws InsufficientBalanceException {
        // Simulate insufficient funds
        if (amount > 5000) {
            throw new InsufficientBalanceException("Insufficient funds in linked bank account.");
        }
        System.out.println("Processing ₹" + amount + " via UPI to " + beneficiary.getName());
    }
}
