package com.practicum.controllers;

import com.practicum.models.Book;
import com.practicum.models.Member;
import com.practicum.storage.LibraryData;
import com.practicum.utils.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;
import javafx.scene.input.KeyCode;

public class BorrowController {
    private Stage stage;

    public BorrowController(Stage stage) {
        this.stage = stage;
    }

    public void showBorrowMenu() {
        TableView<Book> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIsbn()));

        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));

        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuthor()));

        tableView.getColumns().addAll(isbnCol, titleCol, authorCol);
        tableView.getItems().addAll(LibraryData.books.stream().filter(Book::isAvailable).toList());

        Button borrowBtn = new Button("Borrow");
        Button backBtn = new Button("Back");

        borrowBtn.setOnAction(e -> {
            Book selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                // Update book availability
                selected.borrowBook();

                // Update current user's borrowed list
                if (Session.currentUser instanceof Member member) {
                    member.borrowBook(selected.getIsbn());
                }

                showAlert("Book borrowed successfully.");
                showBorrowMenu(); // Refresh table
            } else {
                showAlert("Please select a book.");
            }
        });

        backBtn.setOnAction(e -> new MemberController(stage).showMemberMenu());

        tableView.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                Book selected = tableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    borrowBtn.fire();
                }
            }
        });

        VBox vbox = new VBox(10, tableView, borrowBtn, backBtn);
        vbox.setStyle("-fx-padding: 20;");
        VBox.setVgrow(tableView, Priority.ALWAYS);
        stage.setScene(new Scene(vbox, 500, 400));
        stage.setResizable(true);
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
