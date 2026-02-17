package task1.strategy;

/**
 * Calculates fines for overdue books.
 */
public interface FineCalculationStrategy {
    double calculateFine(long overdueDays);
}
