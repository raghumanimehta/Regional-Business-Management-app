package model;


import org.json.JSONObject;

/*
 * Represents an employee type. An employee type has a title, salary (in cents) to be paid to each employee, count of
 * the employees in that employee type.
 */
public class EmployeeType {
    private String title;
    private int salary;
    private int count;

    // Effects : Constructs an employeeType with title, salary and count of the employeeType
    public EmployeeType(String title, int salary, int count) {
        this.title = title;
        this.salary = salary;
        this.count = count;
    }

    // Effects : returns the total money to be paid to all the employees in the employeeType
    public int getTotalMoneyToBePaid() {
        return getSalary() * getCount();
    }


    // Effects: returns this as a JSON object. Inspired from the code given in JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject myObject = new JSONObject();
        myObject.put("title", this.title);
        myObject.put("salary", this.salary);
        myObject.put("count", this.count);
        return myObject;
    }

    // Effects: returns the title of the employee type
    public String getTitle() {
        return title;
    }

    // Effects: returns the salary of each employee in employee type
    public int getSalary() {
        return salary;
    }

    // Effects: returns the count of the employees in the employee Type
    public int getCount() {
        return count;
    }
}
