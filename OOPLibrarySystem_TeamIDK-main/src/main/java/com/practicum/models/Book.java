package com.practicum.models;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String category;
    private boolean available = true;

    // ✅ Constructor with category
    public Book(String isbn, String title, String author, String category) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
    }

    // ✅ Optional constructor without category (default category used)
    public Book(String isbn, String title, String author) {
        this(isbn, title, author, "Uncategorized");
    }

    // ✅ Getters
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    // ✅ Availability methods
    public void borrowBook() {
        this.available = false;
    }

    public void returnBook() {
        this.available = true;
    }

    @Override
    public String toString() {
        return isbn + " - " + title + " by " + author + " (" + category + ")"
                + (available ? " [Available]" : " [Borrowed]");
    }
}
