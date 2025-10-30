package com.app.dto;

/**
 * Custom User-Defined Checked Exception
 * Must be caught or declared in throws clause
 */
public class AccountBalanceException extends Exception {

    public AccountBalanceException(String msg) {
        super(msg);
    }
}
