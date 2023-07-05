package com.example.fruitsapp;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class DashController {

        @FXML
        private ImageView logout;

        @FXML
        void userLogout(MouseEvent event) throws IOException {
            Main m =new Main();
            Main.changeScene(event, "login");

        }

    }

