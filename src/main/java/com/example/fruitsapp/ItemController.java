package com.example.fruitsapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import com.example.fruitsapp.Main;
import com.example.fruitsapp.MyListener;
import com.example.fruitsapp.Fruit;

import java.io.File;

public class ItemController {

    @FXML
    private Label nameLabel;

    @FXML
    private ImageView img;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(fruit);
    }

    private Fruit fruit;

    private MyListener myListener;

    public void setData(Fruit fruit, MyListener myListener) {
        this.fruit = fruit;
        this.myListener = myListener;

        nameLabel.setText(fruit.getName());

        String imagePath = fruit.getImgSrc();
        File imageFile = new File(imagePath);

        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            img.setImage(image);
        } else {
            Image defaultImage = new Image(getClass().getResourceAsStream("com/example/fruitsapp/img/cherry.png"));
            img.setImage(defaultImage);
        }
    }
}