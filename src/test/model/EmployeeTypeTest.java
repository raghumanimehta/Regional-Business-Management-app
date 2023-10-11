package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTypeTest {
    private EmployeeType testEmployeeType;

    @BeforeEach
    void setUp(){
        testEmployeeType = new EmployeeType("test", 2000, 1000);
    }
    @Test
    void getTtoalMoneytoBePaidTest(){
        assertEquals(2000*1000, testEmployeeType.getTotalMoneyToBePaid());
    }
}
