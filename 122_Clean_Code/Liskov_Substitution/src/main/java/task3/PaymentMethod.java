package task3;

import task3.domain.PaymentResult;
import task3.domain.ValidationResult;

// Refactored base class with clear contracts
public abstract class PaymentMethod {
    /**
     * Validates payment details.
     * @return ValidationResult indicating success or specific failure reason
     */
    public abstract ValidationResult validatePaymentDetails();

    /**
     * Processes payment. Should NEVER throw business logic exceptions.
     * @param amount the amount to process
     * @return PaymentResult indicating success or failure with details
     */
    public abstract PaymentResult processPayment(double amount);

    /**
     * Template method ensuring consistent workflow
     */
    public final PaymentResult executePayment(double amount) {
        ValidationResult validation = validatePaymentDetails();
        if (!validation.isValid()) {
            return PaymentResult.failure("Validation failed: " + validation.getErrorMessage());
        }
        return processPayment(amount);
    }
}
