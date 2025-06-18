package com.praktikum.main;

import com.praktikum.data.Item;
import com.praktikum.users.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginSystem {
    public static ArrayList<User> userList = new ArrayList<>();
    public static ArrayList<Item> reportedItems = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize default data
        userList.add(new Admin("admin", "admin510"));
        userList.add(new Mahasiswa("Fitrah", "510"));

        Scanner scanner = new Scanner(System.in);

        // Login loop
        while (true) {
            System.out.println("\n=== Login System ===");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Mahasiswa");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String input = scanner.nextLine();

            User loggedInUser = null;

            switch (input) {
                case "1":
                    System.out.print("Enter username: ");
                    String adminUser = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String adminPass = scanner.nextLine();
                    loggedInUser = loginAsAdmin(adminUser, adminPass);
                    break;

                case "2":
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter NIM: ");
                    String nim = scanner.nextLine();
                    loggedInUser = loginAsMahasiswa(name, nim);
                    break;

                case "0":
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }

            if (loggedInUser != null) {
                System.out.println("Login successful!");
                loggedInUser.displayAppMenu();  // Polymorphism here
                // After displayAppMenu ends, return to login
            } else {
                System.out.println("Login failed.");
            }
        }
    }

    // Admin login by username/password
    public static User loginAsAdmin(String username, String password) {
        for (User user : userList) {
            if (user instanceof Admin && user.username.equals(username) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }

    // Mahasiswa login by name/NIM
    public static User loginAsMahasiswa(String name, String nim) {
        for (User user : userList) {
            if (user instanceof Mahasiswa && user.username.equals(name) && user.password.equals(nim)) {
                return user;
            }
        }
        return null;
    }
}
