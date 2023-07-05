package com.example.fruitsapp;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SplashScreen extends Application {

    private static final int SPLASH_SCREEN_DURATION = 2000; // Duration in milliseconds

    @Override
    public void start(Stage primaryStage) {
        Image splashImage = new Image("splash.png");
        ImageView imageView = new ImageView(splashImage);

        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), imageView);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(1000), imageView);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);

        fadeIn.play();
        fadeIn.setOnFinished(event -> {
            fadeOut.play();
            fadeOut.setOnFinished(closeEvent -> {
                primaryStage.close();
                // Continue with your application code here
                // ...
            });
        });

        PauseTransition pause = new PauseTransition(Duration.millis(SPLASH_SCREEN_DURATION));
        pause.setOnFinished(event -> primaryStage.close());
        pause.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
