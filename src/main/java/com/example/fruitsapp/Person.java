package com.example.fruitsapp;

import javafx.fxml.FXML;

public class Person {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    public Person(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    @FXML
    public String getFirstName() {
        return firstName;
    }

    @FXML
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @FXML
    public String getLastName() {
        return lastName;
    }

    @FXML
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @FXML
    public String getUserName() {
        return userName;
    }

    @FXML
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @FXML
    public String getPassword() {
        return password;
    }

    @FXML
    public void setPassword(String password) {
        this.password = password;
    }


}
