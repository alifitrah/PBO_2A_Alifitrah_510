package com.praktikum.users;

public abstract class User {
    public String username;
    public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public abstract boolean login(String inputUsername, String inputPassword);

    public abstract void displayAppMenu();

    public abstract Object getUsername();

}