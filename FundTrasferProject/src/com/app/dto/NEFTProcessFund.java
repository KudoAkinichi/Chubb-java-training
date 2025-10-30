package com.app.dto;

import com.app.process.ProcessPayment;
import com.app.process.SMSProcessing;

public class NEFTProcessFund extends ProcessPayment implements SMSProcessing {

    // Static method with throws declaration
    public static void processFund(Customer initiator, Customer bene, double amount) 
            throws AccountBalanceException {
        
        System.out.println("Processing NEFT Fund Transfer...");

        if (initiator != null && bene != null) {
            if (initiator.getAmountbalance() > amount && amount > 2000000) {
                double balanceamount = initiator.getAmountbalance() - amount;
                initiator.setAmount(balanceamount);
                bene.setAmount(bene.getAmountbalance() + amount);
                System.out.println("Fund transferred immediately via NEFT");
            } else {
                // Throwing custom exception
                throw new AccountBalanceException(
                    "Not having sufficient balance or amount not eligible for NEFT (must be > 2000000)"
                );
            }
        }
    }

    @Override
    public boolean validateCustomer(Customer c1) {
        if (c1.getName() != null && !c1.getName().equals("Bin Laden")) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validateEmail() {
        return false;
    }

    @Override
    public boolean sendSMS(Customer c1) {
        System.out.println("Sent SMS to customer: " + c1.getName());
        return true;
    }
}
