package com.practicum.models;

import java.util.ArrayList;
import java.util.List;

public class Member extends User {
    private String id;
    private String fullName;
    private String major;
    private String email;
    private List<String> borrowedIsbns;

    public Member(String username, String password, String id, String fullName, String major, String email) {
        super(username, password);
        this.id = id;
        this.fullName = fullName;
        this.major = major;
        this.email = email;
        this.borrowedIsbns = new ArrayList<>();
    }

    public Member(String username, String password) {
        this(username, password, "", "", "", "");
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMajor() {
        return major;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getBorrowedIsbns() {
        return borrowedIsbns;
    }

    // Setters
    public void setUsername(String username) {
        super.username = username;
    }

    public void setPassword(String password) {
        super.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Borrowing methods
    public void borrowBook(String isbn) {
        borrowedIsbns.add(isbn);
    }

    public void returnBook(String isbn) {
        borrowedIsbns.remove(isbn);
    }
}
