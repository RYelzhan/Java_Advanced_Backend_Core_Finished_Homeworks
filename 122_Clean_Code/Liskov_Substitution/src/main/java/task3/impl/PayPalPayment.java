package task3.impl;

import task3.PaymentMethod;
import task3.domain.PaymentResult;
import task3.domain.ValidationResult;

public class PayPalPayment extends PaymentMethod {
    private final String email;
    private final boolean isBankAccountLinked;

    public PayPalPayment(String email, boolean isBankAccountLinked) {
        this.email = email;
        this.isBankAccountLinked = isBankAccountLinked;
    }

    @Override
    public ValidationResult validatePaymentDetails() {
        if (email == null || !email.contains("@")) {
            return ValidationResult.invalid("Invalid PayPal email");
        }
        // Check bank account linkage during validation (not during processing!)
        if (!isBankAccountLinked) {
            return ValidationResult.invalid("PayPal account not linked to bank account");
        }
        return ValidationResult.valid();
    }

    @Override
    public PaymentResult processPayment(double amount) {
        try {
            // Log in to PayPal and process payment
            String transactionId = "PP-" + System.currentTimeMillis();
            return PaymentResult.success(transactionId);
        } catch (Exception e) {
            return PaymentResult.failure("PayPal processing failed: " + e.getMessage());
        }
    }
}
