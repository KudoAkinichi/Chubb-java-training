import java.util.*;
import com.app.dto.AccountBalanceException;
import com.app.dto.Customer;
import com.app.dto.NEFTProcessFund;
import com.app.process.ProcessPayment;
import java.io.*;

public class FundTransfer {

    public int count; // heap

    public static void main(String[] args) {
        System.out.println("=== Fund Transfer System ===\n");

        FundTransfer fdobj = new FundTransfer();

        // ========================================
        // SCENARIO 1: Try-Catch with ArithmeticException
        // ========================================
        System.out.println("--- Scenario 1: Division by Zero ---");
        try {
            int result = 5 / 0;
        } catch (ArithmeticException ex) {
            System.out.println("Caught ArithmeticException: " + ex.getMessage());
        }
        System.out.println();

        // ========================================
        // SCENARIO 2: Try with Multiple Catch
        // ========================================
        System.out.println("--- Scenario 2: Multiple Catch Blocks ---");
        try {
            int arr[] = {1, 2, 3};
            System.out.println(arr[5]); // ArrayIndexOutOfBoundsException
        } catch (ArithmeticException ex) {
            System.out.println("Arithmetic error: " + ex.getMessage());
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Array index error: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("General exception: " + ex.getMessage());
        }
        System.out.println();

        // ========================================
        // SCENARIO 3: FileNotFoundException
        // ========================================
        System.out.println("--- Scenario 3: FileNotFoundException ---");
        try {
            File f = new File("nonexistent.txt");
            FileInputStream fis = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + ex.getMessage());
        }
        System.out.println();

        // ========================================
        // SCENARIO 4: IOException
        // ========================================
        System.out.println("--- Scenario 4: IOException ---");
        try {
            File f = new File("test.txt");
            f.createNewFile(); // Create file if doesn't exist
            FileReader fr = new FileReader(f);
            fr.read();
            fr.close();
            System.out.println("File operations successful");
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println();

        // ========================================
        // SCENARIO 5: Custom User-Defined Exception
        // ========================================
        System.out.println("--- Scenario 5: Custom Exception (AccountBalanceException) ---");
        
        Customer c1 = new Customer("James", "james@gmail.com", "43432432442", 4343);
        Customer c2 = new Customer("Robin", "robin@gmail.com", "43432432441", 50000);

        NEFTProcessFund neftobj = new NEFTProcessFund();

        System.out.println("Customer balance initiator: " + c1.getAmountbalance());
        System.out.println("Customer balance beneficiary: " + c2.getAmountbalance());

        boolean isValidCustomer = neftobj.validateCustomer(c2);
        
        if (isValidCustomer) {
            try {
                neftobj.processFund(c1, c2, 30000); // Will throw AccountBalanceException
            } catch (AccountBalanceException e) {
                System.out.println("Custom Exception Caught: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Customer is not valid");
        }
        System.out.println();

        // ========================================
        // SCENARIO 6: Throw and Throws Demo
        // ========================================
        System.out.println("--- Scenario 6: Throw and Throws ---");
        try {
            fdobj.demonstrateThrowAndThrows(-100);
        } catch (IllegalArgumentException ex) {
            System.out.println("Caught exception: " + ex.getMessage());
        }
        System.out.println();

        // ========================================
        // SCENARIO 7: User-Defined Runtime Exception
        // ========================================
        System.out.println("--- Scenario 7: Custom Runtime Exception ---");
        try {
            fdobj.processWithRuntimeException(0);
        } catch (InsufficientFundRuntimeException ex) {
            System.out.println("Runtime Exception Caught: " + ex.getMessage());
        }
        System.out.println();

        System.out.println("=== Program Completed ===");
    }

    // Method demonstrating 'throw' and 'throws'
    public void demonstrateThrowAndThrows(double amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative: " + amount);
        }
        System.out.println("Amount is valid: " + amount);
    }

    // Method with custom runtime exception
    public void processWithRuntimeException(double balance) {
        if (balance <= 0) {
            throw new InsufficientFundRuntimeException("Insufficient funds! Balance: " + balance);
        }
        System.out.println("Processing with balance: " + balance);
    }
}

// ========================================
// User-Defined Runtime Exception
// ========================================
class InsufficientFundRuntimeException extends RuntimeException {
    public InsufficientFundRuntimeException(String message) {
        super(message);
    }
}
