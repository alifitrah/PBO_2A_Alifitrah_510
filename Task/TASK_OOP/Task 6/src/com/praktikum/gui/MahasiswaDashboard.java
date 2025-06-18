package com.praktikum.gui;

import com.praktikum.data.DataStore;
import com.praktikum.data.LostItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MahasiswaDashboard extends BorderPane {
    private DataStore dataStore;
    private TableView<LostItem> reportsTable = new TableView<>();
    private String currentUser;

    public MahasiswaDashboard(Stage primaryStage, String username, DataStore dataStore) {
        this.currentUser = username;
        this.dataStore = dataStore;

        // Header
        Text welcomeText = new Text("Selamat datang, " + username);
        welcomeText.setFont(Font.font(20));

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> primaryStage.setScene(new Scene(new LoginPane(primaryStage, dataStore), 800, 600)));

        HBox header = new HBox(welcomeText, logoutBtn);
        header.setSpacing(20);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #f0f0f0;");
        setTop(header);

        // Main Content
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));

        Text reportTitle = new Text("Laporkan Barang Hilang/Temuan");
        reportTitle.setFont(Font.font(18));

        // Report form
        TextField itemName = new TextField();
        itemName.setPromptText("Nama Barang");
        TextArea description = new TextArea();
        description.setPromptText("Deskripsi");
        TextField location = new TextField();
        location.setPromptText("Lokasi");
        Button reportBtn = new Button("Laporkan");

        initializeReportsTable();

        reportBtn.setOnAction(e -> {
            if (itemName.getText().isEmpty() || location.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Data tidak lengkap", "Nama barang dan lokasi harus diisi");
                return;
            }

            LostItem newItem = new LostItem(
                    itemName.getText(),
                    description.getText(),
                    location.getText(),
                    "Reported",
                    currentUser
            );

            dataStore.addLostItem(newItem);
            refreshReportsTable();

            itemName.clear();
            description.clear();
            location.clear();

            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Laporan berhasil disimpan");
        });

        VBox reportForm = new VBox(10, itemName, description, location, reportBtn);
        reportForm.setPadding(new Insets(10));
        reportForm.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5;");

        // Reports list
        Text yourReports = new Text("Daftar Laporan Anda");
        yourReports.setFont(Font.font(16));

        mainContent.getChildren().addAll(reportTitle, reportForm, yourReports, reportsTable);
        setCenter(mainContent);
    }

    private void initializeReportsTable() {
        TableColumn<LostItem, String> nameCol = new TableColumn<>("Nama");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<LostItem, String> locationCol = new TableColumn<>("Lokasi");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));

        reportsTable.getColumns().addAll(nameCol, locationCol);
        refreshReportsTable();
    }

    private void refreshReportsTable() {
        reportsTable.getItems().setAll(dataStore.getLostItemsByUser(currentUser));
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}