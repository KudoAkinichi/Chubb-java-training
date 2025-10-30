package com.smartpay.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    
    private static int transactionCounter = 1000;
    
    private String transactionId;
    private String paymentMethod;
    private double amount;
    private TransactionStatus status;
    private LocalDateTime timestamp;
    private Beneficiary beneficiary;
    private String remarks;
    
    public Transaction(String paymentMethod, double amount, Beneficiary beneficiary) {
        this.transactionId = "TXN" + (++transactionCounter);
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.beneficiary = beneficiary;
        this.timestamp = LocalDateTime.now();
        this.status = TransactionStatus.PENDING;
    }
    
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public TransactionStatus getStatus() {
        return status;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return String.format(
            "TxnID: %s | Method: %s | Amount: ₹%.2f | Status: %s | To: %s | Time: %s | Remarks: %s",
            transactionId, paymentMethod, amount, status, 
            beneficiary.getName(), timestamp.format(formatter), 
            remarks != null ? remarks : "N/A"
        );
    }
}
