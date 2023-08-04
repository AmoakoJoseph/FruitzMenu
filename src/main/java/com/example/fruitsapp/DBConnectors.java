package com.example.fruitsapp;

import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public static List<Reminder> getAllReminders() throws SQLException {
        List<Reminder> remindersList = new ArrayList<>();
        String query = "SELECT Date, Description FROM Reminders";
        try (Connection connection = DBConnection.connectDB();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LocalDate date = rs.getDate("Date").toLocalDate();
                String description = rs.getString("Description");
                Reminder reminder = new Reminder(date, description);
                remindersList.add(reminder);
            }
        }
        return remindersList;
    }

    public static void saveReminder(String d, LocalDate dt) throws SQLException {
        Reminder rm = new Reminder(dt,d);
        try (Connection connection = DBConnection.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Reminders (Date, Description) VALUES (?, ?)")) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(rm.getDate()));
            preparedStatement.setString(2, rm.getDescription());
            preparedStatement.executeUpdate();
        }
    }

    public static void deleteReminder(Reminder reminder) throws SQLException {
        try (Connection connection = DBConnection.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Reminders WHERE Date = ? AND Description = ?")) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(reminder.getDate()));
            preparedStatement.setString(2, reminder.getDescription());
            preparedStatement.executeUpdate();
        }
    }

    public static List<Reminder> getRemindersForDate(LocalDate date) throws SQLException {
        List<Reminder> remindersForDate = new ArrayList<>();
        String query = "SELECT Date, Description FROM Reminders WHERE Date = ?";
        try (Connection connection = DBConnection.connectDB();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDate(1, java.sql.Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LocalDate reminderDate = rs.getDate("Date").toLocalDate();
                    String description = rs.getString("Description");
                    Reminder reminder = new Reminder(reminderDate, description);
                    remindersForDate.add(reminder);
                }
            }
        }
        return remindersForDate;
    }

}
