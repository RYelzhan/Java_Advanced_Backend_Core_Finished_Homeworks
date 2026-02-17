package task2;

public class SalariedEmployee extends Employee {
    private static final int MONTHS_PER_YEAR = 12;
    private static final double ANNUAL_BONUS_RATE = 0.10; // 10% of annual salary

    private final double annualSalary;

    public SalariedEmployee(String name, double annualSalary) {
        super(name);
        this.annualSalary = annualSalary;
    }

    @Override
    public Money calculatePay() {
        double monthlyPay = annualSalary / MONTHS_PER_YEAR;
        return new Money(monthlyPay, Money.Currency.USD);
    }

    @Override
    public Money calculateBonus() {
        double bonus = annualSalary * ANNUAL_BONUS_RATE;
        return new Money(bonus, Money.Currency.USD);
    }
}