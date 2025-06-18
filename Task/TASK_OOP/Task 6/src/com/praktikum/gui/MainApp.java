package com.praktikum.gui;

import com.praktikum.data.DataStore;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final String APP_TITLE = "Lost & Found Kampus";

    private DataStore dataStore = new DataStore();

    @Override
    public void start(Stage primaryStage) {
        try {
            LoginPane loginPane = new LoginPane(primaryStage, dataStore);
            Scene scene = new Scene(loginPane, WINDOW_WIDTH, WINDOW_HEIGHT);

            primaryStage.setTitle(APP_TITLE);
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(WINDOW_WIDTH);
            primaryStage.setMinHeight(WINDOW_HEIGHT);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Application failed to start");
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}