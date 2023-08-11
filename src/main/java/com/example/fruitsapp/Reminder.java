package com.example.fruitsapp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reminder {

    private final int date;
    private String description;

    public Reminder(int date, String description) {
        this.date = date;
        this.description = description;
    }

    public int getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toDataString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.ofEpochDay(date);
        String formattedDate = localDate.format(formatter);
        return formattedDate + "," + description + ",";
    }
}
