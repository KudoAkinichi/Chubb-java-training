package com.smartpay.model;

public class Beneficiary {
    
    private String beneficiaryId;
    private String name;
    private String accountNumber;
    private String bankName;
    
    public Beneficiary(String beneficiaryId, String name, String accountNumber, String bankName) {
        if (beneficiaryId == null || name == null || accountNumber == null) {
            throw new NullPointerException("Beneficiary details cannot be null");
        }
        this.beneficiaryId = beneficiaryId;
        this.name = name;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
    }
    
    // Getters
    public String getBeneficiaryId() {
        return beneficiaryId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getBankName() {
        return bankName;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Account: %s | Bank: %s", 
                           beneficiaryId, name, accountNumber, bankName);
    }
}
