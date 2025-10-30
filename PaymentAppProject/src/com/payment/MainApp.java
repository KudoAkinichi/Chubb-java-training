package com.payment;

// You can import other classes you need, like ArrayList
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainApp {

    public static void main(String[] args) {
        System.out.println("Payment Application is Starting...");

        Account acc1 = new Account(
            "Aryan Kumar",       // accountHolderName
            "123456789",         // accountNo
            "TRX001",            // transCode
            "India",             // country
            "SBIN000123",        // ifscCode
            5000.75              // balance
        );

        Account acc2 = new Account(
            "Zoya Singh",
            "987654321",
            "TRX002",
            "India",
            "HDFC000456",
            12000.00
        );


        System.out.println("New account created for: " + acc1.getAccountHolderName());
        

        System.out.println("Account details: " + acc1);


        List<Account> accountList = new ArrayList<>();
        accountList.add(acc1);
        accountList.add(acc2);

        System.out.println("\n--- Unsorted List ---");
        for (Account acc : accountList) {
            System.out.println(acc.getAccountHolderName());
        }

        // Sort using the default compareTo (by name)
        Collections.sort(accountList);

        System.out.println("\n--- Sorted List (by Name) ---");
        for (Account acc : accountList) {
            System.out.println(acc.getAccountHolderName());
        }
    }
}