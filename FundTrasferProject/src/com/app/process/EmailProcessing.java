package com.app.process;

public interface EmailProcessing {

    // Abstract method
    boolean validateEmail();

    // Static method in interface (Java 8+)
    static boolean sendEmail() {
        System.out.println("Sending emails...");
        return true;
    }

    // Default method in interface (Java 8+)
    default void intializeEmailServer() {
        System.out.println("Initialize email server");
    }
}
