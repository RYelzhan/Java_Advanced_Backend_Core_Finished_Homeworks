package task3.domain;

// Result pattern to handle success/failure without exceptions
public class PaymentResult {
    private final boolean success;
    private final String message;
    private final String transactionId;

    private PaymentResult(boolean success, String message, String transactionId) {
        this.success = success;
        this.message = message;
        this.transactionId = transactionId;
    }

    public static PaymentResult success(String transactionId) {
        return new PaymentResult(true, "Payment processed successfully", transactionId);
    }

    public static PaymentResult failure(String message) {
        return new PaymentResult(false, message, null);
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getTransactionId() { return transactionId; }
}
