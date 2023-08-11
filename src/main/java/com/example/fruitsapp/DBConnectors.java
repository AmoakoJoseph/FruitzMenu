package com.example.fruitsapp;

import javafx.scene.control.TextField;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.fruitsapp.LoginController.logUser;

public class DBConnectors {
    public static void GetUserinfo(TextField firstNameField, TextField lastNameField, TextField usernameField, TextField passwordField, String loggedInUsername) throws SQLException {
        String query = "SELECT Firstname, Lastname, Username, Password FROM User WHERE Username = ?";
        try (Connection connection = DBConnection.connectDB();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, loggedInUsername);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    firstNameField.setText(rs.getString("Firstname"));
                    lastNameField.setText(rs.getString("Lastname"));
                    usernameField.setText(rs.getString("Username"));
                    passwordField.setText(rs.getString("Password"));
                }
            }
        }
    }

    public static void saveReminder(String description, LocalDate date) throws SQLException {
        try (Connection connection = DBConnection.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Reminders (Username, Date, Description) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, logUser);
            preparedStatement.setInt(2, dateToInt(date));
            preparedStatement.setString(3, description);
            preparedStatement.executeUpdate();
        }
    }

    public static void deleteReminder(Reminder reminder) throws SQLException {
        try (Connection connection = DBConnection.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Reminders WHERE Username = ? AND Date = ? AND Description = ?")) {
            preparedStatement.setString(1, logUser);
            preparedStatement.setInt(2, reminder.getDate());
            preparedStatement.setString(3, reminder.getDescription());
            preparedStatement.executeUpdate();
        }
    }




    public static List<Reminder> getRemindersForDate(int date) throws SQLException {
        List<Reminder> remindersForUserAndDate = new ArrayList<>();
        String query = "SELECT Date, Description FROM Reminders WHERE Username = ? AND Date = ?";
        try (Connection connection = DBConnection.connectDB();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, logUser);
            ps.setInt(2, date);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int reminderDateAsInt = rs.getInt("Date");
                    String description = rs.getString("Description");

                    Reminder reminder = new Reminder(reminderDateAsInt, description);
                    remindersForUserAndDate.add(reminder);
                }
            }
        }
        return remindersForUserAndDate;
    }

    public static List<Reminder> getAllReminders() {
        List<Reminder> reminders = new ArrayList<>();

        try (Connection conn = DBConnection.connectDB()) {
            String query = "SELECT Date, Description FROM Reminders WHERE Username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, logUser);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int dateAsInt = rs.getInt("Date");
                String description = rs.getString("Description");

                Reminder reminder = new Reminder(dateAsInt, description);
                reminders.add(reminder);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        return reminders;
    }


    private static int dateToInt(LocalDate date) {

        return Integer.parseInt(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    static LocalDate intToDate(int dateInt) {
        String dateStr = String.valueOf(dateInt);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(dateStr, formatter);
    }


    public static Fruit fetchFruitDetails(String fruitName) {
        Fruit fruit = null;
        Connection connection = null;
        try {
            connection = DBConnection.connectDB();
            String query = "SELECT * FROM fruits WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, fruitName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String additionalInfo = resultSet.getString("additional_info");

                // Create a Fruit object with retrieved data
                fruit = new Fruit();
                fruit.setName(name);
                fruit.setdescription(description);
                fruit.setadditional_info(additionalInfo);
            }
        } catch (SQLException e) {
            System.out.println("Can't get the fruit details");
        } finally {
            DBConnection.closeConnection(connection);
        }
        return fruit;
    }


}
