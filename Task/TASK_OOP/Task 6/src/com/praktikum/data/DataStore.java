package com.praktikum.data;

import com.praktikum.users.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private List<User> users;
    private List<LostItem> lostItems;

    public DataStore() {
        users = new ArrayList<>();
        lostItems = new ArrayList<>();

        // Initialize with sample data
        users.add(new Admin("admin", "admin510"));
        users.add(new Mahasiswa("fitrah", "510", "2023103703110510"));
        users.add(new Mahasiswa("fariz", "039", "2023103703110039"));

        // Sample lost items
        lostItems.add(new LostItem("HP", "Smartphone", "Meja A-01", "Reported", "herdiana"));
        lostItems.add(new LostItem("Dompet", "Dompet Kulit Hitam", "Meja 8-19", "Claimed", "nisrina"));
    }

    public User authenticate(String username, String password, boolean isAdmin) {
        for (User user : users) {
            if (user.getUsername().equals(username) &&
                    user.authenticate(password) &&
                    ((isAdmin && user instanceof Admin) || (!isAdmin && user instanceof Mahasiswa))) {
                return user;
            }
        }
        return null;
    }

    // Report methods
    public void addLostItem(LostItem item) {
        lostItems.add(item);
    }

    public List<LostItem> getLostItems() {
        return new ArrayList<>(lostItems);
    }

    public List<LostItem> getLostItemsByUser(String username) {
        List<LostItem> userItems = new ArrayList<>();
        for (LostItem item : lostItems) {
            if (item.getReporter().equals(username)) {
                userItems.add(item);
            }
        }
        return userItems;
    }

    public void updateItemStatus(String itemName, String newStatus) {
        for (LostItem item : lostItems) {
            if (item.getName().equals(itemName)) {
                item.setStatus(newStatus);
                break;
            }
        }
    }
}

