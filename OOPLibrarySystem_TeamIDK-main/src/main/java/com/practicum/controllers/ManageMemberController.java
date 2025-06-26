package com.practicum.controllers;

import com.practicum.models.Member;
import com.practicum.storage.LibraryData;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.Insets;

public class ManageMemberController {
    private final Stage stage;
    private Label statusLabel;

    public ManageMemberController(Stage stage) {
        this.stage = stage;
    }

    public void showManageMembers() {
        // 🔙 Back Button styled
        Button backBtn = new Button("←");
        backBtn.getStyleClass().add("back-button");
        backBtn.setOnAction(e -> new AdminController(stage).showAdminMenu());

        // 🔍 Search field
        TextField searchField = new TextField();
        searchField.setPromptText("Search by ID, Name, Major, Email, Username");
        searchField.setPrefWidth(400);
        searchField.getStyleClass().add("search-bar");

        HBox topBox = new HBox(10, backBtn, searchField);
        topBox.setStyle("-fx-padding: 10;");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        // 📋 Table setup
        TableView<Member> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getStyleClass().add("styled-table");
        VBox.setVgrow(tableView, Priority.ALWAYS);

        TableColumn<Member, String> idCol = new TableColumn<>("ID");
        TableColumn<Member, String> nameCol = new TableColumn<>("Name");
        TableColumn<Member, String> majorCol = new TableColumn<>("Major");
        TableColumn<Member, String> emailCol = new TableColumn<>("Email");
        TableColumn<Member, String> usernameCol = new TableColumn<>("Username");
        TableColumn<Member, String> passwordCol = new TableColumn<>("Password");

        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFullName()));
        majorCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMajor()));
        emailCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        usernameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));
        passwordCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPassword()));

        tableView.getColumns().addAll(idCol, nameCol, majorCol, emailCol, usernameCol, passwordCol);

        FilteredList<Member> filteredList = new FilteredList<>(FXCollections.observableList(LibraryData.members), m -> true);
        searchField.textProperty().addListener((obs, oldText, newText) -> {
            String lower = newText.toLowerCase();
            filteredList.setPredicate(m ->
                    m.getId().toLowerCase().contains(lower) ||
                            m.getFullName().toLowerCase().contains(lower) ||
                            m.getMajor().toLowerCase().contains(lower) ||
                            m.getEmail().toLowerCase().contains(lower) ||
                            m.getUsername().toLowerCase().contains(lower));
        });
        tableView.setItems(filteredList);
        tableView.setPlaceholder(new Label("No members available."));

        // 📝 Input fields
        TextField idField = new TextField(); idField.setPromptText("ID");
        TextField nameField = new TextField(); nameField.setPromptText("Full Name");
        TextField majorField = new TextField(); majorField.setPromptText("Major");
        TextField emailField = new TextField(); emailField.setPromptText("Email");
        TextField usernameField = new TextField(); usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField(); passwordField.setPromptText("Password");
        Button editBtn = new Button("Edit");
        Button deleteBtn = new Button("Delete");
        editBtn.getStyleClass().add("login");
        deleteBtn.getStyleClass().add("login");
        idField.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) nameField.requestFocus(); });
        nameField.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) majorField.requestFocus(); });
        majorField.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) emailField.requestFocus(); });
        emailField.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) usernameField.requestFocus(); });
        usernameField.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) passwordField.requestFocus(); });
        passwordField.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) editBtn.fire(); });

        statusLabel = new Label();
        statusLabel.getStyleClass().add("status-label");

        // ✏️ Edit action
        editBtn.setOnAction(e -> {
            Member selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                String major = majorField.getText().trim();
                String email = emailField.getText().trim();
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();

                if (id.isEmpty() || name.isEmpty() || major.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    setStatus("⚠️ All fields are required.");
                    return;
                }

                selected.setId(id);
                selected.setFullName(name);
                selected.setMajor(major);
                selected.setEmail(email);
                selected.setUsername(username);
                selected.setPassword(password);
                tableView.refresh();
                setStatus("✅ Member updated.");
            } else {
                setStatus("⚠️ Please select a member to edit.");
            }
        });

        // ❌ Delete action
        deleteBtn.setOnAction(e -> {
            Member selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                tableView.getSelectionModel().clearSelection();
                PauseTransition pause = new PauseTransition(Duration.millis(100));
                pause.setOnFinished(event -> {
                    LibraryData.members.removeIf(m -> m.getUsername().equals(selected.getUsername()));
                    LibraryData.users.removeIf(u -> u instanceof Member && ((Member) u).getUsername().equals(selected.getUsername()));
                    tableView.setItems(FXCollections.observableList(LibraryData.members));
                    clearFields(idField, nameField, majorField, emailField, usernameField, passwordField);
                    setStatus("🗑️ Member deleted.");
                });
                pause.play();
            } else {
                setStatus("⚠️ Please select a member to delete.");
            }
        });

        // Table row selection
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                idField.setText(newSel.getId());
                nameField.setText(newSel.getFullName());
                majorField.setText(newSel.getMajor());
                emailField.setText(newSel.getEmail());
                usernameField.setText(newSel.getUsername());
                passwordField.setText(newSel.getPassword());
            }
        });

        VBox inputBox = new VBox(6, idField, nameField, majorField, emailField, usernameField, passwordField, editBtn, deleteBtn);
        inputBox.setMaxWidth(Double.MAX_VALUE);

        VBox layout = new VBox(12, topBox, tableView, inputBox, statusLabel);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("root");

        Scene scene = new Scene(layout, 850, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("UMM - Manage Members");
        stage.setResizable(true);
        stage.show();
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) field.clear();
    }

    private void setStatus(String message) {
        statusLabel.setText(message);
    }
}
