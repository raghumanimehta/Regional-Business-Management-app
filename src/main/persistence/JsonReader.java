package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// Represents a reader that reads the management data from JSON from the file
// NOTE : The following code has parts used from the JsonSerializationDemo provided in the course.
//        The structure follows the above-mentioned source but has been adapted to suit my project.
public class JsonReader {

    private String source;

    // Effects: Constructs reader to read from the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // Effects : Reads the Region data from the file and returns it;
    // throws IOException if an error occurs in reading data
    public Region loadRegion() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRegion(jsonObject);
    }

    // Effects: reads source file as a string and returns it throws IOException if not found
    // Note: Inspired from https://www.youtube.com/watch?v=ScUJx4aWRi0
    private String readFile(String source) throws IOException {
        StringBuilder newRead = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(source))) {
            newRead = new StringBuilder();
            String newLine;
            while ((newLine = bufferedReader.readLine()) != null) {
                newRead.append(newLine);
            }
        } catch (IOException e) {
            System.out.println("File not found!");
        }

        return newRead.toString();
    }


    // Effects: parses Region from JSON object and returns it
    private Region parseRegion(JSONObject jsonObject) {
        String regionName = jsonObject.getString("name");
        Region loadedRegion = new Region(regionName);
        addFacilities(loadedRegion, jsonObject);
        return loadedRegion;
    }

    // Effects: parses Facility from JSON object and returns it
    private Facility parseFacility(JSONObject jsonObject) {
        String facilityName = jsonObject.getString("name");
        int revenue = jsonObject.getInt("revenue");
        int expensesOtherThanSalaries = jsonObject.getInt("expensesOtherThanSalaries");
        int resources = jsonObject.getInt("resources");

        Facility loadedFacility = new Facility(facilityName, revenue, resources, expensesOtherThanSalaries);
        return loadedFacility;
    }


    // Effects: Parses employeeType from Json and returns it
    private EmployeeType parseEmployeeType(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        int salary = jsonObject.getInt("salary");
        int count = jsonObject.getInt("count");

        EmployeeType loadedEmployeeType = new EmployeeType(title, salary, count);
        return loadedEmployeeType;
    }


    // Modifies: region
    // Effects: parses facilities from the json object and adds them to the region
    private void addFacilities(Region region, JSONObject jsonObject) {
        JSONArray facilityArray;
        facilityArray =  jsonObject.getJSONArray("facilities");
        for (Object facilityObject : facilityArray) {
            JSONObject nextObject = (JSONObject) facilityObject;
            addFacility(region, nextObject);
        }
    }

    //Modifies :region
    // Effects: parses facility from the json object and adds it to the region
    private void addFacility(Region region, JSONObject jsonObject) {
        Facility thisFacility = parseFacility(jsonObject);
        region.getFacilities().add(thisFacility);
        addEmployeeTypes(thisFacility, jsonObject);
    }

    // Modifies: facility
    // Effects: parses employeeTypes from the json object and adds them to the region
    private void addEmployeeTypes(Facility facility, JSONObject jsonObject) {
        JSONArray employeeArray;
        employeeArray = jsonObject.getJSONArray("employees");
        for (Object employeeObject : employeeArray) {
            JSONObject nextObject = (JSONObject) employeeObject;
            addEmployeeType(facility, nextObject);
        }
    }

    // Modifies: facility
    // Effects: parses employeeType from the json object and it them to the region
    private void addEmployeeType(Facility facility, JSONObject jsonObject) {
        EmployeeType thisEmployeeType = parseEmployeeType(jsonObject);
        facility.getEmployeeTypes().add(thisEmployeeType);
    }

}
