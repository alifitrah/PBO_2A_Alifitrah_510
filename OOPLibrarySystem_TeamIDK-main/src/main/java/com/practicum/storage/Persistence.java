package com.practicum.storage;

import com.practicum.models.Book;
import com.practicum.models.Member;

import java.io.*;
import java.util.List;

public class Persistence {

    public static void saveBooks(List<Book> books) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("books.csv"))) {
            pw.println("ISBN,Title,Author,Category,Available");  // CSV header
            for (Book b : books) {
                pw.println(String.join(",",
                        b.getIsbn(),
                        b.getTitle(),
                        b.getAuthor(),
                        b.getCategory(),
                        String.valueOf(b.isAvailable())));
            }
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    public static void loadBooks(List<Book> books) {
        books.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("books.csv"))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 5) {
                    Book b = new Book(parts[0], parts[1], parts[2], parts[3]);
                    if (!Boolean.parseBoolean(parts[4])) {
                        b.borrowBook();
                    }
                    books.add(b);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
        }
    }

    public static void saveMembers(List<Member> members) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("members.csv"))) {
            pw.println("Username,Password,ID,FullName,Major,Email,BorrowedISBNs");  // CSV header
            for (Member m : members) {
                String borrowed = String.join("|", m.getBorrowedIsbns()); // Use '|' to avoid conflict with commas in CSV
                pw.println(String.join(",",
                        m.getUsername(),
                        m.getPassword(),
                        m.getId(),
                        m.getFullName(),
                        m.getMajor(),
                        m.getEmail(),
                        borrowed));
            }
        } catch (IOException e) {
            System.err.println("Error saving members: " + e.getMessage());
        }
    }

    public static void loadMembers(List<Member> members) {
        members.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("members.csv"))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 7) {
                    Member member = new Member(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                    if (!parts[6].isEmpty()) {
                        for (String isbn : parts[6].split("\\|")) {
                            member.borrowBook(isbn);
                        }
                    }
                    members.add(member);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading members: " + e.getMessage());
        }
    }
}
