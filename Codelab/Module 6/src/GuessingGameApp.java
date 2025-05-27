import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Random;

public class GuessingGameApp extends Application {

    private int targetNumber;
    private int attempts;
    private final Random random = new Random();

    @Override
    public void start(Stage primaryStage) {
        // Title label
        Label titleLabel = new Label("🎯 Tebak Angka 1–100");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Subtitle
        Label instructionLabel = new Label("Masukkan tebakanmu!");
        instructionLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

        // Input field
        TextField guessField = new TextField();
        guessField.setPrefWidth(150);

        // Guess button with icon
        Button guessButton = new Button("💡 Coba Tebak!");
        guessButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        // HBox for input
        HBox inputBox = new HBox(10, guessField, guessButton);
        inputBox.setAlignment(Pos.CENTER);

        // Feedback label
        Label feedbackLabel = new Label("");
        feedbackLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        feedbackLabel.setTextFill(Color.DARKRED);

        // Attempts label
        Label attemptsLabel = new Label("Jumlah percobaan: 0");
        attemptsLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        // VBox layout
        VBox root = new VBox(15, titleLabel, instructionLabel, inputBox, feedbackLabel, attemptsLabel);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #E6F2FF;");

        // Logic
        generateNewTarget();

        guessButton.setOnAction(e -> {
            if (guessButton.getText().equals("🔁 Main Lagi")) {
                generateNewTarget();
                attempts = 0;
                feedbackLabel.setText("");
                attemptsLabel.setText("Jumlah percobaan: 0");
                guessButton.setText("💡 Coba Tebak!");
                guessField.clear();
                return;
            }

            String input = guessField.getText().trim();
            try {
                int guess = Integer.parseInt(input);
                attempts++;
                attemptsLabel.setText("Jumlah percobaan: " + attempts);

                if (guess < targetNumber) {
                    feedbackLabel.setText("Terlalu kecil!");
                } else if (guess > targetNumber) {
                    feedbackLabel.setText("Terlalu besar!");
                } else {
                    feedbackLabel.setText("Tebakan benar!");
                    guessButton.setText("🔁 Main Lagi");
                }
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Masukkan angka yang valid!");
            }
            guessField.clear();
        });

        Scene scene = new Scene(root, 400, 250);
        primaryStage.setTitle("Tebak Angka Advance");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void generateNewTarget() {
        targetNumber = random.nextInt(100) + 1;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
