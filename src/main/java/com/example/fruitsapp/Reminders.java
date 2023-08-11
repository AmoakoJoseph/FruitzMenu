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
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;

import static com.example.fruitsapp.DBConnectors.getRemindersForDate;
import static com.example.fruitsapp.DBConnectors.intToDate;
import static com.example.fruitsapp.LoginController.logUser;

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
        System.out.println("Initializing Reminders...");

        // Set tooltips for each ImageView
        setTooltip(About, "About");
        setTooltip(Home, "Home");
        setTooltip(Logout, "Logout");
        setTooltip(Reminders, "Reminders");


        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Retrieve reminders from the database and populate the TableView
        List<Reminder> reminders = DBConnectors.getAllReminders();
        reminderTable.setItems(FXCollections.observableArrayList(reminders));

        loadReminders();


        System.out.println("Reminders initialized.");
    }


    private void loadReminders() {
        ObservableList<Reminder> reminders = FXCollections.observableArrayList();
        try {
            String selectQuery = "SELECT Date, Description FROM Reminders WHERE Username = ?";
            Connection connection = DBConnection.connectDB();
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, logUser);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int dateAsInt = resultSet.getInt("Date");
                String description = resultSet.getString("Description");
                reminders.add(new Reminder(dateAsInt, description));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        reminderTable.setItems(reminders);
    }




    @FXML
    public void addreminder(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            System.out.println("Adding reminder...");

            int date = (int) reminderDatePicker.getValue().toEpochDay(); // Convert LocalDate to days since 1970-01-01
            String description = reminderTextArea.getText().trim();

            if (!description.isEmpty()) {
                try {
                    DBConnectors.saveReminder(description, LocalDate.ofEpochDay(date));
                    Reminder newReminder = new Reminder(date, description);
                    remindersList.add(newReminder);
                    reminderTextArea.clear();
                    reminderDatePicker.setValue(null);

                    // Add the new reminder directly to the TableView
                    reminderTable.getItems().add(newReminder);
                    System.out.println("Saved");
                } catch (SQLException e) {
                    System.err.println("Error: Failed to save the reminder to the database.");
                    e.printStackTrace(); // Print the exception details for debugging
                }
            } else {
                System.err.println("Error: Please enter a description.");
            }
        }
    }

    @FXML
    void deletereminder(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            System.out.println("Deleting reminder...");

            Reminder selectedReminder = reminderTable.getSelectionModel().getSelectedItem();
            if (selectedReminder != null) {
                remindersList.remove(selectedReminder);
                reminderTable.getItems().remove(selectedReminder);
                try {
                    DBConnectors.deleteReminder(selectedReminder);
                } catch (SQLException e) {
                    System.out.print("Can't delete");
                    e.printStackTrace(); // Print the exception details for debugging
                }
            }

            System.out.println("Reminder deleted.");
        }
    }


    /*@FXML
    public void deletereminder(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            Reminder selectedReminder = reminderTable.getSelectionModel().getSelectedItem();
            if (selectedReminder != null) {
                remindersList.remove(selectedReminder);
                reminderTable.getItems().remove(selectedReminder);
                try {
                    DBConnectors.deleteReminder(selectedReminder);
                } catch (SQLException e) {
                    System.out.print("Can't delete");
                }
            }
        }
    }
*/

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




}



