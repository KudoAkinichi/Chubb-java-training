import com.smartpay.model.Beneficiary;
import com.smartpay.payment.*;
import com.smartpay.service.*;
import com.smartpay.exception.*;

import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Using PaymentGateway from your previous code
        PaymentGateway gateway = new PaymentGateway(); 
        BeneficiaryManager beneficiaryManager = new BeneficiaryManager();
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║    SMART PAYMENT GATEWAY SYSTEM v2.0   ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        boolean continueProgram = true;
        
        while (continueProgram) {
            try {
                System.out.println("\n═══════════ MAIN MENU ═══════════");
                System.out.println("1. Add Beneficiary");
                System.out.println("2. View All Beneficiaries");
                System.out.println("3. Process Payment");
                System.out.println("4. View Transaction History");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1:
                        addBeneficiary(scanner, beneficiaryManager);
                        break;
                    case 2:
                        beneficiaryManager.displayAllBeneficiaries();
                        break;
                    case 3:
                        // Pass the gateway to process and log payment
                        processPayment(scanner, beneficiaryManager, gateway); 
                        break;
                    case 4:
                        gateway.displayTransactionHistory();
                        break;
                    case 5:
                        continueProgram = false;
                        System.out.println("\n✓ Thank you for using Smart Payment Gateway!");
                        break;
                    default:
                        System.out.println("✗ Invalid choice! Please try again.");
                }
                
            } catch (java.util.InputMismatchException e) {
                System.out.println("✗ Invalid input. Please enter a number.");
                scanner.nextLine(); // clear buffer
            } catch (Exception e) {
                System.out.println("✗ Unexpected error: " + e.getMessage());
                scanner.nextLine(); // clear buffer
            }
        }
        
        scanner.close();
    }
    
    private static void addBeneficiary(Scanner scanner, BeneficiaryManager manager) {
        // ... (This method is fine as-is from your original code)
        try {
            System.out.println("\n─── Add New Beneficiary ───");
            System.out.print("Enter Beneficiary ID: ");
            String id = scanner.nextLine();
            
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter Account Number: ");
            String accountNumber = scanner.nextLine();
            
            System.out.print("Enter Bank Name: ");
            String bankName = scanner.nextLine();
            
            Beneficiary beneficiary = new Beneficiary(id, name, accountNumber, bankName);
            manager.addBeneficiary(beneficiary);
            
            System.out.println("✓ Beneficiary added successfully!");
            
        } catch (NullPointerException e) {
            System.out.println("✗ Error: Null value encountered - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error adding beneficiary: " + e.getMessage());
        }
    }
    
    // Updated method signature to use PaymentGateway
    private static void processPayment(Scanner scanner, BeneficiaryManager manager, 
                                       PaymentGateway gateway) {
        
        // --- THIS IS THE FIX ---
        // Declare 'amount' outside the try block so catch blocks can see it
        double amount = 0.0;
        Payment payment = null; 
        Beneficiary beneficiary = null;

        try {
            if (manager.getBeneficiaryCount() == 0) {
                System.out.println("✗ No beneficiaries found! Please add a beneficiary first.");
                return;
            }
            
            System.out.println("\n─── Select Beneficiary ───");
            manager.displayAllBeneficiaries();
            System.out.print("Enter Beneficiary ID: ");
            String beneficiaryId = scanner.nextLine();
            
            beneficiary = manager.findBeneficiaryById(beneficiaryId);
            
            System.out.println("\n─── Select Payment Method ───");
            System.out.println("1. Credit Card");
            System.out.println("2. UPI");
            System.out.println("3. Wallet");
            System.out.println("4. Net Banking");
            System.out.print("Enter choice: ");
            int paymentChoice = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Enter amount: ₹");
            // Assign the value inside the try block
            amount = scanner.nextDouble();
            scanner.nextLine();
            
            // Instantiate the NEW CONCRETE classes
            switch (paymentChoice) {
                case 1:
                    System.out.print("Enter Card Number (16 digits): ");
                    String cardNumber = scanner.nextLine();
                    System.out.print("Enter CVV (3 digits): ");
                    String cvv = scanner.nextLine();
                    payment = new CreditCardPayment(cardNumber, cvv);
                    break;
                case 2:
                    System.out.print("Enter UPI ID: ");
                    String upiId = scanner.nextLine();
                    System.out.print("Enter UPI PIN (4 digits): ");
                    String upiPin = scanner.nextLine();
                    payment = new UPIPayment(upiId, upiPin);
                    break;
                case 3:
                    System.out.print("Enter Wallet Balance: ₹");
                    double balance = scanner.nextDouble();
                    scanner.nextLine();
                    payment = new WalletPayment(balance);
                    break;
                case 4:
                    System.out.print("Enter Account Number: ");
                    String accNum = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();
                    payment = new NetBankingPayment(accNum, password);
                    break;
                default:
                    System.out.println("✗ Invalid payment method!");
                    return;
            }
            
            // Call the gateway to execute the payment
            // The gateway will call payment.initiatePayment(...)
            gateway.executePayment(payment, amount, beneficiary);

            // Note: The gateway now handles success/failure logging.
            // We've moved the logic from Main.java into PaymentGateway.java

        } catch (BeneficiaryNotFoundException e) {
            System.out.println("✗ " + e.getMessage());
        } 
        // These exceptions are now caught inside PaymentGateway
        // We catch general exceptions here just in case
        catch (IllegalArgumentException e) {
            System.out.println("✗ Invalid Input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Unexpected error during payment setup: " + e.getMessage());
            e.printStackTrace();
        }
    }
}