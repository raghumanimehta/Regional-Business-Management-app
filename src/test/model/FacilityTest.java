package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FacilityTest {
    private Facility testFacility1;
    private Facility testFacility2;
    private Facility testFacility3;

    @BeforeEach
    void setUp(){
        testFacility1 = new Facility("Facility1", 1000, 40000
                , 2500);
        testFacility2 = new Facility("Facility2", 1000, 40000
                , 1);
        testFacility3 = new Facility("Facility3", 1000, 40000
                , 1000);
    }


    @Test
    void testFacilityConstructor(){
        assertEquals(1000, testFacility1.getRevenue());
        assertEquals("Facility1", testFacility1.getName());
        assertEquals(2500, testFacility1.getExpensesOtherThanSalaries());
        assertEquals(0, testFacility1.getEmployeeTypes().size());
    }


    @Test
    void addEmployeeTypeSameTest(){
        assertTrue(this.testFacility1.addEmployeeType( "Manager", 10000, 1));
        assertEquals(1, this.testFacility1.countEmployeeTypes());
        assertFalse(this.testFacility1.addEmployeeType( "Manager", 10000, 1));
        assertEquals(1, this.testFacility1.countEmployeeTypes());
    }


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

    @Test
    void increaseExpensesTest(){
        testFacility1.increaseExpensesOtherThanSalaries(100);
        assertEquals(2600, testFacility1.getExpensesOtherThanSalaries());
    }
    @Test
    void DecreaseExpensesTest(){
        testFacility1.decreaseExpensesOtherThanSalaries(100);
        assertEquals(2400, testFacility1.getExpensesOtherThanSalaries());
    }
    @Test
    void decreaseResourcesTest(){
        testFacility1.decreaseResources(100);
        assertEquals(39900, testFacility1.getResources());
    }

    @Test
    void increaseResourcesTest(){
        testFacility1.increaseResources(100);
        assertEquals(40100, testFacility1.getResources());
    }

    @Test
    void getSalariesNoEmployeeTest() {
        assertEquals(0, testFacility1.getSalaries());
    }

    @Test
    void getSalariesTestMain() {
        testFacility1.addEmployeeType("Manager", 1000, 3);
        testFacility1.addEmployeeType("CEO", 5000, 1);
        testFacility1.addEmployeeType("Assistant Manager", 2000, 1);
        assertEquals(10000, testFacility1.getSalaries());
    }

    @Test
    void isProfitableTest() {
        assertFalse(testFacility1.isProfitable());
        assertTrue(testFacility2.isProfitable());
        assertFalse(testFacility3.isProfitable());
    }

}
