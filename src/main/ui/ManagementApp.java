package ui;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

// Management App
public class ManagementApp {
    private Scanner scanner;
    private Region region;
    private static final String FILE_STORE = "./data/managementApp.json";
    private JsonWriter writer;
    private JsonReader reader;

    public ManagementApp(String name) throws FileNotFoundException {
        this.region = new Region(name);
        this.writer = new JsonWriter(FILE_STORE);
        this.reader = new JsonReader(FILE_STORE);
        runApp();
    }

    // Effects: Run the app
    public void runApp() {
        boolean run = true;
        String input = null;
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");

        while (run) {
            displayMenu();
            input = scanner.next().toLowerCase();
            scanner.nextLine();
            if (input.equals("q")) {
                run = false;
            } else {
                processInput(input);
            }
        }
        System.out.println("Quitting application");
    }

    // Modifies: this
    // Effects: process various inputs
    @SuppressWarnings("methodlength")
    public void processInput(String input) {
        switch (input) {
            case "la":
                listFacilities();
                break;
            case "a":
                addFacility();
                break;
            case "r":
                removeFacility();
                break;
            case "cr":
                calculateTotalRevenue();
                break;
            case "cx":
                calculateTotalExpenses();
                break;
            case "ctr":
                calculateTotalResources();
                break;
            case "e":
                addEmployeeTypeToAFacility();
                break;
            case "re":
                removeEmployeeType();
                break;
            case "t":
                transferResources();
                break;
            case "sr":
                showResource();
                break;
            case "sx":
                showExpense();
                break;
            case "srev":
                showRevenue();
                break;
            case "p":
                isProfitable();
                break;
            case "s" :
                saveApp();
                break;
            case "l" :
                loadApp();
                break;
            default:
                System.out.println("Invalid input, try again!");
        }
    }

    // Effects: shows if the region is profitable or not
    private void isProfitable() {
        if (region.isProfitable()) {
            System.out.println("The region is profitable!");
        } else {
            System.out.println("The region is not profitable!");
        }
    }

    // Effects: shows the resource of one facility
    private void showResource() {
        if (region.getFacilities().isEmpty()) {
            System.out.println("Empty Region!");
        } else {
            System.out.println("Enter the name of the facility to show resources for: ");
            String facilityName = scanner.nextLine();
            Facility facility = region.findFacility(facilityName);
            if (facility == null) {
                System.out.println("Facility not found");
            } else {
                System.out.println(facility.getResources());
            }

        }
    }

    // Effects: shows the expenses of one facility
    private void showExpense() {
        if (region.getFacilities().isEmpty()) {
            System.out.println("Empty Region!");
        } else {
            System.out.println("Enter the name of the facility to show expenses for: ");
            String facilityName = scanner.nextLine();
            Facility facility = region.findFacility(facilityName);
            if (facility == null) {
                System.out.println("Facility not found!");
            } else {
                System.out.println(facility.getExpensesOtherThanSalaries()
                        + facility.calculateTotalMoneyToBePaidToEmployees());
            }
        }
    }

    // Effects: shows the revenue of facility
    private void showRevenue() {
        if (region.getFacilities().isEmpty()) {
            System.out.println("Empty Region!");
        } else {
            System.out.println("Enter the name of the facility to show expenses for: ");
            String facilityName = scanner.nextLine();
            Facility facility = region.findFacility(facilityName);
            if (facility == null) {
                System.out.println("Facility not found!");
            } else {
                System.out.println(facility.getRevenue());
            }
        }
    }

    // Effects: calculates the total resources invested in the region
    private void calculateTotalResources() {
        if (region.getFacilities().isEmpty()) {
            System.out.println("Empty Region!");
        } else {
            System.out.println(this.region.calculateTotalResourcesRegion());
        }
    }

    // Effects: shows all the facilities in the region
    private void listFacilities() {
        System.out.println("The facilities in " + "Vancouver are: ");
        for (Facility f : region.getFacilities()) {
            System.out.println(f.getName());
        }
    }

    // Requires : the input for revenue (in cents) and resources (in cents) should be of the type Integer
    // Modifies: this
    // Effects: adds facility with the given name, revenue, resources, expenses other than salaries to the region
    private void addFacility() {
        System.out.println("Enter the name of the facility to add: ");
        String name = scanner.nextLine();
        System.out.println("Enter revenue (in cents, use Integer) for the facility: ");
        int revenue = scanner.nextInt();
        System.out.println("Enter resources (in cents, use Integer) allotted for the facility:");
        int resources = scanner.nextInt();
        System.out.println("Enter the expenses (in cents, use Integer) other than salaries for the facility: ");
        int expensesOtherThanSalaries = scanner.nextInt();
        if (region.addFacility(name, revenue, resources, expensesOtherThanSalaries)) {
            System.out.println("Facility added successfully!");
        } else {
            System.out.println("Facility with name already exists in the region! Facility not added.");
        }


    }

    // Modifies: this
    // Effects: removes facility from the region
    private void removeFacility() {
        if (region.getFacilities().isEmpty()) {
            System.out.println("Empty Region!");
        } else {
            System.out.println("Enter the name of the facility to remove: ");
            String name = scanner.nextLine();
            if (region.removeFacility(name)) {
                System.out.println("Facility removed successfully!");
            } else {
                System.out.println("Facility with name already doesn't exist!");
            }
        }

    }

    // Effects: calculates the total revenue in region
    private void calculateTotalRevenue() {
        if (region.getFacilities().isEmpty()) {
            System.out.println("Empty Region!");
        } else {
            System.out.println(region.calculateTotalRevenueRegion());
        }
    }

    // Effects: calculates the total expenses (salaries + other expenses) in the region
    private void calculateTotalExpenses() {
        if (region.getFacilities().isEmpty()) {
            System.out.println("Empty Region!");
        } else {
            System.out.println(region.calculateTotalExpenses());
        }
    }

    // Requires: salary (in cents) and count should be the integer type
    // Modifies: this
    // Effects: adds an employee type with title, salary (in cents) and count.
    private void addEmployeeTypeToAFacility() {
        System.out.println("Enter the name of the facility to add employee type to: ");
        String facilityName = scanner.nextLine();
        System.out.println("Enter the title for the employee type: ");
        String title = scanner.nextLine();
        System.out.println("Enter the salary (in cents, use Integer) to be paid to each employee in the employee type");
        int salary = scanner.nextInt();
        System.out.println("Enter the count (use Integer) of employees of this employee type:");
        int count = scanner.nextInt();

        Facility facility = this.region.findFacility(facilityName);
        if (facility == null) {
            System.out.println("Facility not found. Can't add employee type!");
        } else if (facility.addEmployeeType(title, salary, count)) {
            System.out.println("Employee type added successfully!");
        } else {
            System.out.println("Employee type can't be added!");
        }
    }

    // Modifies: this
    // Effects: removes the employee type with the title from the chosen facility,
    //          does nothing if employee type doesn't exist in the facility
    private void removeEmployeeType() {
        System.out.println("Enter the name of the facility to remove employee type from: ");
        String facilityName = scanner.nextLine();
        System.out.println("Enter the title for the employee type: ");
        String title = scanner.nextLine();
        Facility facility = region.findFacility(facilityName);
        if (facility == null) {
            System.out.println("Facility not found, unsuccessful!");
        } else if (facility.removeEmployeeType(title)) {
            System.out.println("Employee type removed successfully!");
        } else {
            System.out.println("Employee type not found, can't remove!");
        }
    }

    // Requires: amount (in cents) input is of type Integer
    // Modifies: this
    // Effects: transfers resources (in cents) from one facility in the region to another facility in the region
    private void transferResources() {
        if (region.getFacilities().isEmpty()) {
            System.out.println("Empty Region!");
        } else {
            System.out.println("Enter the name of the facility to transfer resources from: ");
            String from = scanner.nextLine();
            System.out.println("Enter the name of the facility to transfer resources to:");
            String to = scanner.nextLine();
            System.out.println("Enter the amount (in cents, use Integer) to transfer:");
            int amount = scanner.nextInt();
            if (region.transferResources(from, to, amount)) {
                System.out.println("Transfer of resources (in cents) from: " + from
                        + " to " + to + " of amount " + amount + " successful");
            } else {
                System.out.println("Transfer not possible!");
            }
        }
    }

    // Effects: displays the menu for the application
    public void displayMenu() {
        System.out.println("Regional Facility Management App");
        System.out.println("Select from:");
        System.out.println("\tla   -> List all Facilities");
        System.out.println("\ta    -> Add Facility to the Region");
        System.out.println("\tr    -> Remove Facility from the Region");
        System.out.println("\tcr   -> Calculate Total Revenue earned from the Region");
        System.out.println("\tcx   -> Calculate Total Expenses in the Region");
        System.out.println("\tctr  -> Calculate Total Resources invested in the region");
        System.out.println("\te    -> Add Employee Type to a facility");
        System.out.println("\tt    -> Transfer Resources between Facilities");
        System.out.println("\tsr   -> Show resources for a facility");
        System.out.println("\tsx   -> Show expenses for a facility");
        System.out.println("\tsrev -> Show revenue for a facility");
        System.out.println("\tp    -> Check if the region is profitable");
        System.out.println("\ts    -> Save progress for the application");
        System.out.println("\tl    -> Load data");
        System.out.println("\tq    -> Quit Application");
    }

    // Modifies: this
    // Effects: saves the data of the app to file
    private void saveApp() {
        try {
            writer.openWriter();
            writer.write(region);
            writer.closeWriter();
            System.out.println("Region " + region.getName() + "saved successfully to " + FILE_STORE);
        } catch (IOException e) {
            System.out.println("Unable to store file to " + FILE_STORE + "!");
        }
    }

    private void loadApp() {
        try {
            region = reader.loadRegion();
            System.out.println("Region loaded successfully!");
        } catch (IOException e) {
            System.out.println("Unable to load file!");
        }
    }
}
