package com.smartpay.service;

import com.smartpay.model.Beneficiary;
import com.smartpay.exception.BeneficiaryNotFoundException;

public class BeneficiaryManager {
    
    private Beneficiary[] beneficiaries;
    private int count;
    private static final int MAX_BENEFICIARIES = 100;
    
    public BeneficiaryManager() {
        this.beneficiaries = new Beneficiary[MAX_BENEFICIARIES];
        this.count = 0;
    }
    
    /**
     * Add a new beneficiary
     */
    public void addBeneficiary(Beneficiary beneficiary) {
        if (beneficiary == null) {
            throw new NullPointerException("Beneficiary cannot be null");
        }
        
        if (count >= MAX_BENEFICIARIES) {
            System.out.println("✗ Maximum beneficiary limit reached");
            return;
        }
        
        // Check for duplicate ID
        try {
            findBeneficiaryById(beneficiary.getBeneficiaryId());
            System.out.println("✗ Beneficiary with ID " + beneficiary.getBeneficiaryId() + " already exists");
            return;
        } catch (BeneficiaryNotFoundException e) {
            // Good - no duplicate found
        }
        
        beneficiaries[count++] = beneficiary;
    }
    
    /**
     * Find beneficiary by ID
     */
    public Beneficiary findBeneficiaryById(String beneficiaryId) 
            throws BeneficiaryNotFoundException {
        
        if (beneficiaryId == null) {
            throw new NullPointerException("Beneficiary ID cannot be null");
        }
        
        for (int i = 0; i < count; i++) {
            if (beneficiaries[i] != null && 
                beneficiaries[i].getBeneficiaryId().equals(beneficiaryId)) {
                return beneficiaries[i];
            }
        }
        
        throw new BeneficiaryNotFoundException(
            "Beneficiary with ID '" + beneficiaryId + "' not found. Please add beneficiary first."
        );
    }
    
    /**
     * Display all beneficiaries
     */
    public void displayAllBeneficiaries() {
        if (count == 0) {
            System.out.println("No beneficiaries found.");
            return;
        }
        
        System.out.println("\n═══ Registered Beneficiaries ═══");
        for (int i = 0; i < count; i++) {
            if (beneficiaries[i] != null) {
                System.out.println((i + 1) + ". " + beneficiaries[i]);
            }
        }
    }
    
    /**
     * Get total beneficiary count
     */
    public int getBeneficiaryCount() {
        return count;
    }
    
    /**
     * Remove beneficiary by ID
     */
    public boolean removeBeneficiary(String beneficiaryId) 
            throws BeneficiaryNotFoundException {
        
        for (int i = 0; i < count; i++) {
            if (beneficiaries[i] != null && 
                beneficiaries[i].getBeneficiaryId().equals(beneficiaryId)) {
                
                // Shift elements left
                for (int j = i; j < count - 1; j++) {
                    beneficiaries[j] = beneficiaries[j + 1];
                }
                beneficiaries[--count] = null;
                return true;
            }
        }
        
        throw new BeneficiaryNotFoundException("Beneficiary not found: " + beneficiaryId);
    }
}
