public class TransferRequest {

    // Sender fields
    private String senderName;
    private String senderCountry;
    private String senderAccount;
    private String senderIfsc;
    private double senderBalance;
    private double transferAmount;
    private String transferType;

    // Receiver fields
    private String receiverName;
    private String receiverCountry;
    private String receiverAccount;
    private String receiverIfsc;

    // Private constructor to force creation through the factory method
    private TransferRequest(String senderName, String senderCountry, String senderAccount, String senderIfsc,
            double senderBalance, double transferAmount, String transferType, String receiverName,
            String receiverCountry, String receiverAccount, String receiverIfsc) {
        this.senderName = senderName;
        this.senderCountry = senderCountry;
        this.senderAccount = senderAccount;
        this.senderIfsc = senderIfsc;
        this.senderBalance = senderBalance;
        this.transferAmount = transferAmount;
        this.transferType = transferType;
        this.receiverName = receiverName;
        this.receiverCountry = receiverCountry;
        this.receiverAccount = receiverAccount;
        this.receiverIfsc = receiverIfsc;
    }

    /**
     * Factory method to create a TransferRequest object from a CSV line.
     * This integrates parsing logic directly with object creation.
     */
    public static TransferRequest fromCsvLine(String line) throws IllegalArgumentException {
        if (line == null || line.isEmpty()) {
            throw new IllegalArgumentException("Line is empty.");
        }

        String[] parts = line.split(",");

        // Validate that we have the correct number of fields
        if (parts.length != 11) {
            throw new IllegalArgumentException("Invalid data format. Expected 11 fields, but found " + parts.length);
        }

        try {
            // Parse data from the parts array
            String senderName = parts[0];
            String senderCountry = parts[1];
            String senderAccount = parts[2];
            String senderIfsc = parts[3];
            double senderBalance = Double.parseDouble(parts[4]);
            double transferAmount = Double.parseDouble(parts[5]);
            String transferType = parts[6];
            String receiverName = parts[7];
            String receiverCountry = parts[8];
            String receiverAccount = parts[9];
            String receiverIfsc = parts[10];

            // Create and return the new object
            return new TransferRequest(senderName, senderCountry, senderAccount, senderIfsc, senderBalance,
                    transferAmount, transferType, receiverName, receiverCountry, receiverAccount, receiverIfsc);

        } catch (NumberFormatException e) {
            // Handle cases where balance or amount aren't valid numbers
            throw new IllegalArgumentException("Invalid number format for balance or transfer amount.", e);
        }
    }

    // --- Getters for business logic ---

    public double getTransferAmount() {
        return transferAmount;
    }

    public double getSenderBalance() {
        return senderBalance;
    }

    public String getSenderIfsc() {
        return senderIfsc;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }
}