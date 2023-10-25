package persistence;
import model.Region;

import org.junit.jupiter.api.Test;


import java.io.IOException;

/*
Test class for Json reader
 */
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;



/*
Test Class for JsonReader
 */
public class JsonReaderTest {

    @Test
    void testReaderNotExistent() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Region region = reader.loadRegion();
            fail("IOException expected");
        } catch (IOException e) {
            // should pass
        } catch (NullPointerException e) {
            // should pass
        }
    }

    @Test
    void testReaderNormalRegion() {
        JsonReader reader = new JsonReader("./data/writerNormalRegion.json");
        try {
          Region region = reader.loadRegion();
          assertEquals("Vancouver", region.getName());
          assertEquals(3, region.getFacilities().size());
          assertEquals("F1", region.getFacilities().get(0).getName());
          assertEquals("F2", region.getFacilities().get(1).getName());
          assertEquals("F3", region.getFacilities().get(2).getName());
          assertEquals(1000 + 5000 + 1230, region.calculateTotalRevenueRegion());
          assertEquals(3000 + 1100 + 9820 + 2000 + 100000 + 300 + 600 + 1000 + 135 + 100,
                  region.calculateTotalExpenses());
          assertEquals(9580, region.calculateTotalResourcesRegion());
        } catch (IOException e) {
            fail("Shouldn't be thrown!");
        }
    }

    @Test
    void testReaderEmptyRegion() {
        JsonReader reader = new JsonReader("./data/WriterEmptyRegion.json");
        try {
            Region region = reader.loadRegion();
            assertEquals("Vancouver", region.getName());
            assertEquals(0, region.getFacilities().size());

            assertEquals(0, region.calculateTotalRevenueRegion());
            assertEquals(0,
                    region.calculateTotalExpenses());
            assertEquals(0, region.calculateTotalResourcesRegion());
        } catch (IOException e) {
            fail("Shouldn't be thrown!");
        }
    }
}

