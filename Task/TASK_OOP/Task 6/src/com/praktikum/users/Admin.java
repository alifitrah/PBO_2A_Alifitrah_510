package com.praktikum.users;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public boolean authenticate(String inputPassword) {
        return getPassword().equals(inputPassword);
    }
}