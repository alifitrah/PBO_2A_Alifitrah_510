package com.praktikum.main;

import com.praktikum.users.Admin;
import com.praktikum.users.Mahasiswa;
import com.praktikum.users.User;
import java.util.Scanner;

public class LoginSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = null;

        System.out.println("=== Welcome to the ILab System ===");
        System.out.print("Enter username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();

        // Dummy users
        Admin admin = new Admin("admin", "admin510");
        Mahasiswa student = new Mahasiswa("Ali Fitrah", "510");

        if (admin.login(inputUsername, inputPassword)) {
            user = admin;
        } else if (student.login(inputUsername, inputPassword)) {
            user = student;
        } else {
            System.out.println("Login failed. Invalid credentials.");
            return;
        }

        System.out.println("Login successful. Welcome " + inputUsername + "!");
        user.displayAppMenu();  // Polymorphism in action
    }
}