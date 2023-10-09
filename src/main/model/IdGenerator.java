package model;

/*
 * Generates id for employee type.
 */
public class IdGenerator {

    private static int id;

    public void id() {
        id = 0;
    }

    public static int generateId() {
        return id++;
    }
}
