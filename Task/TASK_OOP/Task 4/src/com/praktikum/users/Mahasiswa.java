package com.praktikum.users;

import com.praktikum.actions.MahasiswaActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mahasiswa extends User implements MahasiswaActions {

    private static final Scanner scanner = new Scanner(System.in);
    private final List<Report> reportList = new ArrayList<>();

    public Mahasiswa(String username, String password) {
        super(username, password);
    }

    @Override
    public boolean login(String inputUsername, String inputPassword) {
        // Assuming username and password are protected or have getters in User class
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    @Override
    public void displayAppMenu() {
        int choice;
        do {
            System.out.println("\n=== Menu Mahasiswa ===");
            System.out.println("1. Report Found/Lost Items");
            System.out.println("2. View Report List");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.next(); // discard invalid input
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> reportItem();
                case 2 -> viewReportedItems();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    @Override
    public void reportItem() {
        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();

        System.out.print("Enter Item Description: ");
        String itemDesc = scanner.nextLine();

        System.out.print("Enter Last Location/Found: ");
        String location = scanner.nextLine();

        reportList.add(new Report(itemName, itemDesc, location));
        System.out.println(">> Report Submitted Successfully <<");
    }

    @Override
    public void viewReportedItems() {
        if (reportList.isEmpty()) {
            System.out.println(">> No reports available <<");
        } else {
            System.out.println("\n--- Report List ---");
            int i = 1;
            for (Report report : reportList) {
                System.out.printf("%d. %s - %s (Location: %s)%n", i++, report.itemName, report.itemDesc, report.location);
            }
        }
    }

    // Inner class to represent a report
    private static class Report {
        String itemName;
        String itemDesc;
        String location;

        Report(String itemName, String itemDesc, String location) {
            this.itemName = itemName;
            this.itemDesc = itemDesc;
            this.location = location;
        }
    }
}
