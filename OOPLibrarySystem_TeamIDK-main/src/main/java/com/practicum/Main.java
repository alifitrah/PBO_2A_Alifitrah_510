package com.practicum;

import com.practicum.controllers.LoginController;
import com.practicum.storage.Persistence;
import com.practicum.storage.LibraryData;
import com.practicum.models.Admin;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        LibraryData.initialize();

        Persistence.loadBooks(LibraryData.books);
        Persistence.loadMembers(LibraryData.members);

        LibraryData.users.clear();
        LibraryData.users.add(new Admin("admin", "admin123"));  // Hardcoded admin
        LibraryData.users.addAll(LibraryData.members);

        primaryStage.setTitle("Library Management System");
        primaryStage.setResizable(true);

        new LoginController(primaryStage).showLoginPage();

        primaryStage.setOnCloseRequest(event -> {
            Persistence.saveBooks(LibraryData.books);
            Persistence.saveMembers(LibraryData.members);
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


