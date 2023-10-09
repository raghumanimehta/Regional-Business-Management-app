package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FacilityTest {
    private Facility testFacility1;
    private Facility testFacility2;
    private Region testRegion;
    private EmployeeType testEmployeeType1;
    private EmployeeType testEmployeeType2;
    private EmployeeType testEmployeeType3;
    @BeforeEach
    void setUp(){
        testRegion = new Region("Vancouver");
        testFacility1 = new Facility("Facility1", 1000, 40000
                , 2500);
        testFacility2 = new Facility("Facility2", 9990, 100000,45000);
        testEmployeeType1 = new EmployeeType( "Manager", 10000, 1);
        testEmployeeType2 = new EmployeeType( "Assistant Manager", 9000, 2);
        testEmployeeType3 = new EmployeeType("Worker", 4000, 10);

    }

    //TODO
    @Test
    void testFacilityConstructor(){
        assertEquals(1000, testFacility1.getRevenue());
        assertEquals("Facility1", testFacility1.getName());
        assertEquals(2500, testFacility1.getExpensesOtherThanSalaries());
        assertEquals(0, testFacility1.getEmployeeTypes().size());
    }

    //TODO
    @Test
    void addEmployeeTypeSameTest(){
        assertTrue(this.testFacility1.addEmployeeType( "Manager", 10000, 1));
        assertEquals(1, this.testFacility1.countEmployeeTypes());
        assertFalse(this.testFacility1.addEmployeeType( "Manager", 10000, 1));
        assertEquals(1, this.testFacility1.countEmployeeTypes());
    }

    //TODO
    @Test
    void addEmployeeTypeDifferentTest(){
        assertTrue(this.testFacility1.addEmployeeType( "Manager", 10000, 1));
        assertEquals(1, this.testFacility1.countEmployeeTypes());
        assertTrue(this.testFacility1.addEmployeeType( "CEO", 100000, 2));
        assertEquals(2, this.testFacility1.countEmployeeTypes());
    }

    @Test
    void removeEmployeeTypeTest(){
        this.testFacility1.addEmployeeType( "Manager", 10000, 1);
        this.testFacility1.addEmployeeType( "CEO", 100000, 2);
        assertTrue(this.testFacility1.removeEmployeeType("Manager"));
        assertEquals(1, this.testFacility1.countEmployeeTypes());
        assertFalse(this.testFacility1.removeEmployeeType("Manager"));
    }

    //TODO
    @Test
    void calculateSalaryToEmployeeTypeTest(){
        this.testFacility1.addEmployeeType("Manager", 100000, 1);
        this.testFacility1.addEmployeeType("Receptionist", 1000, 2);
        assertEquals(100000, testFacility1.calculateSalaryToEmployeeType("Manager"));
        assertEquals(2000, testFacility1.calculateSalaryToEmployeeType("Receptionist"));
        assertEquals(-1, testFacility1.calculateSalaryToEmployeeType("HEL"));
    }

    @Test
    void calculateTotalMoneyToBePaidToEmployeesTest(){
        this.testFacility1.addEmployeeType("Manager", 100000, 1);
        this.testFacility1.addEmployeeType("Receptionist", 1000, 2);
        this.testFacility1.addEmployeeType("Helper", 100, 5);
        assertEquals(100000 + 2000 + 500,testFacility1.calculateTotalMoneyToBePaidToEmployees());
    }
    @Test
    void calculateTotalMoneyToBePaidToEmployeesEmptyTest(){
        assertEquals(0,testFacility1.calculateTotalMoneyToBePaidToEmployees());
    }

    @Test
    void countEmployeeTest(){
        assertEquals(0, this.testFacility1.countEmployeeTypes());
        this.testFacility1.addEmployeeType("Manager", 100000, 1);
        this.testFacility1.addEmployeeType("Helper", 100, 5);
        this.testFacility1.addEmployeeType("Helper", 100, 5);
        assertEquals(2, this.testFacility1.countEmployeeTypes());
    }

}
