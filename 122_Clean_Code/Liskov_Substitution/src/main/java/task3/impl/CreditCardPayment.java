package task3.impl;

import task3.PaymentMethod;
import task3.domain.PaymentResult;
import task3.domain.ValidationResult;

import java.time.YearMonth;

public class CreditCardPayment extends PaymentMethod {
    private final String cardNumber;
    private final String cvv;
    private final String expiryDate;

    public CreditCardPayment(String cardNumber, String cvv, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    @Override
    public ValidationResult validatePaymentDetails() {
        // Card number validation
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            return ValidationResult.invalid("Card number is required");
        }

        String cleanCardNumber = cardNumber.replaceAll("\\s+", ""); // Remove spaces

        if (!cleanCardNumber.matches("\\d+")) {
            return ValidationResult.invalid("Card number must contain only digits");
        }

        if (cleanCardNumber.length() < 13 || cleanCardNumber.length() > 19) {
            return ValidationResult.invalid("Card number must be between 13 and 19 digits");
        }

        if (!isValidLuhn(cleanCardNumber)) {
            return ValidationResult.invalid("Invalid card number (failed Luhn check)");
        }

        // CVV validation
        if (cvv == null || cvv.trim().isEmpty()) {
            return ValidationResult.invalid("CVV is required");
        }

        if (!cvv.matches("\\d{3,4}")) {
            return ValidationResult.invalid("CVV must be 3 or 4 digits");
        }

        // Expiry date validation
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            return ValidationResult.invalid("Expiry date is required");
        }

        if (!expiryDate.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            return ValidationResult.invalid("Expiry date must be in MM/YY format");
        }

        if (isCardExpired(expiryDate)) {
            return ValidationResult.invalid("Card has expired");
        }

        return ValidationResult.valid();
    }

    @Override
    public PaymentResult processPayment(double amount) {
        try {
            // Simulate payment processing
            String transactionId = "CC-" + System.currentTimeMillis();
            return PaymentResult.success(transactionId);
        } catch (Exception e) {
            return PaymentResult.failure("Credit card processing failed: " + e.getMessage());
        }
    }

    /**
     * Validates card number using Luhn algorithm (mod 10 check)
     * @param cardNumber the card number to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidLuhn(String cardNumber) {
        int sum = 0;
        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }

    /**
     * Checks if the card has expired
     * @param expiryDate expiry date in MM/YY format
     * @return true if expired, false otherwise
     */
    private boolean isCardExpired(String expiryDate) {
        try {
            String[] parts = expiryDate.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]) + 2000; // Convert YY to YYYY

            YearMonth cardExpiry = YearMonth.of(year, month);
            YearMonth now = YearMonth.now();

            return cardExpiry.isBefore(now);
        } catch (Exception e) {
            return true; // Treat parsing errors as expired
        }
    }
}
