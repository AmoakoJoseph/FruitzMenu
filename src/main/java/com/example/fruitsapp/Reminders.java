package com.example.fruitsapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

public class Reminders {

    private ObservableList<Reminder> remindersList = FXCollections.observableArrayList();

    @FXML
    private ImageView About;

    @FXML
    private ImageView Home;

    @FXML
    private ImageView Logout;

    @FXML
    private ImageView Reminders;

    @FXML
    private TextArea reminderTextArea;


    @FXML
    private ImageView close;

    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private Button delete;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private ImageView maximize;

    @FXML
    private ImageView minimize;

    @FXML
    private Button reminder;

    @FXML
    private ImageView Account;

    @FXML
    private DatePicker reminderDatePicker;

    @FXML
    private TableView<Reminder> reminderTable;

    @FXML
    public void initialize() {
        // Set tooltips for each ImageView
        setTooltip(About, "About");
        setTooltip(Home, "Home");
        setTooltip(Logout, "Logout");
        setTooltip(Reminders, "Reminders");

        checkAndDisplayRemindersForToday();

        remindersList = FXCollections.observableArrayList();

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Load data from reminders.txt and add it to remindersList
        loadRemindersFromDatabase();

        // Set the items of the TableView to the remindersList
        reminderTable.setItems(remindersList);
    }

    private void loadRemindersFromDatabase() {
        try {
            List<Reminder> remindersFromDatabase = DBConnectors.getAllReminders();
            remindersList.addAll(remindersFromDatabase);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error: Failed to load reminders from the database.");
        }
    }



    @FXML
    private void addReminder(LocalDate date, String description) {
        if (date != null && !description.isEmpty()) {
            Reminder newReminder = new Reminder(date, description);
            remindersList.add(newReminder);
            reminderTextArea.clear();
            reminderDatePicker.setValue(null);
        } else {
            System.err.println("Error: Please enter both date and description.");
        }
    }


    @FXML
    public void addreminder(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            LocalDate date = reminderDatePicker.getValue();
            String description = reminderTextArea.getText().trim();

            if (date != null && !description.isEmpty()) {
                try {
                    DBConnectors.saveReminder(description, date);
                    Reminder newReminder = new Reminder(date, description);
                    remindersList.add(newReminder);
                    reminderTextArea.clear();
                    reminderDatePicker.setValue(null);

                } catch (SQLException e) {
                    e.printStackTrace();
                    System.err.println("Error: Failed to save the reminder to the database.");
                }
            } else {
                System.err.println("Error: Please enter both date and description.");
            }
        }
    }


    @FXML
    public void deletereminder(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            Reminder selectedReminder = reminderTable.getSelectionModel().getSelectedItem();
            if (selectedReminder != null) {
                remindersList.remove(selectedReminder);
                reminderTable.getItems().remove(selectedReminder);
                try {
                    DBConnectors.deleteReminder(selectedReminder);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setTooltip(ImageView imageView, String tooltipText) {
        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(imageView, tooltip);
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

    public void logout(MouseEvent event) throws IOException {
        Main.changeScene(event, "login");
    }

    private void checkAndDisplayRemindersForToday() {
        LocalDate today = LocalDate.now();

        List<Reminder> remindersForToday = null;
        try {
            remindersForToday = DBConnectors.getRemindersForDate(today);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (remindersForToday != null && !remindersForToday.isEmpty()) {
            StringJoiner joiner = new StringJoiner("\n");
            joiner.add("You have the following reminders for today:");
            for (Reminder reminder : remindersForToday) {
                joiner.add("- " + reminder.getDescription());
            }
            String message = joiner.toString();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Today's Reminders");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

}



