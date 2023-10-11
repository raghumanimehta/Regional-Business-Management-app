package model;

/*
 * Represents an employee type. An employee type has a title, salary (in cents) to be paid to each employee, count of
 * the employees in that employee type.
 */

public class EmployeeType {
    private String title;
    private int salary;
    private int count;

    //
    public EmployeeType(String title, int salary, int count) {

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
