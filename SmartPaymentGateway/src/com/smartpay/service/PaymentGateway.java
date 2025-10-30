package com.smartpay.service;

import com.smartpay.model.*;
import com.smartpay.payment.Payment;
import com.smartpay.exception.*;

// Assuming you have these interfaces. If not, you can remove them.
import com.smartpay.service.Refundable;
import com.smartpay.service.Retryable;


public class PaymentGateway {
    
    private Transaction[] transactions;
    private int transactionCount;
    private static final int MAX_TRANSACTIONS = 1000;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    
    public PaymentGateway() {
        this.transactions = new Transaction[MAX_TRANSACTIONS];
        this.transactionCount = 0;
    }
    
    /**
     * Execute payment with retry mechanism
     */
    public void executePayment(Payment payment, double amount, Beneficiary beneficiary) {
        
        if (payment == null || beneficiary == null) {
            System.out.println("✗ Error: Payment or Beneficiary is null");
            return;
        }
        
        // Assuming Transaction class exists and has this constructor
        Transaction transaction = new Transaction(payment.getPaymentMethod(), amount, beneficiary);
        int attemptNumber = 1;
        boolean paymentSuccessful = false;
        
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║     INITIATING PAYMENT TRANSACTION   ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("Transaction ID: " + transaction.getTransactionId());
        System.out.println("Payment Method: " + payment.getPaymentMethod());
        System.out.println("Amount: ₹" + amount);
        System.out.println("Beneficiary: " + beneficiary.getName());
        System.out.println("─────────────────────────────────────────\n");
        
        while (attemptNumber <= MAX_RETRY_ATTEMPTS && !paymentSuccessful) {
            try {
                if (attemptNumber > 1) {
                    System.out.println("\n→ Attempt " + attemptNumber + " of " + MAX_RETRY_ATTEMPTS);
                    
                    // Check if payment supports retry
                    if (payment instanceof Retryable) {
                        Retryable retryable = (Retryable) payment;
                        if (!retryable.retry(attemptNumber)) {
                            System.out.println("✗ Maximum retry attempts exceeded for this payment method");
                            break;
                        }
                    } else {
                        System.out.println("✗ This payment method doesn't support retry");
                        break;
                    }
                    
                    // Wait before retry
                    Thread.sleep(1000);
                }
                
                // Initiate payment --- THIS IS THE FIX ---
                // We must pass the beneficiary object as defined in the abstract class
                payment.initiatePayment(amount, beneficiary);
                
                // Payment successful
                transaction.setStatus(TransactionStatus.SUCCESS);
                transaction.setRemarks("Payment completed successfully");
                paymentSuccessful = true;
                
                System.out.println("\n╔══════════════════════════════════════╗");
                System.out.println("║         ✓ PAYMENT SUCCESSFUL!        ║");
                System.out.println("╚══════════════════════════════════════╝");
                System.out.println(transaction);
                
            } catch (InvalidAmountException e) {
                // Non-retryable - invalid input
                transaction.setStatus(TransactionStatus.FAILED);
                transaction.setRemarks("Invalid amount: " + e.getMessage());
                System.out.println("\n✗ PAYMENT FAILED: " + e.getMessage());
                System.out.println("  This is a validation error. Please correct the amount and try again.");
                break; // Don't retry for validation errors
                
            } catch (InsufficientBalanceException e) {
                // Non-retryable - insufficient balance
                transaction.setStatus(TransactionStatus.FAILED);
                transaction.setRemarks("Insufficient balance");
                System.out.println("\n✗ PAYMENT FAILED: " + e.getMessage());
                System.out.println("  → Suggested Actions:");
                System.out.println("      1. Recharge/Top-up your account");
                System.out.println("      2. Choose alternate payment method");
                System.out.println("      3. Reduce payment amount");
                break; // Don't retry
                
            } catch (InvalidCredentialsException e) {
                // Non-retryable - security issue
                transaction.setStatus(TransactionStatus.FAILED);
                transaction.setRemarks("Authentication failed");
                System.out.println("\n✗ PAYMENT FAILED: Authentication Error");
                System.out.println("  → " + maskSensitiveInfo(e.getMessage()));
                System.out.println("  Please verify your credentials and try again.");
                break; // Don't retry
                
            } catch (PaymentGatewayTimeoutException e) {
                // Retryable - network/gateway issue
                System.out.println("\n⚠ TIMEOUT: " + e.getMessage());
                
                if (attemptNumber < MAX_RETRY_ATTEMPTS) {
                    System.out.println("  Will retry automatically...");
                } else {
                    transaction.setStatus(TransactionStatus.TIMEOUT);
                    transaction.setRemarks("Gateway timeout after " + MAX_RETRY_ATTEMPTS + " attempts");
                    System.out.println("  Maximum retry attempts reached.");
                }
                // Don't break - allow retry in next iteration
                
            } catch (InterruptedException e) {
                System.out.println("✗ Retry interrupted");
                transaction.setStatus(TransactionStatus.FAILED);
                transaction.setRemarks("Payment interrupted");
                Thread.currentThread().interrupt();
                break;
                
            } catch (TransactionFailedException e) {
                // Catch the general failure from the template method
                transaction.setStatus(TransactionStatus.FAILED);
                transaction.setRemarks("Transaction failed: " + e.getMessage());
                System.out.println("\n✗ TRANSACTION FAILED: " + e.getMessage());
                e.printStackTrace();
                break; // Stop retrying

            } catch (Exception e) {
                // Catch any unexpected runtime exceptions
                transaction.setStatus(TransactionStatus.FAILED);
                transaction.setRemarks("Unexpected error: " + e.getClass().getSimpleName());
                System.out.println("\n✗ UNEXPECTED ERROR: " + e.getMessage());
                e.printStackTrace();
                break;
            }
            
            attemptNumber++;
        }
        
        // Handle refund for failed high-value transactions
        if (!paymentSuccessful && amount > 10000 && payment instanceof Refundable) {
            System.out.println("\n→ Initiating automatic refund for high-value failed transaction...");
            Refundable refundable = (Refundable) payment;
            if (refundable.refund(amount)) {
                transaction.setStatus(TransactionStatus.REFUNDED);
            }
        }
        
        // Store transaction
        if (transactionCount < MAX_TRANSACTIONS) {
            transactions[transactionCount++] = transaction;
        }
    }
    
    /**
     * Mask sensitive information in error messages
     */
    private String maskSensitiveInfo(String message) {
        // Remove any potential sensitive data from logs
        return message.replaceAll("\\d{4}", "****")
                      .replaceAll("PIN", "***")
                      .replaceAll("password", "********");
    }
    
    /**
     * Display transaction history
     */
    public void displayTransactionHistory() {
        if (transactionCount == 0) {
            System.out.println("\nNo transactions found.");
            return;
        }
        
        System.out.println("\n╔═══════════════════════════════════════════════════╗");
        System.out.println("║             TRANSACTION HISTORY                   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i] != null) {
                System.out.println((i + 1) + ". " + transactions[i]);
            }
        }
    }
    
    /**
     * Get transaction by ID
     */
    public Transaction findTransactionById(String transactionId) {
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i] != null && 
                transactions[i].getTransactionId().equals(transactionId)) {
                return transactions[i];
            }
        }
        return null;
    }
}