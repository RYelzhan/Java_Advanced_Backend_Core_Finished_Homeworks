package task3;

import org.junit.jupiter.api.Test;
import task3.domain.PaymentResult;
import task3.impl.CreditCardPayment;
import task3.impl.PayPalPayment;

import static org.junit.jupiter.api.Assertions.*;

class PaymentProcessorTest {

    @Test
    void testCreditCardPayment_InvalidCard() {
        PaymentMethod payment = new CreditCardPayment("123", "123", "12/25");
        PaymentProcessor processor = new PaymentProcessor();

        PaymentResult result = processor.makePayment(payment, 100.0);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Card number must be between"));
    }

    @Test
    void testPayPalPayment_Success() {
        PaymentMethod payment = new PayPalPayment("user@example.com", true);
        PaymentProcessor processor = new PaymentProcessor();

        PaymentResult result = processor.makePayment(payment, 100.0);

        assertTrue(result.isSuccess());
        assertNotNull(result.getTransactionId());
    }

    @Test
    void testPayPalPayment_NotLinked_NoException() {
        // KEY TEST: This should NOT throw an exception (LSP compliance)
        PaymentMethod payment = new PayPalPayment("user@example.com", false);
        PaymentProcessor processor = new PaymentProcessor();

        PaymentResult result = processor.makePayment(payment, 100.0);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("not linked"));
    }

    @Test
    void testLiskovSubstitution() {
        // All payment methods should be interchangeable
        PaymentMethod[] payments = {
                new CreditCardPayment("1234567890123456", "123", "12/25"),
                new PayPalPayment("user@example.com", true)
        };

        PaymentProcessor processor = new PaymentProcessor();

        for (PaymentMethod payment : payments) {
            // Should work for all subtypes without special handling
            PaymentResult result = processor.makePayment(payment, 50.0);
            assertNotNull(result); // No exceptions thrown
        }
    }
}
