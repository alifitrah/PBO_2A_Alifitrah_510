package com.praktikum.users;

import com.praktikum.actions.MahasiswaActions;
import com.praktikum.main.LoginSystem;
import com.praktikum.data.Item;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Mahasiswa extends User implements MahasiswaActions {
    private String nim;

    public Mahasiswa(String name, String nim) {
        super(name, nim);
        this.nim = nim;
    }

    @Override
    public boolean login(String inputName, String inputNim) {
        return this.username.equals(inputName) && this.password.equals(inputNim);
    }

    @Override
    public void displayAppMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1; // fix untuk choice

        do {
            System.out.println("\n=== Mahasiswa Menu ===");
            System.out.println("1. Report Found/Lost Items");
            System.out.println("2. View Report List");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number!");
                scanner.nextLine(); // clear buffer
                continue;
            }

            switch (choice) {
                case 1 -> reportItem();
                case 2 -> viewReportedItems();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    @Override
    public Object getUsername() {
        return null;
    }

    @Override
    public void reportItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Item Name: ");
        String name = scanner.nextLine();
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Last Known Location: ");
        String location = scanner.nextLine();

        Item item = new Item(name, desc, location);
        LoginSystem.reportedItems.add(item);
        System.out.println(">> Item reported successfully!");
    }

    @Override
    public void viewReportedItems() {
        boolean found = false;
        if (LoginSystem.reportedItems.isEmpty()) {
            System.out.println("No item reports yet.");
        } else {
            System.out.println("\n=== Reported Items ===");
            for (Item item : LoginSystem.reportedItems) {
                if (item.getStatus().equals("Reported")) {
                    System.out.println("Item: " + item.getItemName());
                    System.out.println("Description: " + item.getDescription());
                    System.out.println("Location: " + item.getLocation());
                    System.out.println("Status: " + item.getStatus());
                    System.out.println("------------------------");
                    found = true;
                }
            }
            if (!found) System.out.println("No active reported items.");
        }
    }
}
