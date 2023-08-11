package com.example.fruitsapp;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import io.github.gleidson28.GNAvatarView;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

import static com.example.fruitsapp.LoginController.logUser;

public class ProfileController {

    @FXML
    private ImageView About;

    @FXML
    private ImageView Account;

    @FXML
    private ImageView DeleteImage;

    @FXML
    private Button Edit;

    @FXML
    private ImageView Editimage;

    @FXML
    private ImageView Home;

    @FXML
    private ImageView Logout;

    @FXML
    private ImageView Reminders;

    @FXML
    private GNAvatarView avatarView;

    @FXML
    private ImageView close;

    @FXML
    private ImageView Enable;

    @FXML
    private TextField enterFirstname;

    @FXML
    private TextField enterLastname;

    @FXML
    private TextField enterPassword;

    @FXML
    private TextField enterUsername;

    @FXML
    private ImageView maximize;

    @FXML
    private ImageView minimize;

    private String loggedInUsername;

    @FXML
    private String getUserFirstName() {
        return enterFirstname.getText();
    }

    @FXML
    private String getUserLastName() {
        return enterLastname.getText();
    }

    @FXML
    private String getUserPassword() {
        return enterPassword.getText();
    }

    @FXML
    public void initialize() throws SQLException {
        setTooltip(About, "About");
        setTooltip(Home, "Home");
        setTooltip(Logout, "Logout");
        setTooltip(Reminders, "Reminders");
        displayUserData();
        DBConnectors.GetUserinfo(enterFirstname, enterLastname, enterUsername, enterPassword, logUser);
        System.out.println(logUser);
    }

    @FXML
    private void setTooltip(ImageView imageView, String tooltipText) {
        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(imageView, tooltip);
    }

    @FXML
    public void setLoggedInUsername(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
        System.out.println("setLoggedInUsername called. loggedInUsername: " + loggedInUsername);
        displayUserData();
    }


    @FXML
    void displayUserData() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:fruits.sqlite")) {
            String query = "SELECT Firstname, Lastname, Password FROM User WHERE Username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, logUser);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("Firstname");
                String lastName = rs.getString("Lastname");
                String password = rs.getString("Password");

                enterFirstname.setText(firstName);
                enterLastname.setText(lastName);
                enterPassword.setText(password);
                enterUsername.setText(logUser);

                System.out.println("User data displayed: " + firstName + ", " + lastName + ", " + password + ", " + loggedInUsername);
            } else {
                System.out.println("User data not found for username: " + loggedInUsername);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void makeVisible(MouseEvent event) {
        enterFirstname.setDisable(!enterFirstname.isDisabled());
        enterLastname.setDisable(!enterLastname.isDisabled());
        enterUsername.setDisable(!enterUsername.isDisabled());
        enterPassword.setDisable(!enterPassword.isDisabled());
    }




    @FXML
    void Editinfo(MouseEvent event) {
        String newUsername = enterUsername.getText();
        String newFirstName = enterFirstname.getText();
        String newLastName = enterLastname.getText();
        String newPassword = enterPassword.getText();

        if (newUsername.isEmpty() || newFirstName.isEmpty() || newLastName.isEmpty() || newPassword.isEmpty()) {
            showInfoAlert("No changes made.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:fruits.sqlite")) {
            String updateQuery = "UPDATE User SET Username = ?, Firstname = ?, Lastname = ?, Password = ? WHERE Username = ?";
            PreparedStatement ps = conn.prepareStatement(updateQuery);
            ps.setString(1, newUsername);
            ps.setString(2, newFirstName);
            ps.setString(3, newLastName);
            ps.setString(4, newPassword);
            ps.setString(5, logUser);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                logUser = newUsername;
                showInfoAlert("User information updated successfully.");
            } else {
                showInfoAlert("Failed to update user information.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void moveAbout(MouseEvent event) throws IOException {
        Main.changeScene(event, "About");
    }

    @FXML
    void moveAccount(MouseEvent event) throws IOException {
        Main.changeScene(event, "Profile");
    }

    @FXML
    void moveReminders(MouseEvent event) throws IOException {
        Main.changeScene(event, "Reminders");
    }

    @FXML
    public void moveDash(MouseEvent event) throws IOException {
        Main.changeScene(event, "Dash");
    }

    @FXML
    public void Minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void Maximize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }

    @FXML
    public void terminate(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void logout(MouseEvent event) throws IOException {
        Main.changeScene(event, "login");
    }

    private static final String DEFAULT_IMAGE_PATH = "/com/example/fruitsapp/Images/account.png";

    @FXML
    public void deleteimage(MouseEvent mouseEvent) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the image?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();

        if (confirmation.getResult() == ButtonType.YES) {
            // Delete the image
            avatarView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_IMAGE_PATH))));
            avatarView.setEffect(null); // Clear any transformation effects
            Alert message = new Alert(Alert.AlertType.INFORMATION, "Image deleted successfully.", ButtonType.OK);
            message.showAndWait();
        }
    }

    @FXML
    public void editimage(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Show the file chooser dialog and get the selected file
        File selectedFile = fileChooser.showOpenDialog(avatarView.getScene().getWindow());

        if (selectedFile != null) {
            // Set the selected image to the avatar view
            Image image = new Image(selectedFile.toURI().toString());
            avatarView.setImage(image);

            // Show a message to inform the user that the image has been updated
            Alert message = new Alert(Alert.AlertType.INFORMATION, "Image updated successfully.", ButtonType.OK);
            message.showAndWait();
        }
    }


}