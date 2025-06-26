package com.practicum.controllers;

import com.practicum.models.Admin;
import com.practicum.models.Member;
import com.practicum.models.User;
import com.practicum.storage.LibraryData;
import com.practicum.utils.Session;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;

public class LoginController {

    private final Stage stage;

    public LoginController(Stage stage) {
        this.stage = stage;
    }

    public void showLoginPage() {
        Label titleLabel = new Label("Login");
        titleLabel.getStyleClass().add("title");

        // 🔐 Input fields
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");

        // ⌨️ Keyboard nav
        usernameField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) passwordField.requestFocus();
        });
        passwordField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) login();
        });

        // 🔘 Login Button
        Button loginButton = new Button("LOGIN");
        loginButton.getStyleClass().add("login");
        loginButton.setOnAction(e -> login());

        // 🔗 Register link
        Hyperlink registerLink = new Hyperlink("Register as member");
        registerLink.getStyleClass().add("register");
        registerLink.setOnAction(e -> showRegisterPage());

        Label messageLabel = new Label();

        // 🖼 Logo
        ImageView logo = new ImageView();
        URL logoUrl = getClass().getResource("/images/ummlogo.png");
        if (logoUrl != null) {
            logo.setImage(new Image(logoUrl.toExternalForm()));
            logo.setFitWidth(150);
            logo.setPreserveRatio(true);
            logo.getStyleClass().add("image-view");
        }

        VBox layout = new VBox(12, titleLabel, logo, usernameField, passwordField, loginButton, registerLink, messageLabel);
        layout.getStyleClass().add("root");

        Scene scene = new Scene(layout, 350, 500);
        addStylesheet(scene);

        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    private void login() {
        VBox root = (VBox) stage.getScene().getRoot();
        TextField usernameField = (TextField) root.getChildren().get(2);
        PasswordField passwordField = (PasswordField) root.getChildren().get(3);
        Label messageLabel = (Label) root.getChildren().get(6);

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.equals("admin") && password.equals("admin123")) {
            Session.currentUser = new Admin(username, password);
            new AdminController(stage).showAdminMenu();
            return;
        }

        User found = LibraryData.members.stream()
                .filter(m -> m.getUsername().equals(username) && m.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (found != null) {
            Session.currentUser = found;
            new MemberController(stage).showMemberMenu();
        } else {
            messageLabel.setText("Invalid credentials.");
        }
    }

    private void showRegisterPage() {
        Label title = new Label("Register Member");
        title.getStyleClass().add("title");

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField majorField = new TextField();
        TextField emailField = new TextField();

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        idField.setPromptText("Member ID");
        nameField.setPromptText("Full Name");
        majorField.setPromptText("Major");
        emailField.setPromptText("Email");

        Button registerBtn = new Button("Register");
        Button backBtn = new Button("Back");
        registerBtn.getStyleClass().add("login");
        backBtn.getStyleClass().add("login");

        // ⌨️ Navigation
        usernameField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) passwordField.requestFocus();
        });
        passwordField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) idField.requestFocus();
        });
        idField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) nameField.requestFocus();
        });
        nameField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) majorField.requestFocus();
        });
        majorField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) emailField.requestFocus();
        });
        emailField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) registerBtn.fire();
        });

        // 🖼 Logo
        ImageView logo = new ImageView();
        URL logoUrl = getClass().getResource("/images/ummlogo.png");
        if (logoUrl != null) {
            logo.setImage(new Image(logoUrl.toExternalForm()));
            logo.setFitWidth(120);
            logo.setPreserveRatio(true);
            logo.getStyleClass().add("image-view");
        }

        registerBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String major = majorField.getText().trim();
            String email = emailField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || id.isEmpty() || name.isEmpty()
                    || major.isEmpty() || email.isEmpty()) {
                showMessage("Please fill all fields.");
                return;
            }

            boolean exists = LibraryData.members.stream()
                    .anyMatch(m -> m.getUsername().equals(username));

            if (exists) {
                showMessage("Username already taken.");
                return;
            }

            Member newMember = new Member(username, password, id, name, major, email);
            LibraryData.members.add(newMember);
            LibraryData.users.add(newMember);
            showMessage("Registration successful.");
            showLoginPage();
        });

        backBtn.setOnAction(e -> showLoginPage());

        VBox layout = new VBox(10, title, logo, usernameField, passwordField, idField, nameField, majorField, emailField, registerBtn, backBtn);
        layout.getStyleClass().add("root");

        Scene scene = new Scene(layout, 400, 550);
        addStylesheet(scene);

        stage.setScene(scene);
        stage.show();
    }

    private void showMessage(String message) {
        Stage dialog = new Stage();
        dialog.initOwner(stage);

        Label msgLabel = new Label(message);
        msgLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #b71c1c; -fx-font-weight: bold;");

        Button okButton = new Button("OK");
        okButton.getStyleClass().add("login");
        okButton.setOnAction(e -> dialog.close());

        VBox layout = new VBox(15, msgLabel, okButton);
        layout.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-alignment: center;");
        layout.getStyleClass().add("root");

        Scene scene = new Scene(layout, 300, 150);
        addStylesheet(scene);

        dialog.setScene(scene);
        dialog.setResizable(false);
        dialog.setTitle("UMM Library");
        dialog.showAndWait();
    }

    private void addStylesheet(Scene scene) {
        URL cssUrl = getClass().getResource("/styles/login.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("⚠️ CSS file not found at /styles/login.css");
        }
    }
}
