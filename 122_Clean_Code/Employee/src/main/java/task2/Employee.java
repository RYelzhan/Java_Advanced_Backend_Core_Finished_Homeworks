package task2;

public abstract class Employee {
    protected String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract Money calculatePay();

    public abstract Money calculateBonus();
}

