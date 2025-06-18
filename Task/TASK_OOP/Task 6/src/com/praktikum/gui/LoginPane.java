package com.praktikum.gui;

import com.praktikum.data.DataStore;
import com.praktikum.users.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPane extends VBox {
    private DataStore dataStore;

    public LoginPane(Stage primaryStage, DataStore dataStore) {
        this.dataStore = dataStore;
        setSpacing(20);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);

        Text title = new Text("Lost & Found Kampus");
        title.setFont(Font.font(24));

        Text subtitle = new Text("Login System Lost & Found");
        subtitle.setFont(Font.font(18));

        ToggleGroup userTypeGroup = new ToggleGroup();
        RadioButton mahasiswaBtn = new RadioButton("Mahasiswa");
        RadioButton adminBtn = new RadioButton("Admin");
        mahasiswaBtn.setToggleGroup(userTypeGroup);
        adminBtn.setToggleGroup(userTypeGroup);
        mahasiswaBtn.setSelected(true);

        HBox radioBox = new HBox(10, mahasiswaBtn, adminBtn);
        radioBox.setAlignment(Pos.CENTER);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        loginBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean isAdmin = adminBtn.isSelected();

            User user = dataStore.authenticate(username, password, isAdmin);
            if (user != null) {
                if (isAdmin) {
                    primaryStage.setScene(new Scene(new AdminDashboard(primaryStage, dataStore), 800, 600));
                } else {
                    primaryStage.setScene(new Scene(new MahasiswaDashboard(primaryStage, username, dataStore), 800, 600));
                }
            } else {
                errorLabel.setText("Login gagal, periksa kredensial.");
            }
        });

        getChildren().addAll(title, subtitle, radioBox,
                usernameField, passwordField, loginBtn, errorLabel);
    }
}