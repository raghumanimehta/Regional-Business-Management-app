package ui;


import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new ManagementAppUI("Vancouver");
        } catch (FileNotFoundException e) {
            System.out.println("Error encountered! File not found!");
        }


        try {
            new ManagementApp("Vancouver");
        } catch (FileNotFoundException e) {
            System.out.println("Error encountered! File not found!");
        }
    }

}
