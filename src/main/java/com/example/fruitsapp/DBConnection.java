package com.example.fruitsapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection connectDB() {
        try {
            String url = "jdbc:sqlite:fruits.sqlite"; // Replace with the correct path to your database file
            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
