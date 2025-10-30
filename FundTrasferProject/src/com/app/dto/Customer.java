package com.app.dto;

public class Customer {

    private String name;
    private double amountbalance;
    private String email;
    private String accountno;

    // Default constructor
    public Customer() {
        System.out.println("Inside default constructor");
    }

    // Parameterized constructor
    public Customer(String custname, String custemail, String accountdetails, double balance) {
        this.name = custname;
        this.email = custemail;
        this.accountno = accountdetails;
        this.amountbalance = balance;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getAmountbalance() {
        return amountbalance;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountno() {
        return accountno;
    }

    // Setter for amount
    public void setAmount(double amount) {
        this.amountbalance = amount;
    }
}
