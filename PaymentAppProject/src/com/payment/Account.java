package com.payment;

public class Account implements Comparable<Account> {
    private String accountHolderName;
    private String accountNo;
    private String transCode;
    private String country;
    private String ifscCode;
    private double balance;

    // Constructor
    public Account(String accountHolderName, String accountNo, String transCode,
            String country, String ifscCode, double balance) {
        this.accountHolderName = accountHolderName;
        this.accountNo = accountNo;
        this.transCode = transCode;
        this.country = country;
        this.ifscCode = ifscCode;
        this.balance = balance;
    }

    // Getters and Setters
    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public int compareTo(Account other) {
        return this.accountHolderName.compareTo(other.accountHolderName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        return accountHolderName.equals(other.accountHolderName) &&
                accountNo.equals(other.accountNo);
    }

    @Override
    public int hashCode() {
        int result = accountHolderName.hashCode();
        result = 31 * result + accountNo.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountHolderName='" + accountHolderName + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", transCode='" + transCode + '\'' +
                ", country='" + country + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                ", balance=" + balance +
                '}';
    }
}