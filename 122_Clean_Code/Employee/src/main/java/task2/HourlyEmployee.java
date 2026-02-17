package task2;

public class HourlyEmployee extends Employee {
    private static final double STANDARD_WORK_HOURS = 40.0;
    private static final double OVERTIME_MULTIPLIER = 1.5;
    private static final double OVERTIME_BONUS_AMOUNT = 100.0;
    private static final double STANDARD_BONUS_AMOUNT = 50.0;

    private final double hourlyRate;
    private final double hoursWorked;

    public HourlyEmployee(String name, double hourlyRate, double hoursWorked) {
        super(name);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public Money calculatePay() {
        double pay = hourlyRate * hoursWorked;

        if (hoursWorked > STANDARD_WORK_HOURS) {
            double overtimeHours = hoursWorked - STANDARD_WORK_HOURS;
            pay = (STANDARD_WORK_HOURS * hourlyRate) + (overtimeHours * hourlyRate * OVERTIME_MULTIPLIER);
        }

        return new Money(pay, Money.Currency.USD);
    }

    @Override
    public Money calculateBonus() {
        double bonus = hoursWorked > STANDARD_WORK_HOURS ? OVERTIME_BONUS_AMOUNT : STANDARD_BONUS_AMOUNT;
        return new Money(bonus, Money.Currency.USD);
    }
}