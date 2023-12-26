package ui;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static java.awt.Color.black;

/*

Management App GUI class

 */
public class ManagementAppUI extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    private Region region;
    private static final String FILE_STORE = "./data/managementApp.json";
    private JsonWriter writer;
    private JsonReader reader;
    private JFrame frame;
    private DefaultListModel<String> facilitiesListModel;
    private JList<String> facilitiesList;
    private JToggleButton showOnlyProfitable;
    private JTextArea textArea;
    private JTextArea detailsText;
    private JPanel rightPanel;
    private JScrollPane detailsPane;
    private Facility selectedFacility;


    // Effects: Constructs the UI for Management App
    public ManagementAppUI(String name) throws FileNotFoundException {
        this.region = new Region(name);
        this.writer = new JsonWriter(FILE_STORE);
        this.reader = new JsonReader(FILE_STORE);
        this.frame = new JFrame();
        this.facilitiesListModel = new DefaultListModel<>();
        this.facilitiesList = new JList<>(facilitiesListModel);
        addListActionListener();
        startScreen();
        runUI();
    }

    // Modifies: this
    // Effects: adds actionListener to the facilities in the listPanel on the left
    private void addListActionListener() {
        facilitiesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedFacility = region.findFacility(facilitiesList.getSelectedValue());
                if (selectedFacility != null) {
                    displayDetails(selectedFacility);
                }
            }
        });
    }


    // Modifies: this
    //Effects: displays the details of the facilities in the right panel
    private void displayDetails(Facility selectedFacility) {
        rightPanel.removeAll();
        detailsText = new JTextArea(10, 30);
        detailsText.setEditable(false);
        detailsText.setText(
                "Name: " + selectedFacility.getName() + "\n"
                        + "Revenue: " + selectedFacility.getRevenue() + "\n"
                        + "Resources: " + selectedFacility.getResources() + "\n"
                        + "Expenses: " + (selectedFacility.getSalaries()
                        + selectedFacility.getExpensesOtherThanSalaries())
                        + "\n" + ((selectedFacility.isProfitable())
                        ? "Facility is profitable" : "Facility is not profitable")
                        + "\n" + "Employee Types: \n"
        );
        detailsPane = new JScrollPane(detailsText);
        displayEmployeeTypes(selectedFacility, detailsText);
        rightPanel();
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    // Effects: displays the details of the employee types of the facility in the right panel
    private void displayEmployeeTypes(Facility selectedFacility, JTextArea detailsText) {
        if (selectedFacility.getEmployeeTypes().isEmpty()) {
            detailsText.append("Facility has no employee types");
        } else {
            for (EmployeeType e: selectedFacility.getEmployeeTypes()) {
                detailsText.append("Title: " + e.getTitle() + ", "
                        + "salary: " + e.getSalary() + " cents, count: " + e.getCount() + "\n");
            }
        }
    }

    // Modifies: this
    // Effects : makes the frame for the UI and sets up the basics for the interface
    private void runUI() {
        frame.setLayout(new BorderLayout());
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setTitle("Regional Management App");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setResizable(false);
        makeListPanel();
        rightPanel();
        frame.setVisible(true);
    }

    // Modifies: this
    // Effects: instantiates and sets up the start up image.
    // credit for the image: in the README.md file
    private void startScreen() {
        JWindow startWindow = new JWindow();
        ImageIcon starScreenImage = new ImageIcon("budget.png");
        JLabel label = new JLabel(starScreenImage);
        startWindow.getContentPane().add(label);
        startWindow.pack();
        startWindow.setLocationRelativeTo(null);
        startWindow.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startWindow.dispose();
    }

    // Modifies: this
    // Effects: instantiates and sets up the right panel
    private void rightPanel() {
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(new Color(201, 201, 201));
        rightPanel.setPreferredSize(new Dimension(400, HEIGHT));
        initializeTextAreas();
        JPanel saveLoadExitPanel = saveLoadExitPanel();
        makeTextArea();
        rightPanel.add(textArea, BorderLayout.CENTER);
        if (detailsPane != null) {
            rightPanel.add(detailsPane);
        }
        rightPanel.add(saveLoadExitPanel, BorderLayout.SOUTH);
        frame.add(rightPanel, BorderLayout.EAST);
    }

    // Modifies: this
    // Effects : initialises the text areas of the right panel
    private void initializeTextAreas() {
        if (textArea == null) {
            textArea = new JTextArea(5, 30);
            textArea.setEditable(false);
        }

        if (detailsText == null) {
            detailsText = new JTextArea();
            detailsText.setEditable(false);
        }

        if (detailsPane == null) {
            detailsPane = new JScrollPane(detailsText);
            rightPanel.add(detailsPane);
        }
    }


    // Modifies: this
    // Effects: creates the panel that contains the save, load, and exit buttons
    private JPanel saveLoadExitPanel() {
        JPanel saveLoadExitPanel = new JPanel();
        saveLoadExitPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        saveLoadExitPanel.setBackground(new Color(201, 201, 201));
        saveLoadExitPanel.setPreferredSize(new Dimension(400, 45));
        JButton saveButton = saveRegion();
        JButton loadButton = loadRegion();
        JButton exitButton = exitRegion();
        saveLoadExitPanel.add(exitButton, FlowLayout.LEFT);
        saveLoadExitPanel.add(loadButton, FlowLayout.LEFT);
        saveLoadExitPanel.add(saveButton, FlowLayout.LEFT);
        return saveLoadExitPanel;
    }

    // Modifies: this
    // Effects: makes the exit button
    private JButton exitRegion() {
        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setPreferredSize(new Dimension(125, 30));
        exitButton.addActionListener(e -> exit());
        return exitButton;
    }

    // Modifies: this
    // Effects : makes the load button
    private JButton loadRegion() {
        JButton loadButton = new JButton("Load");
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setPreferredSize(new Dimension(125, 30));
        loadButton.addActionListener(e -> load());
        return loadButton;
    }

    // Modifies: this
    // Effects : makes the save button
    private JButton saveRegion() {
        JButton saveButton = new JButton("Save");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setPreferredSize(new Dimension(125, 30));
        saveButton.addActionListener(e -> save());
        return saveButton;
    }


    // Modifies : this
    // Effects: save the data of the app to the file
    private void save() {
        try {
            writer.openWriter();
            writer.write(region);
            writer.closeWriter();
            JOptionPane.showMessageDialog(frame, "Region saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Unable to store file to " + FILE_STORE + "!");
        }
    }

    // Modifies: this
    // Effects: loads the data of the app from the file
    private void load() {
        try {
            region = reader.loadRegion();
            clearDetails();
            repaintList();
            updateTextArea();
            detailsText.repaint();
            JOptionPane.showMessageDialog(frame, "Region loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Unable to load file!");
        }

    }


    // Modifies: this
    // Effects : updates the UI to display the loaded facilities
    private void repaintList() {
        facilitiesListModel.clear();
        for (Facility f : region.getFacilities()) {
            facilitiesListModel.addElement(f.getName());
        }
    }

    // Effects: exits the application and stop processes
    private void exit() {
        printLog();
        System.exit(0);
    }

    private void printLog() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.getDescription());
        }
    }

    // Modifies: this
    // Effects: makes the list panel for displaying all the facilities in the region
    private void makeListPanel() {
        JPanel listPanel = setUpPanel();
        JLabel headingLabel = listPanelHeadingLabel();
        JScrollPane jscrollPane = listFacilities();
        JButton addFacilityButton = addFacilityButton();
        JButton removeFacilityButton = removeFacilityButton();
        JButton addEmployeeTypeButton = addEmployeeTypeButton();
        showOnlyProfitable = showOnlyProfitableButton();
        listPanel.add(showOnlyProfitable);
        listPanel.add(headingLabel);
        listPanel.add(jscrollPane);
        listPanel.add(addFacilityButton);
        listPanel.add(removeFacilityButton);
        listPanel.add(addEmployeeTypeButton);
        listPanel.add(transferResourcesButton());
        listPanel.setBorder(null);
        jscrollPane.setBorder(null);
        frame.add(listPanel, BorderLayout.WEST);
        frame.validate();
        frame.repaint();
    }

    // Modifies: this
    // Effects : instantiates and sets the list panel
    private JPanel setUpPanel() {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(black);
        listPanel.setPreferredSize(new Dimension(WIDTH - 400, HEIGHT));
        return listPanel;
    }

    //Modifies : this
    // Effects: makes the heading label for the list panel
    private JLabel listPanelHeadingLabel() {
        JLabel headingLabel = new JLabel("Facilities in the Region");
        headingLabel.setForeground(Color.white);
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headingLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        return headingLabel;
    }

    //Modifies : this
    // Effects: makes the button, which when clicked shows only profitable facilities and when clicked again,
    //          again shows all the facilities in the region.
    private JToggleButton showOnlyProfitableButton() {
        showOnlyProfitable = new JToggleButton("Show Profitable facilities");
        showOnlyProfitable.setAlignmentX(Component.CENTER_ALIGNMENT);
        showOnlyProfitable.addActionListener(e -> filterFacilities());
        return showOnlyProfitable;
    }


    // Modifies: this
    // Effects: filters the facilities such that all the facilities that are positive remain
    private void filterFacilities() {
        facilitiesListModel.clear();
        if (showOnlyProfitable.isSelected()) {
            for (Facility f: region.getFacilities()) {
                if (f.isProfitable()) {
                    facilitiesListModel.addElement(f.getName());
                }
            }
            showOnlyProfitable.setText("Show All Facilities");
        } else {
            for (Facility f : region.getFacilities()) {
                facilitiesListModel.addElement(f.getName());
            }
            showOnlyProfitable.setText("Show Profitable Facilities");
        }
    }

    // Modifies: this
    // Effects: makes the JScroll Pane that displays the facility names in the list
    private JScrollPane listFacilities() {
        for (Facility f : region.getFacilities()) {
            facilitiesListModel.addElement(f.getName());
        }
        facilitiesList.setBackground(Color.black);
        facilitiesList.setForeground(Color.white);
        JScrollPane jscrollPane = new JScrollPane(facilitiesList);
        jscrollPane.setPreferredSize(new Dimension(200, HEIGHT - 100));
        jscrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        return jscrollPane;
    }

    // Modifies: this
    // Effects: prompts for adding Facility to the region and displaying it on the panel on the left and adds the
    //          facility to the region
    private void addFacility() {
        String name = inputString("Enter the name of the facility to add: ");
        if (name == null || name.trim().isEmpty()) {
            return;
        }
        int revenue = inputInteger("Enter revenue (in cents) for the facility: ");
        if (revenue == -1) {
            return;
        }
        int resources = inputInteger("Enter resources (in cents) for the facility: ");
        if (resources == -1) {
            return;
        }
        int expenses = inputInteger("Enter expenses other than salaries (in cents) for the facility: ");
        if (expenses == -1) {
            return;
        }
        if (region.addFacility(name, revenue, resources, expenses)) {
            JOptionPane.showMessageDialog(frame, "Facility added successfully!");
            facilitiesListModel.addElement(name);
            update();
        } else {
            JOptionPane.showMessageDialog(frame, "Facility with this name already exists!");
        }
    }

    // Modifies: this
    // Effects: updates the UI
    private void update() {
        updateTextArea();
        if (selectedFacility != null) {
            displayDetails(selectedFacility);
        }
        rightPanel.repaint();
        rightPanel.revalidate();
    }

    // Effects : prompts the user for the string input
    private String inputString(String str) {
        String input = JOptionPane.showInputDialog(str);
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        return input.trim();
    }

    // Effects: parses the string input to integer
    private int inputInteger(String str) {
        String input = inputString(str);
        try {
            return Integer.parseInt(input);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(frame, "Null input, try again!");
            return -1;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter an integer.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    // Modifies: this
    // Effects: makes the text area for the displaying the totals on the right panel
    private void makeTextArea() {
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        textArea.setText(getText());
        textArea.setBackground(Color.white);
    }

    // Modifies: this
    // Effects: prompts the user for the name of the facility to remove, removes if the facility with name exists,
    //         does nothing if facility with name does not exist.
    private void removeFacility() {
        if (region.getFacilities().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Empty Region! Facility cannot be removed.");
            return;
        }
        String name = inputString("Enter the name of the facility to remove: ");
        if (name == null || name.trim().isEmpty()) {
            return;
        }
        if (Objects.equals(selectedFacility.getName(), name)) {
            JOptionPane.showMessageDialog(frame, "Please deselect the facility (by selecting another facility)"
                    + " and try again");
            return;
        }
        if (region.removeFacility(name)) {
            JOptionPane.showMessageDialog(frame,"Facility removed successfully!");
            facilitiesListModel.removeElement(name);
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Facility with the given name doesn't exist! Can't be removed");
        }
        update();
    }


    // Effects: updates the text area
    private void updateTextArea() {
        textArea.setText(getText());
    }


    // Effects: gets the text to be displayed on the right panel
    private String getText() {
        String profit = "Region is not profitable";
        if (region.isProfitable()) {
            profit = "Region is profitable";
        }
        return "Total Revenue earned= " + region.calculateTotalRevenueRegion() + " cents\n"
                + "Total Resources invested = " + region.calculateTotalResourcesRegion() + " cents\n"
                + "Total Expenses = " + region.calculateTotalExpenses() + " cents\n"
                + profit;
    }

    // Modifies: this
    // Effects: adds Employee type to a facility
    private void addEmployeeType() {
        if (region.getFacilities().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Empty Region! Cannot Add Employee Type!");
        } else {
            String name = inputString("Enter the name of the facility to add employee type to: ");
            if (name == null || name.trim().isEmpty()) {
                return;
            }
            Facility facility = this.region.findFacility(name);
            checkFacilityAddable(facility, name);
        }

    }

    // Effects : check if the exists, if it exists prompts the user to add employee type to the facility
    private void checkFacilityAddable(Facility facility, String name) {
        if (facility == null) {
            JOptionPane.showMessageDialog(frame, "Facility does not exist; cannot add employee type!");
        } else {
            String title = inputString("Enter the title for the employee type");
            if (title == null || name.trim().isEmpty()) {
                return;
            }
            int salary = inputInteger("Enter the salary (in cents, use Integer) to be paid ot each employee");
            checkValidInput(salary);
            int count = inputInteger("Enter the count (use Integer) of the employees in the employee type");
            checkValidInput(count);
            if ((facility.addEmployeeType(title, salary, count))) {
                JOptionPane.showMessageDialog(frame, "Employee type added successfully!");
                updateTextArea();
                displayDetails(facility);
            } else {
                JOptionPane.showMessageDialog(frame, "Employee type can't be added!");
            }
        }
    }

    // Effects: Checks if the input is valid
    private void checkValidInput(int number) {
        if (number < 0) {
            return;
        }
    }




    // Modifies : this
    // Effects : makes and returns the JButton to add a facility
    private JButton addFacilityButton() {
        JButton addFacilityButton = new JButton("Add Facility");
        addFacilityButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addFacilityButton.addActionListener(e -> addFacility());

        Dimension buttonSize = new Dimension(150, 25);
        addFacilityButton.setPreferredSize(buttonSize);
        addFacilityButton.setSize(buttonSize);
        return addFacilityButton;
    }

    // Modifies : this
    // Effects: makes and returns the JButton to remove a facility
    private JButton removeFacilityButton() {
        JButton removeFacilityButton = new JButton("Remove Facility");
        removeFacilityButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeFacilityButton.addActionListener(e -> removeFacility());

        Dimension buttonSize = new Dimension(100, 25);
        removeFacilityButton.setPreferredSize(buttonSize);
        removeFacilityButton.setSize(buttonSize);

        return removeFacilityButton;
    }

    // Modifies: this
    // Effects: makes and returns the JButton to add employee type to a facility
    private JButton addEmployeeTypeButton() {
        JButton addEmployeeTypeButton = new JButton("Add Employee Type");
        addEmployeeTypeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addEmployeeTypeButton.addActionListener(e -> addEmployeeType());

        Dimension buttonSize = new Dimension(100, 25);
        addEmployeeTypeButton.setPreferredSize(buttonSize);
        addEmployeeTypeButton.setSize(buttonSize);

        return addEmployeeTypeButton;
    }

    // Modifies: this
    // Effects: makes and returns the JButton to transfer resources from one facility to another
    private JButton transferResourcesButton() {
        JButton transferResources = new JButton("Transfer Resources");
        transferResources.setAlignmentX(Component.CENTER_ALIGNMENT);
        transferResources.addActionListener(e -> transferResources());

        Dimension buttonSize = new Dimension(100, 25);
        transferResources.setPreferredSize(buttonSize);
        transferResources.setSize(buttonSize);

        return transferResources;
    }

    // Effects: transfers resources between facilities
    private void transferResources() {
        if (region.getFacilities().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Empty Region! Transfer not possible");
            return;
        } else if (region.getFacilities().size() == 1) {
            JOptionPane.showMessageDialog(frame, "Region has only one facility, can't transfer!");
            return;
        }
        String from = inputString("Enter the name of the facility transfer resources from: ");
        if (from == null || from.trim().isEmpty()) {
            return;
        }
        String to = inputString("Enter the name of the facility to transfer resources to: ");
        if (to == null || to.trim().isEmpty()) {
            return;
        }
        int amount = inputInteger("Enter the amount of resources to transfer (in Cents, use Integer): ");
        checkValidInput(amount);
        makeTransfer(from, to, amount);
        detailsText.repaint();
        displayDetails(selectedFacility);
        detailsPane.repaint();
    }

    // Modifies: this
    // Effects : makes transfer from "from" to "to" of amount "amount" and notifies the user
    private void makeTransfer(String from, String to, int amount) {
        if (region.transferResources(from, to, amount)) {
            JOptionPane.showMessageDialog(frame,"Transfer of " + amount + " from " + from + " to "
                    + to +  " successful");
        } else {
            JOptionPane.showMessageDialog(frame, "Transfer not possible!");
        }
    }

    // Modifies: this
    // Effects: sets text for the text area
    private void clearDetails() {
        if (detailsText != null) {
            detailsText.setText(""); // Clear the text area
        }
    }
}