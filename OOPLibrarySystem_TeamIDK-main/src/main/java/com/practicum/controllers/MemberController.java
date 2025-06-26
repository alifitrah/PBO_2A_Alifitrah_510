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

public class MemberController {
    private final Stage stage;
    private final Member member;

    public MemberController(Stage stage) {
        this.stage = stage;
        this.member = (Member) Session.currentUser;
    }

    public void showMemberMenu() {
        Label welcome = new Label("👋 Welcome, " + member.getFullName());
        welcome.getStyleClass().add("title");

        Button viewBooksBtn = new Button("📚 View Available Books");
        Button viewMyBorrowBtn = new Button("📖 My Borrowed Books");
        Button logoutBtn = new Button("🚪 Logout");

        viewBooksBtn.getStyleClass().add("login");
        viewMyBorrowBtn.getStyleClass().add("login");
        logoutBtn.getStyleClass().add("login");

        viewBooksBtn.setOnAction(e -> showAvailableBooks());
        viewMyBorrowBtn.setOnAction(e -> showMyBorrowedBooks());
        logoutBtn.setOnAction(e -> new LoginController(stage).showLoginPage());

        VBox menuBox = new VBox(15, welcome, viewBooksBtn, viewMyBorrowBtn, logoutBtn);
        menuBox.setPadding(new Insets(30));
        menuBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(menuBox, 500, 400);
        URL css = getClass().getResource("/styles/login.css");
        if (css != null) scene.getStylesheets().add(css.toExternalForm());

        stage.setScene(scene);
        stage.setTitle("UMM - Member Dashboard");
        stage.setResizable(true);
        stage.show();
    }

    private void showAvailableBooks() {
        TableView<Book> tableView = createBookTable();
        List<Book> availableBooks = LibraryData.books.stream().filter(Book::isAvailable).toList();
        tableView.setItems(FXCollections.observableArrayList(availableBooks));
        tableView.setPlaceholder(new Label("No available books."));

        Button borrowBtn = new Button("Borrow");
        Button backBtn = new Button("← Back");

        borrowBtn.getStyleClass().add("login");
        backBtn.getStyleClass().add("back-button");

        borrowBtn.setOnAction(e -> {
            Book selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.borrowBook();
                member.borrowBook(selected.getIsbn());
                showAlert("📥 Book borrowed successfully.");
                showAvailableBooks();
            } else {
                showAlert("⚠️ Please select a book to borrow.");
            }
        });

        tableView.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) borrowBtn.fire();
        });

        backBtn.setOnAction(e -> showMemberMenu());

        VBox layout = new VBox(10, tableView, borrowBtn, backBtn);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("book-view");

        Scene scene = new Scene(layout, 700, 500);
        applyStyle(scene);
        stage.setScene(scene);
    }

    private void showMyBorrowedBooks() {
        TableView<Book> tableView = createBookTable();
        List<Book> borrowed = member.getBorrowedIsbns().stream()
                .map(isbn -> LibraryData.books.stream()
                        .filter(book -> book.getIsbn().equals(isbn))
                        .findFirst().orElse(null))
                .filter(book -> book != null)
                .toList();

        tableView.setItems(FXCollections.observableArrayList(borrowed));
        tableView.setPlaceholder(new Label("You haven't borrowed any books."));

        Button returnBtn = new Button("Return");
        Button backBtn = new Button("← Back");

        returnBtn.getStyleClass().add("login");
        backBtn.getStyleClass().add("back-button");

        returnBtn.setOnAction(e -> {
            Book selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.returnBook();
                member.returnBook(selected.getIsbn());
                showAlert("✅ Book returned.");
                showMyBorrowedBooks();
            } else {
                showAlert("⚠️ Please select a book to return.");
            }
        });

        tableView.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) returnBtn.fire();
        });

        backBtn.setOnAction(e -> showMemberMenu());

        VBox layout = new VBox(10, tableView, returnBtn, backBtn);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("book-view");

        Scene scene = new Scene(layout, 700, 500);
        applyStyle(scene);
        stage.setScene(scene);
    }

    private TableView<Book> createBookTable() {
        TableView<Book> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getStyleClass().add("styled-table");

        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIsbn()));

        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));

        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuthor()));

        table.getColumns().addAll(isbnCol, titleCol, authorCol);
        return table;
    }

    private void applyStyle(Scene scene) {
        URL css = getClass().getResource("/styles/login.css");
        if (css != null) scene.getStylesheets().add(css.toExternalForm());
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
