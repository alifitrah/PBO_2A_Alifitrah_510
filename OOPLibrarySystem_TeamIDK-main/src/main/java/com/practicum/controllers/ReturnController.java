package com.practicum.controllers;

import com.practicum.models.Book;
import com.practicum.models.Member;
import com.practicum.storage.LibraryData;
import com.practicum.utils.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class ReturnController {
    private final Stage stage;

    public ReturnController(Stage stage) {
        this.stage = stage;
    }

    public void showReturnMenu() {
        if (!(Session.currentUser instanceof Member member)) {
            showAlert("Only members can return books.");
            return;
        }

        // 📚 Table setup
        TableView<Book> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getStyleClass().add("styled-table");
        VBox.setVgrow(tableView, Priority.ALWAYS);

        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIsbn()));

        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));

        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuthor()));

        tableView.getColumns().addAll(isbnCol, titleCol, authorCol);

        // 📦 Populate table with borrowed books
        List<Book> borrowedBooks = LibraryData.books.stream()
                .filter(book -> member.getBorrowedIsbns().contains(book.getIsbn()))
                .collect(Collectors.toList());

        tableView.setItems(FXCollections.observableArrayList(borrowedBooks));
        tableView.setPlaceholder(new Label("You haven't borrowed any books."));

        // 🔘 Buttons
        Button returnBtn = new Button("Return");
        returnBtn.getStyleClass().add("login");

        Button backBtn = new Button("← Back");
        backBtn.getStyleClass().add("back-button");

        returnBtn.setOnAction(e -> {
            Book selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.returnBook();
                member.returnBook(selected.getIsbn());
                showAlert("✅ Book returned successfully.");
                showReturnMenu(); // Refresh
            } else {
                showAlert("⚠️ Please select a book to return.");
            }
        });

        tableView.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) returnBtn.fire();
        });

        backBtn.setOnAction(e -> new MemberController(stage).showMemberMenu());

        // 📐 Layout
        VBox layout = new VBox(15, tableView, returnBtn, backBtn);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("book-view");

        // 🎨 Scene
        Scene scene = new Scene(layout, 700, 500);
        URL css = getClass().getResource("/styles/login.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        stage.setScene(scene);
        stage.setTitle("UMM - Return Book");
        stage.setResizable(true);
        stage.show();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
