package task2;

/**
 * Client code demonstrating the usage of the employee hierarchy
 */
public class PayrollSystem {
    public static void main(String[] args) {
        Employee[] employees = {
                new CommissionedEmployee("Alice", 3000, 0.15, 50000),
                new HourlyEmployee("Bob", 25, 45),
                new SalariedEmployee("Charlie", 60000)
        };

        System.out.println("=== Payroll Report ===\n");

        for (Employee employee : employees) {
            System.out.println("Employee: " + employee.getName());
            System.out.println("Pay: " + employee.calculatePay());
            System.out.println("Bonus: " + employee.calculateBonus());
            System.out.println();
        }
    }
}