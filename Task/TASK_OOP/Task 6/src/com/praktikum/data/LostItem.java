package com.praktikum.data;

public class LostItem {
    private String name;
    private String description;
    private String location;
    private String status;
    private String reporter;

    public LostItem(String name, String description, String location, String status, String reporter) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.status = status;
        this.reporter = reporter;
    }

    // Getters and setters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public String getStatus() { return status; }
    public String getReporter() { return reporter; }

    public void setStatus(String status) { this.status = status; }
}
