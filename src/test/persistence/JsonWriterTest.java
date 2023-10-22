package persistence;

import model.*;

import org.junit.jupiter.api.Test;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testInvalidFile() {
        try {
            Region region = new Region("Vancouver");
            JsonWriter writer = new JsonWriter("/data/ThisIsAnIllegalNmae\t\0.json");
            writer.openWriter();
            writer.write(region);
            writer.closeWriter();
            fail("IOException was expected!");
        } catch (IOException e) {
            // should pass
        }
    }

    @Test
    void testWriterNormalRegion() {
       try {
           Region region = new Region("Vancouver");
           region.addFacility("F1", 1000, 1000,1000);
           region.addFacility("F2", 10000, 3000, 2000);
           region.addFacility("F3", 30450, 4000, 6000);
           region.getFacilities().get(0).addEmployeeType("Manager", 2000, 1);
           region.getFacilities().get(0).addEmployeeType("Helper", 100, 10);
           region.getFacilities().get(1).addEmployeeType("Manager", 300, 1);
           region.getFacilities().get(2).addEmployeeType("YouTuber", 10000, 3);

           JsonWriter writer = new JsonWriter("./data/WriterNormalRegion1.json");

           writer.openWriter();
           writer.write(region);
           writer.closeWriter();

           Region region1;
           JsonReader reader = new JsonReader("./data/WriterNormalRegion1.json");
           region1 = reader.loadRegion();
           assertEquals("Vancouver", region.getName());
           assertEquals(3, region1.getFacilities().size());
           assertEquals(1000 + 2000 + 6000 + 2000 + 1000 + 300 + 30000, region1.calculateTotalExpenses());
           assertEquals(30450 + 1000 + 10000, region1.calculateTotalRevenueRegion());
           assertEquals(1000 + 3000 + 4000, region1.calculateTotalResourcesRegion());
           assertEquals("F1", region1.getFacilities().get(0).getName());
           assertEquals("F2", region1.getFacilities().get(1).getName());
           assertEquals("F3", region1.getFacilities().get(2).getName());

       } catch (IOException e) {
           fail("Should not throw exception");
       }
    }

    @Test
    void testWriterEmptyRegion() {
        try {
            Region region = new Region("Vancouver");
            JsonWriter writer = new JsonWriter("./data/WriterEmptyRegion.json");

            writer.openWriter();
            writer.write(region);
            writer.closeWriter();

            Region region1;
            JsonReader reader = new JsonReader("./data/WriterEmptyRegion.json");
            region1 = reader.loadRegion();
            assertEquals("Vancouver", region.getName());
            assertTrue(region1.getFacilities().isEmpty());
            assertEquals(0, region1.calculateTotalResourcesRegion());
            assertEquals(0, region1.calculateTotalRevenueRegion());
            assertEquals(0, region1.calculateTotalExpenses());
        } catch (IOException e) {
            fail("Should not throw exception");
        }
    }

}
