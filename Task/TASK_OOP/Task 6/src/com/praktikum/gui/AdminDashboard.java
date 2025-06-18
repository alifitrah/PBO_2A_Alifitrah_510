package com.praktikum.gui;

import com.praktikum.data.DataStore;
import com.praktikum.data.LostItem;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminDashboard extends BorderPane {
    private DataStore dataStore;

    public AdminDashboard(Stage primaryStage, DataStore dataStore) {
        this.dataStore = dataStore;

        // Header with consistent padding
        Text welcomeText = new Text("Halo, Administrator admin");
        welcomeText.setFont(Font.font(20));

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> primaryStage.setScene(new Scene(new LoginPane(primaryStage, dataStore), 800, 600)));

        HBox header = new HBox(20, welcomeText, logoutBtn);
        header.setPadding(new Insets(15, 20, 15, 20)); // top, right, bottom, left
        header.setStyle("-fx-background-color: #f0f0f0;");
        setTop(header);

        // Main Content with TabPane
        TabPane tabPane = new TabPane();
        tabPane.setPadding(new Insets(10, 15, 10, 15)); // Inner tab pane padding

        // Items Tab
        Tab itemsTab = new Tab("Laporan Barang");
        itemsTab.setClosable(false);

        // Table with items
        TableView<LostItem> itemsTable = new TableView<>();
        itemsTable.setPadding(new Insets(5, 0, 5, 0)); // Table padding

        TableColumn<LostItem, String> itemNameCol = new TableColumn<>("Nama");
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<LostItem, String> itemLocationCol = new TableColumn<>("Lokasi");
        itemLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<LostItem, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        itemsTable.getColumns().addAll(itemNameCol, itemLocationCol, statusCol);
        itemsTable.getItems().setAll(dataStore.getLostItems());

        // Mark as Claimed controls
        TextField studentNameField = new TextField();
        studentNameField.setPromptText("Nama Mahasiswa");
        studentNameField.setPadding(new Insets(5, 5, 5, 5)); // Text field padding

        TextField nimField = new TextField();
        nimField.setPromptText("NIM");
        nimField.setPadding(new Insets(5, 5, 5, 5)); // Text field padding

        Button markClaimedBtn = new Button("Tandai Claimed");
        markClaimedBtn.setPadding(new Insets(5, 10, 5, 10)); // Button padding

        markClaimedBtn.setOnAction(e -> {
            LostItem selected = itemsTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.setStatus("Claimed");
                dataStore.updateItemStatus(selected.getName(), "Claimed");
                itemsTable.refresh();
            }
        });

        HBox markClaimedBox = new HBox(10, studentNameField, nimField, markClaimedBtn);
        markClaimedBox.setPadding(new Insets(10, 0, 10, 0)); // Control box padding

        VBox itemsContent = new VBox(15, itemsTable, markClaimedBox);
        itemsContent.setPadding(new Insets(10, 15, 10, 15)); // Content padding
        itemsTab.setContent(itemsContent);

        // Students Tab
        Tab studentsTab = new Tab("Data Mahasiswa");
        studentsTab.setClosable(false);

        tabPane.getTabs().addAll(itemsTab, studentsTab);
        setCenter(tabPane);

        // Optional: Add padding around the entire BorderPane
        this.setPadding(new Insets(5, 5, 5, 5)); // Main container padding
    }
}