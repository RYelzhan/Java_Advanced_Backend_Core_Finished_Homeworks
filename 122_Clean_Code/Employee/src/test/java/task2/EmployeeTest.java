package task2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    @Test
    public void testCommissionedEmployeePay() {
        CommissionedEmployee emp = new CommissionedEmployee("Alice", 3000, 0.15, 50000);
        Money pay = emp.calculatePay();
        assertEquals(10500.0, pay.getAmount(), 0.01); // 3000 + (50000 * 0.15)
    }

    @Test
    public void testCommissionedEmployeeBonus() {
        CommissionedEmployee emp = new CommissionedEmployee("Alice", 3000, 0.15, 50000);
        Money bonus = emp.calculateBonus();
        assertEquals(750.0, bonus.getAmount(), 0.01); // 50000 * 0.15 * 0.1
    }

    @Test
    public void testHourlyEmployeePayNoOvertime() {
        HourlyEmployee emp = new HourlyEmployee("Bob", 25, 40);
        Money pay = emp.calculatePay();
        assertEquals(1000.0, pay.getAmount(), 0.01); // 25 * 40
    }

    @Test
    public void testHourlyEmployeePayWithOvertime() {
        HourlyEmployee emp = new HourlyEmployee("Bob", 25, 45);
        Money pay = emp.calculatePay();
        assertEquals(1187.5, pay.getAmount(), 0.01); // (40 * 25) + (5 * 25 * 1.5)
    }

    @Test
    public void testHourlyEmployeeBonus() {
        HourlyEmployee emp = new HourlyEmployee("Bob", 25, 45);
        Money bonus = emp.calculateBonus();
        assertEquals(100.0, bonus.getAmount(), 0.01);
    }

    @Test
    public void testSalariedEmployeePay() {
        SalariedEmployee emp = new SalariedEmployee("Charlie", 60000);
        Money pay = emp.calculatePay();
        assertEquals(5000.0, pay.getAmount(), 0.01); // 60000 / 12
    }

    @Test
    public void testSalariedEmployeeBonus() {
        SalariedEmployee emp = new SalariedEmployee("Charlie", 60000);
        Money bonus = emp.calculateBonus();
        assertEquals(6000.0, bonus.getAmount(), 0.01); // 60000 * 0.10
    }

    @Test
    public void testMoneyEquality() {
        Money m1 = new Money(100.0, Money.Currency.USD);
        Money m2 = new Money(100.0, Money.Currency.USD);
        Money m3 = new Money(100.0, Money.Currency.EUR);

        assertEquals(m1, m2);
        assertNotEquals(m1, m3);
    }
}