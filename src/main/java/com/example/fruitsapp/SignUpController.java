package com.example.fruitsapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SignUpController {

    @FXML
    private Label wrongSignup;

    @FXML
    private Label Back;

    @FXML
    private ImageView close;

    @FXML
    private TextField enterFirstName;

    @FXML
    private TextField enterLastName;

    @FXML
    private PasswordField enterPassword;

    @FXML
    private TextField enterUsername;

    @FXML
    private Button signup;

    private static final String DB_URL = "jdbc:sqlite:fruits.sqlite";

    @FXML
    void Back(MouseEvent event) throws IOException {
        Main.changeScene(event, "login");
    }

    public void initialize(URL location, ResourceBundle resources) {

        close.setOnMouseClicked(this::close);
    }

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    void moveFruits(MouseEvent event) {
        String firstName = enterFirstName.getText();
        String lastName = enterLastName.getText();
        String password = enterPassword.getText();
        String username = enterUsername.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || username.isEmpty()) {
            wrongSignup.setText("Please fill in all fields.");
            return;
        }

        if (isUsernameExists(username)) {
            wrongSignup.setText("Username already exists. Please choose a different username.");
            return;
        }

        // Save user data to the SQLite database
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO User (Firstname, Lastname, Username, Password) VALUES (?, ?, ?, ?)")) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.executeUpdate();

            System.out.println("Signup successful!");
            Main.changeScene(event, "login");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isUsernameExists(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Check if the username exists in the database
            String query = "SELECT Username FROM User WHERE Username = '" + username + "'";
            try (ResultSet rs = stmt.executeQuery(query)) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
