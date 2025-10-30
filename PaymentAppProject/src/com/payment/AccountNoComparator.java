package com.payment;

import java.util.Comparator;

public class AccountNoComparator implements Comparator<Account> {
    @Override
    public int compare(Account a1, Account a2) {
        return a1.getAccountNo().compareTo(a2.getAccountNo());
    }
}