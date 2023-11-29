package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("Region's revenue was calculated");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Region's revenue was calculated", e.getDescription());
        assertEquals(e.getDate(), e.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Region's revenue was calculated", e.toString());
    }

    @Test
    public void equalsNullTest() {
        assertFalse(e.equals(null));
    }

    @Test
    public void equalsFalse() {
        assertFalse(e.equals(new Object()));
    }
}
