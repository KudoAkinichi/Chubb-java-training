package com.app.process;

import com.app.dto.Customer;

public abstract class ProcessPayment implements EmailProcessing {

    public int processcount;

    // Static method with throws clause
    public static void processFund(Customer initiator, Customer bene, double amount) 
            throws Exception {
        
        System.out.println("Processing payment...");

        if (initiator != null && bene != null) {
            if (initiator.getAmountbalance() > amount) {
                double balanceamount = initiator.getAmountbalance() - amount;
                initiator.setAmount(balanceamount);
                bene.setAmount(bene.getAmountbalance() + amount);
                System.out.println("Payment processed successfully");
            } else {
                System.out.println("Not having sufficient balance");
            }
        }
    }

    // Abstract method - must be implemented by subclasses
    public abstract boolean validateCustomer(Customer c1);

    // Concrete method
    public boolean validateEmail(Customer c1) {
        if (c1.getEmail() != null && c1.getEmail().contains("@")) {
            return true;
        }
        return false;
    }

    @Override
    public void intializeEmailServer() {
        System.out.println("Initialize server with Azure email service");
    }
}
