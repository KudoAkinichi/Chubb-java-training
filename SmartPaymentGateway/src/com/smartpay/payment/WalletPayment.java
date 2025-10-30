package com.smartpay.payment;

import com.smartpay.exception.*;

public class WalletPayment extends Payment {
    
    private double balance;

    public WalletPayment(double balance) {
        super("Wallet");
        this.balance = balance;
    }

    @Override
    protected void authenticate() {
        // Wallet balance is pre-authenticated, but we check balance here
        System.out.println("Checking wallet balance...");
    }

    @Override
    public void processPayment(double amount) throws InsufficientBalanceException {
        if (this.balance < amount) {
            throw new InsufficientBalanceException("Insufficient wallet balance. Available: ₹" + this.balance);
        }
        this.balance -= amount;
        System.out.println("Processing ₹" + amount + " from Wallet. New balance: ₹" + this.balance);
    }
}
