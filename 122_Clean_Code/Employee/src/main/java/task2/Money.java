package task2;

/**
 * Represents a monetary amount with currency
 */
public class Money {
    private final double amount;
    private final Currency currency;

    public enum Currency {
        USD, EUR, GBP, JPY
    }

    public Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", amount, currency);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Money money = (Money) obj;
        return Double.compare(money.amount, amount) == 0 && currency == money.currency;
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(amount);
        result = 31 * result + currency.hashCode();
        return result;
    }
}