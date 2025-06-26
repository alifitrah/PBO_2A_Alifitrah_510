package com.practicum.controllers;

import com.practicum.models.Book;
import com.practicum.storage.LibraryData;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

public class BookController {

    private final Stage stage;
    private final Label statusLabel = new Label();

    public BookController(Stage stage) {
        this.stage = stage;
    }

    public void showBookMenu() {
        // 🔙 Back button
        Button backBtn = new Button("←");
        backBtn.getStyleClass().add("back-button");
        backBtn.setOnAction(e -> new AdminController(stage).showAdminMenu());

        // 🔍 Search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search by ISBN, Title, or Author");
        searchField.setPrefWidth(500);
        searchField.getStyleClass().add("search-bar");

        HBox topBar = new HBox(10, backBtn, searchField);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));
        HBox.setHgrow(searchField, Priority.ALWAYS);

        // 📚 TableView
        TableView<Book> tableView = new TableView<>();
        tableView.getStyleClass().add("styled-table");
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        TableColumn<Book, String> statusCol = new TableColumn<>("Status");

        isbnCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIsbn()));
        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        authorCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuthor()));
        statusCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().isAvailable() ? "Available" : "Borrowed"
        ));

        tableView.getColumns().addAll(isbnCol, titleCol, authorCol, statusCol);

        // 🔎 Filtered list
        FilteredList<Book> filteredBooks = new FilteredList<>(
                FXCollections.observableList(LibraryData.books), b -> true
        );

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String lower = newVal.toLowerCase();
            filteredBooks.setPredicate(book ->
                    book.getIsbn().toLowerCase().contains(lower) ||
                            book.getTitle().toLowerCase().contains(lower) ||
                            book.getAuthor().toLowerCase().contains(lower)
            );
        });

        tableView.setItems(filteredBooks);
        tableView.setPlaceholder(new Label("No books found."));

        // 📖 Input form
        TextField isbnField = new TextField(); isbnField.setPromptText("ISBN");
        TextField titleField = new TextField(); titleField.setPromptText("Title");
        TextField authorField = new TextField(); authorField.setPromptText("Author");

        isbnField.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) titleField.requestFocus(); });
        titleField.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) authorField.requestFocus(); });
        authorField.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) addBook(isbnField, titleField, authorField, tableView); });

        // ➕ Add & 🗑 Delete
        Button addBtn = new Button("Add Book");
        addBtn.getStyleClass().add("login");
        addBtn.setOnAction(e -> addBook(isbnField, titleField, authorField, tableView));

        Button deleteBtn = new Button("Delete Book");
        deleteBtn.getStyleClass().add("login");
        deleteBtn.getStyleClass().add("delete-button");
        deleteBtn.setOnAction(e -> deleteBook(tableView, isbnField, titleField, authorField));

        HBox buttonBox = new HBox(10, addBtn, deleteBtn);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        VBox inputBox = new VBox(8, isbnField, titleField, authorField, buttonBox);
        inputBox.setPadding(new Insets(10));

        // 🖼 UMM logo
        ImageView logo = new ImageView();
        URL logoUrl = getClass().getResource("/images/ummlogo.png");
        if (logoUrl != null) {
            logo.setImage(new Image(logoUrl.toExternalForm()));
            logo.setFitWidth(100);
            logo.setPreserveRatio(true);
        }

        HBox logoBox = new HBox(logo);
        logoBox.setAlignment(Pos.CENTER_RIGHT);

        // ✅ Status label
        statusLabel.setStyle("-fx-text-fill: darkred; -fx-font-style: italic;");
        VBox layout = new VBox(10, topBar, tableView, inputBox, statusLabel, logoBox);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("book-view");

        // 🖥 Scene
        Scene scene = new Scene(layout, 850, 600);
        URL cssUrl = getClass().getResource("/styles/login.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }

        stage.setScene(scene);
        stage.setTitle("UMM - Book Management");
        stage.setResizable(true);
        stage.show();
    }

    private void addBook(TextField isbnField, TextField titleField, TextField authorField, TableView<Book> table) {
        String isbn = isbnField.getText().trim();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();

        if (isbn.isEmpty() || title.isEmpty() || author.isEmpty()) {
            setStatus("⚠️ All fields are required.");
            return;
        }

        LibraryData.books.add(new Book(isbn, title, author, ""));
        isbnField.clear();
        titleField.clear();
        authorField.clear();
        table.refresh();
        setStatus("✅ Book added successfully.");
    }

    private void deleteBook(TableView<Book> table, TextField isbnField, TextField titleField, TextField authorField) {
        Book selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            table.getSelectionModel().clearSelection();
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            pause.setOnFinished(event -> {
                LibraryData.books.removeIf(b -> b.getIsbn().equals(selected.getIsbn()));
                table.setItems(FXCollections.observableList(LibraryData.books));
                clearFields(isbnField, titleField, authorField);
                setStatus("🗑️ Book deleted.");
            });
            pause.play();
        } else {
            setStatus("⚠️ Please select a book to delete.");
        }
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private void setStatus(String msg) {
        statusLabel.setText(msg);
    }
}
