package com.example.fruitsapp;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class About {

    @FXML
    private ImageView About;

    @FXML
    private ImageView Home;

    @FXML
    private ImageView Logout;

    @FXML
    private ImageView Reminders;

    @FXML
    private ImageView close;

    @FXML
    private void initialize() {
        // Set tooltips for each ImageView
        setTooltip(About, "About");
        setTooltip(Home, "Home");
        setTooltip(Logout, "Logout");
        setTooltip(Reminders, "Reminders");
    }

    @FXML
    private void setTooltip(ImageView imageView, String tooltipText) {
        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(imageView, tooltip);
    }

    @FXML
    void moveAbout(MouseEvent event) throws IOException {
        Main.changeScene(event, "About");
    }


    @FXML
    void movePreferences(MouseEvent event) throws IOException {
        Main.changeScene(event, "Preferences");
    }

    @FXML
    void moveReminders(MouseEvent event) throws IOException {
        Main.changeScene(event, "Reminders");
    }

    @FXML
    public void getFruit(MouseEvent mouseEvent) {
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

    @FXML
    public void moveAccount(MouseEvent mouseEvent) throws IOException {
        Main.changeScene(mouseEvent, "Profile");
    }
}
