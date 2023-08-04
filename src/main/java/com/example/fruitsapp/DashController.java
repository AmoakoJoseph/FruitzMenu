package com.example.fruitsapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashController implements Initializable {
    public Label Username;

    @FXML
    private Button search;

    @FXML
    private VBox chosenFruitCard;

    @FXML
    private TextField enterFruit;

    @FXML
    private Label fruitNameLable;


    @FXML
    private ImageView fruitImg;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView minimize;

    @FXML
    private ImageView maximize;

    @FXML
    private Button open;

    @FXML
    private List<Fruit> fruits = new ArrayList<>();

    @FXML
    private Image image;

    @FXML
    private MyListener myListener;

    private List<Fruit> getData() {
        List<Fruit> fruits = new ArrayList<>();
        Fruit fruit;

        fruit = new Fruit();
        fruit.setName("Kiwi");
        fruit.setImgSrc("src/main/resources/com/example/fruitsapp/img/kiwi.png");
        fruit.setColor("6A7324");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Coconut");
        fruit.setImgSrc("src/main/resources/com/example/fruitsapp/img/coconut.png");
        fruit.setColor("A7745B");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Peach");
        fruit.setImgSrc("src/main/resources/com/example/fruitsapp/img/peach.png");
        fruit.setColor("F16C31");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Grapes");
        fruit.setImgSrc("src/main/resources/com/example/fruitsapp/img/grapes.png");
        fruit.setColor("291D36");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Watermelon");
        fruit.setImgSrc("src/main/resources/com/example/fruitsapp/img/watermelon.png");
        fruit.setColor("22371D");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Orange");
        fruit.setImgSrc("src/main/resources/com/example/fruitsapp/img/orange.png");
        fruit.setColor("FB5D03");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Strawberry");
        fruit.setImgSrc("src/main/resources/com/example/fruitsapp/img/strawberry.png");
        fruit.setColor("80080C");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Mango");
        fruit.setImgSrc("src/main/resources/com/example/fruitsapp/img/mango.png");
        fruit.setColor("FFB605");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Cherry");
        fruit.setImgSrc("src/main/resources/com/example/fruitsapp/img/cherry.png");
        fruit.setColor("5F060E");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Banana");
        fruit.setImgSrc("src/main/resources/com/example/fruitsapp/img/banana.png");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Apple");
        fruit.setImgSrc("src/main/resources/com/example/fruitsapp/img/apple.png");
        fruit.setColor("66b447");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Guava");
        fruit.setImgSrc("src/main/resources/com/example/fruitsapp/img/guava.png");
        fruit.setColor("ec6a4b");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Pawpaw");
        fruit.setImgSrc("src/main/resources/com/example/fruitsapp/img/pawpaw.png");
        fruit.setColor("E56717");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Pineapple");
        fruit.setImgSrc("src/main/resources/com/example/fruitsapp/img/pineapple.png");
        fruit.setColor("9ACD32 ");
        fruits.add(fruit);

        return fruits;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fruits.addAll(getData());
        if (fruits.size() > 0) {
            myListener = this::setChosenFruit;
            setChosenFruit(fruits.get(0));
        }

        // Display the username of the currently logged-in user in the Username label
       String logUser= LoginController.logUser;
        if (logUser!= null && !logUser.isEmpty()) {
            Username.setText("Welcome " + logUser );
            System.out.println("Username is" + logUser);
        }


        int column = 0;
        int row = 1;
        try {
            for (Fruit fruit : fruits) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(fruit, myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setChosenFruit(Fruit fruit) {
        String imagePath = fruit.getImgSrc();
        System.out.println(imagePath);

        try {
            InputStream inputStream = new FileInputStream(imagePath);

            Image image = new Image(inputStream);

            fruitImg.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//
        fruitNameLable.setText(fruit.getName());
        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
    }


    @FXML
    public void closeWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) Username.getScene().getWindow();
        stage.close();
    }

    @FXML
    void moveAccount(MouseEvent event) throws IOException {
        Main.changeScene(event, "Profile");
    }

    @FXML
    public void OpenFiles(MouseEvent mouseEvent) {

    }

    @FXML
    void getFruit(MouseEvent mouseEvent) {
        }
    @FXML
    public void Minimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) Username.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void Maximize(MouseEvent mouseEvent) {
        Stage stage = (Stage) Username.getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }
}
