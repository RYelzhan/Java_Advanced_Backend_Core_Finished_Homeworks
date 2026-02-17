package task1.strategy;

public class DailyFineStrategy implements FineCalculationStrategy {

    private double dailyFine;

    public DailyFineStrategy(double dailyFine) {
        this.dailyFine = dailyFine;
    }

    @Override
    public double calculateFine(long overdueDays) {
        return overdueDays * dailyFine;
    }
}
