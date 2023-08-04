package com.example.fruitsapp;

import java.time.LocalDate;
import java.util.StringTokenizer;

public class Reminder {

    private LocalDate date;
    private String description;

    public Reminder(LocalDate date, String description) {
        this.date = date;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }


    public static Reminder parseReminder(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, ",");
        LocalDate date = LocalDate.parse(tokenizer.nextToken());
        String description = tokenizer.nextToken().trim();
        return new Reminder(date, description);
    }


    public String toDataString() {
        return date + "," + description;
    }




    public void setDescription(String description) {
        this.description = description;
    }
}