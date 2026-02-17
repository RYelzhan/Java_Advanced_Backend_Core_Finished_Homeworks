package task3;

import task3.domain.PaymentResult;

public class PaymentProcessor {
    public PaymentResult makePayment(PaymentMethod payment, double amount) {
        // Now uses the template method which ensures consistent behavior
        return payment.executePayment(amount);
    }
}
