package studentinfosystem.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import studentinfosystem.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register {
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Register");

        // Logo Image
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/resources/images/logo.png")));
        logo.setFitHeight(100);
        logo.setPreserveRatio(true);

        // Title Text
        Label titleLabel = new Label("STUDENT INFORMATION SYSTEM");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        Label titleSubLabel = new Label("REGISTER");
        titleSubLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // GridPane layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // UI Components
        Label nameLabel = new Label("Full Name:");
        TextField nameField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        Button registerButton = new Button("Register");
        Button backButton = new Button("Back to Login");
        Label errorMessage = new Label();

        registerButton.setPrefWidth(150);
        backButton.setPrefWidth(150);

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(emailLabel, 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(userLabel, 0, 2);
        grid.add(userField, 1, 2);
        grid.add(passLabel, 0, 3);
        grid.add(passField, 1, 3);
        grid.add(errorMessage, 1, 4);

        HBox buttonBox = new HBox(10, registerButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 0, 5, 2, 1);

        registerButton.setOnAction(e -> {
            String fullName = nameField.getText();
            String email = emailField.getText();
            String username = userField.getText();
            String password = passField.getText();

            if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                errorMessage.setText("Please fill all fields.");
                errorMessage.setStyle("-fx-text-fill: red;");
                return;
            }

            if (registerUser(fullName, email, username, password)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration Successful!", ButtonType.OK);
                alert.showAndWait();
                new Login().start(new Stage());
                primaryStage.close();
            } else {
                errorMessage.setText("Registration Failed!");
                errorMessage.setStyle("-fx-text-fill: red;");
            }
        });

        backButton.setOnAction(e -> {
            new Login().start(new Stage());
            primaryStage.close();
        });

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(logo, titleLabel, titleSubLabel, grid);

       Scene scene = new Scene(root, 400, 500);
       primaryStage.setScene(scene);
       primaryStage.setMaximized(true); // This will open the Login window in full screen
       primaryStage.show();

    }

    private boolean registerUser(String fullName, String email, String username, String password) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return false;

        String query = "INSERT INTO users (full_name, email, username, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.setString(3, username);
            pstmt.setString(4, password);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
