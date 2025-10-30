package com.smartpay.service;

public interface Refundable {
    
    /**
     * Process refund for the payment
     * @param amount Amount to refund
     * @return true if refund successful
     */
    boolean refund(double amount);
}
