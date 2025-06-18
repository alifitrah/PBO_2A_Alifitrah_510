package com.praktikum.users;

public class Mahasiswa extends User {
    private String nim;

    public Mahasiswa(String username, String password, String nim) {
        super(username, password);
        this.nim = nim;
    }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    @Override
    public boolean authenticate(String inputPassword) {
        return getPassword().equals(inputPassword);
    }
}