package com.practicum.storage;

import com.practicum.models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryData {
    public static List<User> users = new ArrayList<>();
    public static List<Member> members = new ArrayList<>();
    public static List<Book> books = new ArrayList<>();
    public static List<Book> borrowedBooks = new ArrayList<>();

    static {
        initialize();
    }

    public static void initialize() {
        users.clear();
        members.clear();
        books.clear();

        // Load from file if exists
        loadMembersFromFile();

        // Add admin
        users.add(new Admin("admin", "admin123"));

        // Add books
        books.add(new Book("123", "Harry Potter", "J.K. Rowling", "Fantasy"));
        books.add(new Book("456", "Java Programming", "John Doe", "Technology"));
        books.add(new Book("789", "Data Structures", "Jane Smith", "Computer Science"));
    }

    public static void saveMembersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("members.txt"))) {
            for (Member m : members) {
                writer.write(m.getUsername() + "," + m.getPassword() + "," +
                        m.getId() + "," + m.getFullName() + "," +
                        m.getMajor() + "," + m.getEmail());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving members: " + e.getMessage());
        }
    }

    public static void loadMembersFromFile() {
        File file = new File("members.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    Member m = new Member(parts[2], parts[3], parts[4], parts[5], parts[0], parts[1]);
                    members.add(m);
                    users.add(m);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading members: " + e.getMessage());
        }
    }
}
