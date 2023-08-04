package com.example.fruitsapp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;
    private static LoginController loginController;

    public static void main(String[] args) {
        launch(args);
    }

    public static LoginController getLoginController() {
        return loginController;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Main.primaryStage = primaryStage;
        startSplashScreen();
    }

    private void startSplashScreen() throws IOException {
        Parent splashRoot = FXMLLoader.load(getClass().getResource("Splash.fxml"));
        Scene splashScene = new Scene(splashRoot);
        primaryStage.setScene(splashScene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

        // Create a PauseTransition for the splash screen
        javafx.animation.PauseTransition splashPause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(3));
        splashPause.setOnFinished(event -> {
            // After 3 seconds, load and display the Login.fxml
            loadAndDisplayLoginScene();
        });
        splashPause.play();
    }

    private void loadAndDisplayLoginScene() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
            Parent loginRoot = loader.load();
            loginController = loader.getController();
            Scene loginScene = new Scene(loginRoot);
            primaryStage.setScene(loginScene);
            primaryStage.show();
            primaryStage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void changeScene(MouseEvent event, String fxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFileName +
                ".fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
}
