package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegionTest {
    private Region testRegion;
    private Facility testFacility1;
    private Facility testFacility3;

    @BeforeEach
    void setUp() {
        testRegion = new Region("Vancouver");
        testFacility1 = new Facility("Facility1", 1000, 40000
                , 2500);
        testFacility3 = new Facility("Facility3", 500000, 350000, 35000);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testRegion.getFacilities().size());
    }

    @Test
    void addFacilityOnceTest() {
        assertTrue(testRegion.addFacility("Facility1", 1000, 40000
                , 2500));
        assertEquals(1, testRegion.getFacilities().size());
        assertEquals("Facility1", testRegion.getFacilities().get(0).getName());
    }

    @Test
    void addFacilityMultipleTest() {
        assertTrue(testRegion.addFacility("Facility1", 1000, 40000
                , 2500));
        assertTrue(testRegion.addFacility("Facility2", 9990, 100000,
                45000));
        assertTrue(testRegion.addFacility("Facility3", 500000, 350000,
                35000));
        assertEquals(3, testRegion.getFacilities().size());
        assertEquals("Facility1", testRegion.getFacilities().get(0).getName());
        assertEquals("Facility2", testRegion.getFacilities().get(1).getName());
        assertEquals("Facility3", testRegion.getFacilities().get(2).getName());
        assertFalse(testRegion.addFacility("Facility2", 9990, 100000,
                45000));
        assertEquals(3, testRegion.getFacilities().size());
    }

    @Test
    void removeFacilityNotInTest() {
        testRegion.addFacility("Facility1", 1000, 40000
                , 2500);
        assertFalse(testRegion.removeFacility("Facility2"));
    }

    @Test
    void removeFacilityTest() {
        testRegion.addFacility("Facility1", 1000, 40000
                , 2500);
        testRegion.addFacility("Facility2", 9990, 100000, 45000);
        testRegion.addFacility("Facility3", 500000, 350000, 35000);
        assertTrue(testRegion.removeFacility("Facility2"));
        assertEquals(2, testRegion.countFacilities());
    }

    @Test
    void calculateTotalRevenueEmptyTest() {
        assertEquals(0, testRegion.calculateTotalRevenueRegion());
    }

    @Test
    void calculateTotalRevenueMultipleTest() {
        testRegion.addFacility("Facility1", 1000, 40000
                , 2500);
        testRegion.addFacility("Facility2", 9990, 100000, 45000);
        testRegion.addFacility("Facility3", 500000, 350000, 35000);
        assertEquals(1000 + 9990 + 500000, testRegion.calculateTotalRevenueRegion());
    }

    @Test
    void calculateTotalResourcesEmptyTest() {
        assertEquals(0, testRegion.calculateTotalResourcesRegion());
    }

    @Test
    void calculateTotalResourcesMultipleTest() {
        testRegion.addFacility("Facility1", 1000, 40000
                , 2500);
        testRegion.addFacility("Facility2", 9990, 100000, 45000);
        testRegion.addFacility("Facility3", 500000, 350000, 35000);
        assertEquals(40000 + 100000 + 350000, testRegion.calculateTotalResourcesRegion());
    }

    @Test
    void calculateTotalExpensesEmptyTest() {
        assertEquals(0, testRegion.calculateTotalResourcesRegion());
    }

    @Test
    void calculateTotalExpensesMultipleTest() {
        testRegion.addFacility("Facility1", 1000, 40000
                , 2500);
        testRegion.addFacility("Facility2", 9990, 100000, 45000);
        testRegion.addFacility("Facility3", 500000, 350000, 35000);

        testRegion.getFacilities().get(0).addEmployeeType("Manager", 2000, 1);
        testRegion.getFacilities().get(0).addEmployeeType("Receptionist", 1000, 2);
        testRegion.getFacilities().get(1).addEmployeeType("Manager", 5000, 3);
        testRegion.getFacilities().get(2).addEmployeeType("Helper", 500, 5);

        assertEquals(2500 + 45000 + 35000 + 2000 + 2000 + 15000 + 2500, testRegion.calculateTotalExpenses());
    }


    @Test
    void countFacilityTest() {
        assertEquals(0, testRegion.countFacilities());
        testRegion.addFacility("Facility1", 1000, 40000
                , 2500);
        assertEquals(1, testRegion.countFacilities());
        testRegion.addFacility("Facility2", 9990, 100000, 45000);
        testRegion.addFacility("Facility3", 500000, 350000, 35000);
        assertEquals(3, testRegion.countFacilities());
    }

    @Test
    void getTotalExpenseRegionTest() {
        assertEquals(0, testRegion.calculateTotalExpenses());
        testRegion.addFacility("Facility1", 1000, 40000
                , 2500);
        testRegion.addFacility("Facility2", 9990, 100000, 45000);
        testRegion.addFacility("Facility3", 500000, 350000, 35000);

        assertEquals(82500, testRegion.calculateTotalExpenses());

    }

    @Test
    void transferMoneyNotPossible() {
        testRegion.addFacility("Facility1", 1000, 40000
                , 2500);
        testRegion.addFacility("Facility2", 9990, 100000, 45000);
        testRegion.addFacility("Facility3", 500000, 350000, 35000);
        assertFalse(testRegion.transferResources("Facility1", "Facility2", 40001));
    }
    @Test
    void transferMoneyFacilityNotFound() {
        assertFalse(testRegion.transferResources("Facility1", "Facility2", 1000));
        assertEquals(40000 , testFacility1.getResources());
        assertEquals( 350000, testFacility3.getResources());
    }

    @Test
    void transferMoneyPossible() {
        testRegion.addFacility("Facility1", 1000, 40000
                , 2500);
        testRegion.addFacility("Facility3", 500000, 350000, 35000);
        assertTrue(testRegion.transferResources("Facility1", "Facility3", 1000));
        assertEquals(40000 - 1000, testRegion.getFacilities().get(0).getResources());
        assertEquals(1000 + 350000,  testRegion.getFacilities().get(1).getResources());
    }

    @Test
    void isProfitableEmptyTest() {
        assertFalse(testRegion.isProfitable());
    }

    @Test
    void isProfitableFalseTest() {
        testRegion.addFacility("Facility2", 9990, 100000, 45000);
        assertFalse(testRegion.isProfitable());
    }

    @Test
    void isProfitableMultipleTest() {
        testRegion.addFacility("Facility1", 1000, 40000
                , 2500);
        testRegion.addFacility("Facility2", 9990, 100000, 45000);
        testRegion.addFacility("Facility3", 500000, 350000, 35000);
        assertTrue(testRegion.isProfitable());
    }

    @Test
    void getName(){
        assertEquals("Vancouver",testRegion.getName());
    }


}