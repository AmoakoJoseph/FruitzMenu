package com.example.fruitsapp;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

        @FXML
        private Label SignUp;
        @FXML
        private Button button;
        @FXML
        private ImageView close;
        @FXML
        private PasswordField enterPassword;
        @FXML
        private TextField enterUsername;
        @FXML
        private Label wronglogin;
        @FXML
        void terminate(MouseEvent event){
            Splash.getstage.close();
        }
        @FXML
        void signup(MouseEvent event) throws IOException {
            Main f =new Main();
            Main.changeScene(event, "SignUp");
        }

        @FXML
        void userLogin(MouseEvent event) throws IOException {
            checkLogin(event);

        }

        private void checkLogin(MouseEvent event) throws IOException {
            Main m = new Main();
            if(enterUsername.getText().toString().equals("ace") && enterPassword.getText().toString().equals("123")){
                wronglogin.setText("Succes");

                Main.changeScene(event,"Dashboard");
            }
            else if (enterUsername.getText().isEmpty() && enterPassword.getText().isEmpty()) {
                wronglogin.setText("Please enter your data. ");
            }
            else {
                wronglogin.setText("Wrong username or password.");
            }

        }

    }
