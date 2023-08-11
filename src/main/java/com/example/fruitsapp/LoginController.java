package com.example.fruitsapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label SignUp;

    @FXML
    private Button button;

    @FXML
    private ImageView close;

    @FXML
    private PasswordField enterPassword;

    @FXML
    private TextField enterUsername;

    @FXML
    private Label wronglogin;

    // Variable to store the logged-in username

    public static  String logUser;



    @FXML
    void signup(MouseEvent event) throws IOException {
        // Implement the logic for signing up a new user here
        Main.changeScene(event, "SignUp");
    }

    @FXML
    void terminate(MouseEvent event) {
        // Close the application
        closeApplication();
    }

    @FXML
    void userLogin(MouseEvent event) throws IOException {
        String username = enterUsername.getText();
        String password = enterPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            wronglogin.setText("Please enter your username and password.");
            return;
        }

        boolean loginSuccessful = false;

        // We implement the logic for checking the login credentials in the database
        String DATABASE_URL = "jdbc:sqlite:fruits.sqlite";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM User WHERE Username = ? AND Password = ?")) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                loginSuccessful = true;
                // Save the logged-in username
                logUser = username;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (loginSuccessful) {
            wronglogin.setText("Success");
            // This is the logic for switching to the dashboard scene after successful login
            Main.changeScene(event, "Dash");
        } else {
            wronglogin.setText("Wrong username or password.");
        }
    }

    // Helper method to close the application
    private void closeApplication() {
        // Get the current stage and close it
        close.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
