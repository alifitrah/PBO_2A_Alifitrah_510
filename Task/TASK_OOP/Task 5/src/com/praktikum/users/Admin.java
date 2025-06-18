package com.praktikum.users;

import com.praktikum.actions.AdminActions;
import com.praktikum.main.LoginSystem;
import com.praktikum.data.Item;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Admin extends User implements AdminActions {

    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public boolean login(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    @Override
    public void displayAppMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1; // FIX

        do {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Manage Item Reports");
            System.out.println("2. Manage Student Data");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // clear newline
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number!");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> manageItems();
                case 2 -> manageUsers();
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
    public void manageItems() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1; // fix

        do {
            System.out.println("\n--- Manage Item Reports ---");
            System.out.println("1. View All Reports");
            System.out.println("2. Mark Item as Claimed");
            System.out.println("0. Back");
            System.out.print("Select: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number!");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> {
                    for (int i = 0; i < LoginSystem.reportedItems.size(); i++) {
                        Item item = LoginSystem.reportedItems.get(i);
                        System.out.println("[" + i + "] " + item.getItemName() + " - " + item.getStatus());
                    }
                }
                case 2 -> {
                    for (int i = 0; i < LoginSystem.reportedItems.size(); i++) {
                        Item item = LoginSystem.reportedItems.get(i);
                        if (item.getStatus().equals("Reported")) {
                            System.out.println("[" + i + "] " + item.getItemName());
                        }
                    }
                    System.out.print("Enter index to mark as claimed: ");
                    try {
                        int index = scanner.nextInt();
                        Item item = LoginSystem.reportedItems.get(index);
                        if (item.getStatus().equals("Reported")) {
                            item.setStatus("Claimed");
                            System.out.println("Item marked as Claimed.");
                        } else {
                            System.out.println("Item already claimed.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Input must be a number!");
                        scanner.nextLine();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid index selected!");
                    }
                }
                case 0 -> {}
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }


    @Override
    public void manageUsers() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1; // FIX

        do {
            System.out.println("\n--- Manage Student Data ---");
            System.out.println("1. Add Mahasiswa");
            System.out.println("2. Delete Mahasiswa");
            System.out.println("0. Back");
            System.out.print("Select: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number!");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter NIM: ");
                    String nim = scanner.nextLine();
                    LoginSystem.userList.add(new Mahasiswa(name, nim));
                    System.out.println("Mahasiswa added.");
                }
                case 2 -> {
                    System.out.print("Enter NIM to delete: ");
                    String targetNim = scanner.nextLine();
                    boolean found = false;
                    Iterator<User> iterator = LoginSystem.userList.iterator();
                    while (iterator.hasNext()) {
                        User user = iterator.next();
                        if (user instanceof Mahasiswa && user.password.equals(targetNim)) {
                            iterator.remove();
                            found = true;
                            System.out.println("Mahasiswa removed.");
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Mahasiswa with that NIM not found.");
                    }
                }
                case 0 -> {}
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }
}
