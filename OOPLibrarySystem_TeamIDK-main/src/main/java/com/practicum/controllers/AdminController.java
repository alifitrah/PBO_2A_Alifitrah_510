package com.practicum.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminController {
    private final Stage stage;

    public AdminController(Stage stage) {
        this.stage = stage;
    }

    public void showAdminMenu() {
        // === Logo (Top-Right) ===
        Image logoImg = new Image(getClass().getResource("/images/ummlogo.png").toExternalForm());
        ImageView logo = new ImageView(logoImg);
        logo.setFitWidth(100);
        logo.setPreserveRatio(true);
        logo.getStyleClass().add("image-view");

        HBox logoBox = new HBox(logo);
        logoBox.setAlignment(Pos.TOP_RIGHT);
        logoBox.setPadding(new Insets(20, 20, 0, 0));

        // === Back Button (Top-Left) ===
        Button backButton = new Button("←");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> new LoginController(stage).showLoginPage());

        HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.TOP_LEFT);
        backBox.setPadding(new Insets(20, 0, 0, 20));

        HBox header = new HBox(backBox, new Region(), logoBox);
        HBox.setHgrow(header.getChildren().get(1), Priority.ALWAYS);

        // === Action Buttons ===
        Button bookButton = new Button("Manage Books");
        Button memberButton = new Button("Manage Members");
        Button reportButton = new Button("Reports");

        bookButton.getStyleClass().add("main-button");
        memberButton.getStyleClass().add("main-button");
        reportButton.getStyleClass().add("main-button");

        bookButton.setOnAction(e -> new BookController(stage).showBookMenu());
        memberButton.setOnAction(e -> new ManageMemberController(stage).showManageMembers());
        reportButton.setOnAction(e -> new ReportController(stage).showReportMenu());

        VBox buttonsBox = new VBox(20, bookButton, memberButton, reportButton);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(40));

        // === Card Layout ===
        VBox card = new VBox(buttonsBox);
        card.getStyleClass().add("card");
        card.setAlignment(Pos.CENTER);

        // === Main Layout ===
        VBox mainLayout = new VBox(30, header, card);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.getStyleClass().add("root");

        Scene scene = new Scene(mainLayout, 600, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/admin.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.setResizable(true);
        stage.show();
    }
}
