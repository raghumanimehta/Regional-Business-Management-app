package model;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

/*
 * Represents a facility. A facility has a name, revenue, expenses, resources, and a list of EmployeeTypes.
 *
 * Each EmployeeType in the facility list represents a type of employee with specific attributes like title,
 * salary (in cents),and count. The employee type can be identified uniquely by its title within the facility.
 * There can't be more than one employee type in the list of employee type with the same type.
 * Revenue, all types of expenses, and resources are stored in cents.
 *
 * INVARIANT: Every title in EmployeeType within the facility is unique.
 *
 */
public class Facility {
    private String name;
    private int revenue;
    private int expensesOtherThanSalaries;
    private List<EmployeeType> employeeTypes;
    private int resources;

    // Requires: revenue, resources, expensesOtherThanSalaries >= 0
    // Effects : Constructs a facility with the given name, initial revenue, initial resources,
    //           and initial expenses other than salaries. Initializes an empty list of employee types.
    public Facility(String name, int revenue, int resources, int expensesOtherThanSalaries) {
        this.name = name;
        this.revenue = revenue;
        this.resources = resources;
        this.expensesOtherThanSalaries = expensesOtherThanSalaries;
        this.employeeTypes = new ArrayList<>();
    }

    // Requires: count > 0
    // Modifies: this
    // Effects: adds the employee type to the list of employee types and returns true. If employee type already in the
    //          list does nothing and returns false
    public boolean addEmployeeType(String title, int salary, int count) {
        for (EmployeeType e : this.employeeTypes) {
            if (e.getTitle().equals(title)) {
                return false;
            }
        }
        EmployeeType employeeType = new EmployeeType(title, salary, count);
        this.employeeTypes.add(employeeType);
        return true;
    }

    // Modifies: this
    // Effects: removes the employee type with the title from the list of employee types and returns true.
    // If employee type not already in th list does nothing and returns false
    public boolean removeEmployeeType(String title) {

        for (int i = 0; i < this.employeeTypes.size(); i++) {
            if (this.employeeTypes.get(i).getTitle().equals(title)) {
                this.employeeTypes.remove(i);
                return true;
            }
        }
        return false;
    }


    // Effects: Returns the total money (in cents) that is needed to be paid to all the employees of a particular
    //          employee type based on its title. If the employee type with the given title doesn't exist, returns -1.
    public int calculateSalaryToEmployeeType(String title) {
        for (EmployeeType employeeType : this.employeeTypes) {
            if (employeeType.getTitle().equals(title)) {
                return employeeType.getTotalMoneyToBePaid();
            }
        }
        return -1;
    }


    // Effects: Returns the total amount (in cents) of money that is needed to be paid to all the employees in all the
    //          employee types
    public int calculateTotalMoneyToBePaidToEmployees() {
        int totalMoneyToBePaid = 0;
        for (EmployeeType e : this.employeeTypes) {
            totalMoneyToBePaid += e.getTotalMoneyToBePaid();
        }
        return totalMoneyToBePaid;
    }

    // Requires: amount > 0
    // Modifies: this
    // Effects: increases the resources allotted to the facility by the given amount (in cents)
    public void increaseResources(int amount) {
        this.resources += amount;
    }

    // Requires: amount > 0
    // Modifies: this
    // Effects: decreases the resources allotted to the facility by the given amount (in cents)
    public void decreaseResources(int amount) {
        this.resources -= amount;
    }

    // Requires : amount > 0
    // Modifies: this
    // Effects: increases the expenses by the given amount (in cents)
    public void increaseExpensesOtherThanSalaries(int amount) {
        this.expensesOtherThanSalaries += amount;
    }

    // Requires: amount > 0
    // Modifies: this
    // Effects: decreases the expenses by the given amount (in cents)
    public void decreaseExpensesOtherThanSalaries(int amount) {
        this.expensesOtherThanSalaries -= amount;
    }


    // Effects: returns this as a JSON object. Inspired from the code given in JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject myObject = new JSONObject();
        myObject.put("name", this.name);
        myObject.put("revenue", this.revenue);
        myObject.put("expensesOtherThanSalaries", this.expensesOtherThanSalaries);
        myObject.put("employees", employeeTypesToJson());
        myObject.put("resources", this.resources);
        return myObject;
    }

    // Effects: returns this as an JSON array. Inspired from the code given in JsonSerializationDemo
    public JSONArray employeeTypesToJson() {
        JSONArray myArray = new JSONArray();

        for (EmployeeType employeeType : this.employeeTypes) {
            myArray.put(employeeType.toJson());
        }
        return myArray;
    }

    // Effects: gets the list of employee types in the facility
    public List<EmployeeType> getEmployeeTypes() {
        return employeeTypes;
    }

    //Effects: gets the resources invested in the facility
    public int getResources() {
        return resources;
    }

    // Effects: Returns the number of employee types in the facility
    public int countEmployeeTypes() {
        return this.employeeTypes.size();
    }

    // Effects: returns the name of the facility
    public String getName() {
        return name;
    }

    // Effects: returns the revenues of the facility
    public int getRevenue() {
        return revenue;
    }

    // Effects: returns the expenses other than salaries
    public int getExpensesOtherThanSalaries() {
        return expensesOtherThanSalaries;
    }

    // Effects: returns the total salary to be paid to all the employees, returns 0 if there are no employee types
    public int getSalaries() {
        int salary = 0;
        for (EmployeeType e: this.employeeTypes) {
            salary += e.getSalary() * e.getCount();
        }
        return salary;
    }

    // Effects: returns true if the facility is profitable
    public boolean isProfitable() {
        return (revenue > (expensesOtherThanSalaries + getSalaries()));
    }
}


