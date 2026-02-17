package task3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import task3.domain.PaymentResult;
import task3.domain.ValidationResult;
import task3.impl.CreditCardPayment;

import static org.junit.jupiter.api.Assertions.*;

import java.time.YearMonth;

class CreditCardPaymentTest {

    // ==================== Card Number Tests ====================

    @Test
    @DisplayName("Valid card number should pass validation")
    void testValidCardNumber() {
        // Visa test card number (passes Luhn check)
        CreditCardPayment payment = new CreditCardPayment("4532015112830366", "123", "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertTrue(result.isValid());
    }

    @Test
    @DisplayName("Null card number should fail validation")
    void testNullCardNumber() {
        CreditCardPayment payment = new CreditCardPayment(null, "123", "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertEquals("Card number is required", result.getErrorMessage());
    }

    @Test
    @DisplayName("Empty card number should fail validation")
    void testEmptyCardNumber() {
        CreditCardPayment payment = new CreditCardPayment("", "123", "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertEquals("Card number is required", result.getErrorMessage());
    }

    @Test
    @DisplayName("Whitespace-only card number should fail validation")
    void testWhitespaceCardNumber() {
        CreditCardPayment payment = new CreditCardPayment("   ", "123", "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertEquals("Card number is required", result.getErrorMessage());
    }

    @Test
    @DisplayName("Card number with spaces should be accepted and cleaned")
    void testCardNumberWithSpaces() {
        // Valid Visa with spaces
        CreditCardPayment payment = new CreditCardPayment("4532 0151 1283 0366", "123", "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertTrue(result.isValid());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123abc456", "4532-0151-1283-0366", "4532.0151.1283.0366", "abcd1234efgh5678"})
    @DisplayName("Card number with non-digit characters should fail")
    void testCardNumberWithNonDigits(String cardNumber) {
        CreditCardPayment payment = new CreditCardPayment(cardNumber, "123", "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("must contain only digits"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "123456789012", "12345678901234567890"})
    @DisplayName("Card number with invalid length should fail")
    void testCardNumberInvalidLength(String cardNumber) {
        CreditCardPayment payment = new CreditCardPayment(cardNumber, "123", "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("between 13 and 19 digits"));
    }

    @Test
    @DisplayName("Card number failing Luhn check should fail validation")
    void testCardNumberFailsLuhnCheck() {
        // Invalid card number (fails Luhn check)
        CreditCardPayment payment = new CreditCardPayment("4532015112830367", "123", "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("failed Luhn check"));
    }

    @ParameterizedTest
    @CsvSource({
            "4532015112830366, Visa",           // Visa
            "5425233430109903, Mastercard",     // Mastercard
            "374245455400126, Amex",            // American Express
            "6011000991001201, Discover"        // Discover
    })
    @DisplayName("Valid card numbers from different issuers should pass Luhn check")
    void testValidCardNumbersFromDifferentIssuers(String cardNumber, String issuer) {
        CreditCardPayment payment = new CreditCardPayment(cardNumber, "123", "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertTrue(result.isValid(), issuer + " card should be valid");
    }

    // ==================== CVV Tests ====================

    @Test
    @DisplayName("Valid 3-digit CVV should pass validation")
    void testValid3DigitCVV() {
        CreditCardPayment payment = new CreditCardPayment("4532015112830366", "123", "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertTrue(result.isValid());
    }

    @Test
    @DisplayName("Valid 4-digit CVV should pass validation")
    void testValid4DigitCVV() {
        CreditCardPayment payment = new CreditCardPayment("4532015112830366", "1234", "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertTrue(result.isValid());
    }

    @Test
    @DisplayName("Null CVV should fail validation")
    void testNullCVV() {
        CreditCardPayment payment = new CreditCardPayment("4532015112830366", null, "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertEquals("CVV is required", result.getErrorMessage());
    }

    @Test
    @DisplayName("Empty CVV should fail validation")
    void testEmptyCVV() {
        CreditCardPayment payment = new CreditCardPayment("4532015112830366", "", "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertEquals("CVV is required", result.getErrorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"12", "12345", "1", "99999"})
    @DisplayName("CVV with invalid length should fail")
    void testInvalidCVVLength(String cvv) {
        CreditCardPayment payment = new CreditCardPayment("4532015112830366", cvv, "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("CVV must be 3 or 4 digits"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12a", "abc", "1@3", "12 3"})
    @DisplayName("CVV with non-digit characters should fail")
    void testCVVWithNonDigits(String cvv) {
        CreditCardPayment payment = new CreditCardPayment("4532015112830366", cvv, "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("CVV must be 3 or 4 digits"));
    }

    // ==================== Expiry Date Tests ====================

    @Test
    @DisplayName("Valid future expiry date should pass validation")
    void testValidFutureExpiryDate() {
        YearMonth futureDate = YearMonth.now().plusMonths(6);
        String expiryDate = String.format("%02d/%02d",
                futureDate.getMonthValue(),
                futureDate.getYear() % 100);

        CreditCardPayment payment = new CreditCardPayment("4532015112830366", "123", expiryDate);
        ValidationResult result = payment.validatePaymentDetails();
        assertTrue(result.isValid());
    }

    @Test
    @DisplayName("Current month expiry date should pass validation")
    void testCurrentMonthExpiryDate() {
        YearMonth currentMonth = YearMonth.now();
        String expiryDate = String.format("%02d/%02d",
                currentMonth.getMonthValue(),
                currentMonth.getYear() % 100);

        CreditCardPayment payment = new CreditCardPayment("4532015112830366", "123", expiryDate);
        ValidationResult result = payment.validatePaymentDetails();
        assertTrue(result.isValid());
    }

    @Test
    @DisplayName("Null expiry date should fail validation")
    void testNullExpiryDate() {
        CreditCardPayment payment = new CreditCardPayment("4532015112830366", "123", null);
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertEquals("Expiry date is required", result.getErrorMessage());
    }

    @Test
    @DisplayName("Empty expiry date should fail validation")
    void testEmptyExpiryDate() {
        CreditCardPayment payment = new CreditCardPayment("4532015112830366", "123", "");
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertEquals("Expiry date is required", result.getErrorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"13/25", "00/25", "1/25", "12/2025", "12-25", "12.25", "25/12"})
    @DisplayName("Expiry date with invalid format should fail")
    void testInvalidExpiryDateFormat(String expiryDate) {
        CreditCardPayment payment = new CreditCardPayment("4532015112830366", "123", expiryDate);
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("MM/YY format"));
    }

    @Test
    @DisplayName("Expired card should fail validation")
    void testExpiredCard() {
        YearMonth pastDate = YearMonth.now().minusMonths(1);
        String expiryDate = String.format("%02d/%02d",
                pastDate.getMonthValue(),
                pastDate.getYear() % 100);

        CreditCardPayment payment = new CreditCardPayment("4532015112830366", "123", expiryDate);
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertEquals("Card has expired", result.getErrorMessage());
    }

    @Test
    @DisplayName("Card expired years ago should fail validation")
    void testCardExpiredYearsAgo() {
        CreditCardPayment payment = new CreditCardPayment("4532015112830366", "123", "01/20");
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertEquals("Card has expired", result.getErrorMessage());
    }

    // ==================== Integration Tests ====================

    @Test
    @DisplayName("Complete valid payment should process successfully")
    void testCompleteValidPayment() {
        YearMonth futureDate = YearMonth.now().plusYears(2);
        String expiryDate = String.format("%02d/%02d",
                futureDate.getMonthValue(),
                futureDate.getYear() % 100);

        PaymentMethod payment = new CreditCardPayment("4532015112830366", "123", expiryDate);
        PaymentProcessor processor = new PaymentProcessor();

        PaymentResult result = processor.makePayment(payment, 100.0);

        assertTrue(result.isSuccess());
        assertNotNull(result.getTransactionId());
        assertTrue(result.getTransactionId().startsWith("CC-"));
    }

    @Test
    @DisplayName("Payment with multiple validation errors should fail on first error")
    void testMultipleValidationErrors() {
        CreditCardPayment payment = new CreditCardPayment(null, null, null);
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        // Should fail on first check (card number)
        assertEquals("Card number is required", result.getErrorMessage());
    }

    @Test
    @DisplayName("Payment with invalid card but valid CVV and expiry should fail")
    void testInvalidCardValidOthers() {
        YearMonth futureDate = YearMonth.now().plusMonths(6);
        String expiryDate = String.format("%02d/%02d",
                futureDate.getMonthValue(),
                futureDate.getYear() % 100);

        CreditCardPayment payment = new CreditCardPayment("1234567890123456", "123", expiryDate);
        ValidationResult result = payment.validatePaymentDetails();
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("failed Luhn check"));
    }

    @Test
    @DisplayName("Edge case: Card number with leading/trailing spaces")
    void testCardNumberWithLeadingTrailingSpaces() {
        CreditCardPayment payment = new CreditCardPayment("  4532015112830366  ", "123", "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertTrue(result.isValid());
    }

    @Test
    @DisplayName("Edge case: Maximum valid card length (19 digits)")
    void testMaximumCardLength() {
        // Valid 19-digit card (passes Luhn)
        CreditCardPayment payment = new CreditCardPayment("6304000000000000000", "123", "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertTrue(result.isValid());
    }

    @Test
    @DisplayName("Edge case: Minimum valid card length (13 digits)")
    void testMinimumCardLength() {
        // Valid 13-digit card (passes Luhn)
        CreditCardPayment payment = new CreditCardPayment("4532015112830", "123", "12/30");
        ValidationResult result = payment.validatePaymentDetails();
        assertTrue(result.isValid());
    }
}
