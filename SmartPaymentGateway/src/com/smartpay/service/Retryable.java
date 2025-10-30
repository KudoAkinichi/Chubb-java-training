package com.smartpay.service;

public interface Retryable {
    
    /**
     * Check if payment can be retried
     * @param attemptNumber Current retry attempt number
     * @return true if retry is allowed
     */
    boolean retry(int attemptNumber);
}
