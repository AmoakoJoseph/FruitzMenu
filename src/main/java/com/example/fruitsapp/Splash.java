package com.example.fruitsapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Splash implements Initializable {

    @FXML
    private ImageView splashImage;

    @FXML
    private AnchorPane splashPane;

    public static Stage getstage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){new loadsplashScreen().start();}
    class loadsplashScreen extends Thread{

        @Override
        public void run(){
            try{
                Thread.sleep(5000);

                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {

                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.setScene(scene);
                            getstage =stage;
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                        splashPane.getScene().getWindow().hide();
                    }
                });

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



}