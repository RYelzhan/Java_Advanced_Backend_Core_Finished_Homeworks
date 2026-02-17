package task2;

public class CommissionedEmployee extends Employee {
    public static final double COMMISSION_BONUS_RATE = 0.1;
    private final double baseSalary;
    private final double commissionRate;
    private final double salesAmount;

    public CommissionedEmployee(String name, double baseSalary, double commissionRate, double salesAmount) {
        super(name);
        this.baseSalary = baseSalary;
        this.commissionRate = commissionRate;
        this.salesAmount = salesAmount;
    }

    @Override
    public Money calculatePay() {
        double pay = baseSalary + (salesAmount * commissionRate);
        return new Money(pay, Money.Currency.USD);
    }

    @Override
    public Money calculateBonus() {
        double bonus = salesAmount * commissionRate * COMMISSION_BONUS_RATE; // 10% of commission as bonus
        return new Money(bonus, Money.Currency.USD);
    }
}