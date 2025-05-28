package studentinfosystem.controllers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import studentinfosystem.database.DBConnection;
import studentinfosystem.views.Dashboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.layout.HBox;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        // Logo Image
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/resources/images/logo.png")));
        logo.setFitHeight(100); // Set Image Size
        logo.setPreserveRatio(true);

        // Title Text
        Label titleLabel = new Label("STUDENT INFORMATION SYSTEM");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        Label titleSubLabel = new Label("LOGIN");
        titleSubLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // GridPane for Form
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        
        loginButton.setPrefWidth(150);
        registerButton.setPrefWidth(150);

        
        Label errorMessage = new Label();

        grid.add(userLabel, 0, 0);
        grid.add(userField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passField, 1, 1);
        grid.add(errorMessage, 1, 2);
        
        HBox buttonBox = new HBox(10, loginButton, registerButton);
        buttonBox.setAlignment(Pos.CENTER); // Center buttons
        grid.add(buttonBox, 0, 3, 2, 1); // Span both columns

        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();

            int userId = authenticateUser(username, password);
            if (userId != -1) {
                new Dashboard(userId).start(new Stage());
                primaryStage.close();
            } else {
                errorMessage.setText("Invalid credentials! Try again.");
                errorMessage.setStyle("-fx-text-fill: red;");
            }
        });

        registerButton.setOnAction(e -> {
            new Register().start(new Stage());
            primaryStage.close();
        });

        // VBox Layout (Image + Title + Form)
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(logo, titleLabel, titleSubLabel, grid);
        
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // This will open the Login window in full screen
        primaryStage.show();

    }

    private int authenticateUser(String username, String password) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return -1;

        String query = "SELECT id FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
