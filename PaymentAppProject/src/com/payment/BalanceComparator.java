package com.payment;

import java.util.Comparator;

public class BalanceComparator implements Comparator<Account> {
    @Override
    public int compare(Account a1, Account a2) {
        return Double.compare(a1.getBalance(), a2.getBalance());
    }
}