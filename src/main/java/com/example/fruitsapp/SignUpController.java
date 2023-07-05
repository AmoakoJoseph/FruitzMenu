package com.example.fruitsapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SignUpController {

    @FXML
    private Label Back;

    @FXML
    private ImageView close;


    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField enterFirstName;

    @FXML
    private TextField enterLastName;

    @FXML
    private PasswordField enterPassword;

    @FXML
    private TextField enterUsername;


    @FXML
    private Label login;

    @FXML
    private Button signup;




    @FXML
    void Back(MouseEvent event) throws IOException {
        Main m =new Main();
        Main.changeScene(event,"login");


    }

    @FXML
    void close(MouseEvent event){
        Splash.getstage.close();

    }



}
