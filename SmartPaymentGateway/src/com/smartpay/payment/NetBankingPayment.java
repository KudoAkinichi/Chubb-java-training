package com.smartpay.payment;

import com.smartpay.exception.*;

public class NetBankingPayment extends Payment {
    
    private String accountNumber;
    private String password;

    public NetBankingPayment(String accountNumber, String password) {
        super("Net Banking");
        this.accountNumber = accountNumber;
        this.password = password;
    }

    @Override
    protected void authenticate() throws InvalidCredentialsException {
        System.out.println("Authenticating Net Banking for account: " + accountNumber);
        if (password == null || password.isEmpty() || password.equals("password")) {
            throw new InvalidCredentialsException("Invalid Net Banking credentials.");
        }
        System.out.println("✓ Net Banking login successful.");
    }

    @Override
    public void processPayment(double amount) {
        // Simulate processing
        System.out.println("Processing ₹" + amount + " via Net Banking from " + accountNumber);
    }
}
