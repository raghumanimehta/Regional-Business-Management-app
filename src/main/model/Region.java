package model;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;


/*
 * Represents a region. A region has a name, a list of facilities in the region and expenses allotted by the business
 * for the region. There cannot be duplicates in the list of facilities, that is, each facility can only be present in
 * the list once. The facilities are stored in the order of which they were added. Expenses, Revenues, and Resources
 * are all stored as cents.
 */
public class Region {

    private String name;
    private List<Facility> facilities;

    // Effects: Constructs a new region with the given name, an empty list of Facilities
    public Region(String name) {
        this.name = name;
        this.facilities = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Constructed a region with name: " + name));
    }

    // Modifies : this
    // Effects: Adds a facility with the given parameters to the region and returns true.
    // If Facility with the same name already in the region does nothing and return false.
    public boolean addFacility(String name, int revenue, int resources, int expensesOtherThanSalaries) {
        for (Facility f : this.facilities) {
            if (f.getName().equals(name)) {
                EventLog.getInstance().logEvent(new Event("Attempted to add facility with name "
                        + name + " to the region"));
                return false;
            }
        }
        Facility facility = new Facility(name, revenue, resources, expensesOtherThanSalaries);
        this.facilities.add(facility);
        EventLog.getInstance().logEvent(new Event("Added facility with name "
                + name + " to the region"));
        return true;
    }


    // Modifies : this
    // Effects: Removes the facility from the region and returns true.
    // If Facility not in the region does nothing and return false.
    public boolean removeFacility(String title) {

        for (int i = 0; i < this.facilities.size(); i++) {
            if (this.facilities.get(i).getName().equals(title)) {
                this.facilities.remove(i);
                EventLog.getInstance().logEvent(new Event("Removed facility with name "
                        + title + " from the region"));
                return true;
            }
        }
        EventLog.getInstance().logEvent(new Event("Attempted to remove facility with name "
                + title + " from the region"));
        return false;
    }

    // Effects : returns the number of facilities in the region.
    public int countFacilities() {
        return this.facilities.size();
    }

    // Requires: amount > 0 (in cents).
    // Modifies: this, Facility
    // Effects: If the start facility has resources (in cents) greater than or equal to the given
    // amount(in cents), deducts that amount (in cents) from the start facility and adds it to the destination facility
    // and returns true. If the start facility ends up with < 0 resources, returns false. If one or both facilities not
    // found then returns false and does nothing.
    public boolean transferResources(String start, String destination, int amount) {
        Facility startFacility = findFacility(start);
        Facility destinationFacility = findFacility(destination);

        if (startFacility == null || destinationFacility == null) {
            EventLog.getInstance().logEvent(new Event("Attempted to transfer resources transferred from "
                    + start + " to " + destination));
            return false;
        } else {
            if (startFacility.getResources() - amount >= 0) {
                startFacility.decreaseResources(amount);
                destinationFacility.increaseResources(amount);
                EventLog.getInstance().logEvent(new Event("Resources transferred from "
                        + start + " to " + destination + " of amount " + amount));
                return true;
            } else {
                EventLog.getInstance().logEvent(new Event("Attempted to transfer resources transferred from "
                        + start + " to " + destination));
                return false;
            }
        }

    }


    // Effects: checks if the facility is in the list
    public Facility findFacility(String name) {
        for (Facility f : this.facilities) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return null;
    }


    // Effects: Returns the total expense (in cents) incurred in the region.
    public int calculateTotalExpenses() {
        int totalExpenses = 0;
        for (Facility facility : this.facilities) {
            totalExpenses += facility.getExpensesOtherThanSalaries()
                    + facility.calculateTotalMoneyToBePaidToEmployees();
        }
        EventLog.getInstance().logEvent(new Event("Region's total expenses calculated: " + totalExpenses));
        return totalExpenses;
    }

    // Effects: Returns the total resources (in cents) allotted in the region
    public int calculateTotalResourcesRegion() {
        int totalResources = 0;
        for (Facility facility : this.facilities) {
            totalResources += facility.getResources();
        }
        EventLog.getInstance().logEvent(new Event("Region's total resources calculated: " + totalResources));
        return totalResources;
    }

    // Effects: Returns the total revenue (in cents) earned in the region
    public int calculateTotalRevenueRegion() {
        int totalRevenue = 0;
        for (Facility facility : this.facilities) {
            totalRevenue += facility.getRevenue();
        }
        EventLog.getInstance().logEvent(new Event("Region's total revenue calculated: " + totalRevenue));
        return totalRevenue;
    }

    // Effects : Returns true if the region is profitable (revenue > total expenses)
    public boolean isProfitable() {
        boolean bool = (calculateTotalRevenueRegion() > calculateTotalExpenses());
        EventLog.getInstance().logEvent(new Event("Checked if region is profitable " + bool));
        return bool;
    }


    // Effects: returns this as a JSON object. Inspired from the code given in JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject myObject = new JSONObject();
        myObject.put("name", this.name);
        myObject.put("facilities", facilitiesToJson());
        EventLog.getInstance().logEvent(new Event("Saving region to JSON"));
        return myObject;
    }

    //Effects: returns facilities in the region as a JSON array. Inspired from the code given in JsonSerializationDemo
    public JSONArray facilitiesToJson() {
        JSONArray myArray = new JSONArray();

        for (Facility nextFacility : this.facilities) {
            myArray.put(nextFacility.toJson());
        }
        return myArray;
    }

    // Effects: gets the name of the region
    public String getName() {
        return name;
    }

    // Effects: Returns the list of facilities in the region.
    public List<Facility> getFacilities() {
        return facilities;
    }
}
