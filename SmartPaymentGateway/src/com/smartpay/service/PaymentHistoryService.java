package com.smartpay.service;

import com.smartpay.model.Beneficiary;
import com.smartpay.payment.Payment;
import com.smartpay.model.Transaction; // Assuming you have this class
import com.smartpay.model.TransactionStatus; // Assuming you have this enum

import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryService {
    
    // You'll need a Transaction class. I'll create a simple placeholder.
    // Replace this with your own 'com.smartpay.model.Transaction'
    private static class SimpleTransaction {
        String id;
        double amount;
        String beneficiaryName;
        String status;
        String paymentMethod;
        java.util.Date timestamp;

        public SimpleTransaction(String method, double amt, String bName, String status) {
            this.id = "TXN" + System.currentTimeMillis();
            this.amount = amt;
            this.beneficiaryName = (bName != null) ? bName : "N/A";
            this.status = status;
            this.paymentMethod = (method != null) ? method : "N/A";
            this.timestamp = new java.util.Date();
        }

        @Override
        public String toString() {
            return String.format("| %-17s | %-20s | %-15s | ₹%-10.2f | %-10s |",
                id, timestamp, beneficiaryName, amount, status);
        }
    }

    private List<SimpleTransaction> transactions = new ArrayList<>();

    public void addTransaction(Payment payment, double amount, Beneficiary beneficiary, String status) {
        String method = (payment != null) ? payment.getPaymentMethod() : "Setup Failed";
        String bName = (beneficiary != null) ? beneficiary.getName() : "Unknown";
        transactions.add(new SimpleTransaction(method, amount, bName, status));
    }

    public void displayTransactionHistory() {
        System.out.println("\n─────────────────────── Transaction History ───────────────────────");
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        
        System.out.println(String.format("| %-17s | %-20s | %-15s | %-11s | %-10s |",
            "Transaction ID", "Timestamp", "Beneficiary", "Amount", "Status"));
        System.out.println("|-------------------|----------------------|-----------------|-------------|------------|");
        
        for (SimpleTransaction tx : transactions) {
            System.out.println(tx);
        }
        System.out.println("───────────────────────────────────────────────────────────────────");
    }
}
