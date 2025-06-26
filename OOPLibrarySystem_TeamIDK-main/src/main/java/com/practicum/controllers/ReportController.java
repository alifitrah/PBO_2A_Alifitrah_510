package com.practicum.controllers;

import com.practicum.models.Book;
import com.practicum.models.Member;
import com.practicum.storage.LibraryData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;

public class ReportController {
    private final Stage stage;

    public ReportController(Stage stage) {
        this.stage = stage;
    }

    public void showReportMenu() {
        // 🔙 Back button
        Button backBtn = new Button("←");
        backBtn.getStyleClass().add("back-button");
        backBtn.setOnAction(e -> new AdminController(stage).showAdminMenu());

        Label titleLabel = new Label("Borrowed Book Report");
        titleLabel.getStyleClass().add("title");

        HBox header = new HBox(10, titleLabel, backBtn);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10));
        HBox.setHgrow(titleLabel, Priority.ALWAYS);

        // 📊 TableView
        TableView<Book> tableView = new TableView<>();
        tableView.getStyleClass().add("styled-table");
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIsbn()));

        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));

        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuthor()));

        TableColumn<Book, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().isAvailable() ? "Available" : "Borrowed"
        ));

        TableColumn<Book, String> borrowerCol = new TableColumn<>("Borrowed By");
        borrowerCol.setCellValueFactory(data -> {
            if (data.getValue().isAvailable()) {
                return new SimpleStringProperty("-");
            } else {
                // Find member who borrowed the book
                for (Member member : LibraryData.members) {
                    if (member.getBorrowedIsbns().contains(data.getValue().getIsbn())) {
                        return new SimpleStringProperty(member.getFullName());
                    }
                }
                return new SimpleStringProperty("Unknown");
            }
        });

        tableView.getColumns().addAll(isbnCol, titleCol, authorCol, statusCol, borrowerCol);
        tableView.setItems(FXCollections.observableArrayList(LibraryData.books));
        tableView.setPlaceholder(new Label("No books in the library."));

        // 🖼 Logo
        ImageView logo = new ImageView();
        URL logoUrl = getClass().getResource("/images/ummlogo.png");
        if (logoUrl != null) {
            logo.setImage(new Image(logoUrl.toExternalForm()));
            logo.setFitWidth(100);
            logo.setPreserveRatio(true);
        }

        HBox logoBox = new HBox(logo);
        logoBox.setAlignment(Pos.CENTER_RIGHT);

        VBox layout = new VBox(15, header, tableView, logoBox);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("book-view");

        Scene scene = new Scene(layout, 850, 600);
        URL cssUrl = getClass().getResource("/styles/login.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }

        stage.setScene(scene);
        stage.setTitle("UMM - Borrowing Report");
        stage.setResizable(true);
        stage.show();
    }
}
