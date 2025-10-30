import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FundTransferService {

    public static void main(String[] args) {
        // 1. Define the file to read
        String fileName = "transfers.csv";

        // 2. Call the processing method
        processTransfers(fileName);
    }

    public static void processTransfers(String fileName) {
        double totalAmountPaidByHdfc = 0;
        int successfulTransfers = 0;
        int failedTransfers = 0;

        System.out.println("--- Starting Fund Transfer Processing ---");

        // 3. Read the file line by line using try-with-resources
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            int lineNumber = 0;

            // 4. Loop through each line in the file
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                TransferRequest request;

                // 5. Create Object: Try to parse the line into a TransferRequest object
                try {
                    request = TransferRequest.fromCsvLine(line);
                } catch (IllegalArgumentException e) {
                    System.out.println("[Line " + lineNumber + "] FAILED: Invalid data. " + e.getMessage());
                    failedTransfers++;
                    continue; // Skip to the next line
                }

                System.out.println("\n[Line " + lineNumber + "] Processing: " + request.getSenderName() +
                        " -> " + request.getReceiverName() + " for " + request.getTransferAmount());

                // 6. Apply Business Rules

                // Rule 1: Transfer amount must be greater than zero
                if (request.getTransferAmount() <= 0) {
                    System.out.println("  STATUS: FAILED. Transfer amount must be greater than zero.");
                    failedTransfers++;
                    continue;
                }

                // Rule 2: Sender must have sufficient balance
                if (request.getSenderBalance() < request.getTransferAmount()) {
                    System.out.println("  STATUS: FAILED. Insufficient balance. (Balance: " +
                            request.getSenderBalance() + ", Required: " + request.getTransferAmount() + ")");
                    failedTransfers++;
                    continue;
                }

                // 7. Success: All rules passed
                System.out.println("  STATUS: SUCCESS. Transfer complete.");
                successfulTransfers++;

                // 8. Aggregation: Add to total if the sender's bank is HDFC
                if (request.getSenderIfsc().startsWith("HDFC")) {
                    totalAmountPaidByHdfc += request.getTransferAmount();
                    System.out.println("  INFO: HDFC transfer logged.");
                }

            } // End of while loop

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        // 9. Print the final report
        System.out.println("\n--- Transfer Processing Complete ---");
        System.out.println("Total Successful Transfers: " + successfulTransfers);
        System.out.println("Total Failed Transfers: " + failedTransfers);
        System.out.println("------------------------------------");
        System.out.println("TOTAL AMOUNT PAID BY HDFC BANK: " + totalAmountPaidByHdfc);
    }
}