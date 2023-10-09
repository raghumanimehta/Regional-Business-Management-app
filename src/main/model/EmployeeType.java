package model;

public class EmployeeType {
    private String title;
    private int salary;
    private int count;

    public EmployeeType(String title, int salary, int count) {
       // this.id = IdGenerator.generateId();
        this.title = title;
        this.salary = salary;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public int getSalary() {
        return salary;
    }

    public int getCount() {
        return count;
    }

    public int getTotalMoneyToBePaid() {
        return getSalary() * getCount();
    }
}
