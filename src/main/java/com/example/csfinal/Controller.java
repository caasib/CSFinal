package com.example.csfinal;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Random;

import java.util.Objects;

public class Controller {
    @FXML
    AnchorPane pane;

    Random rand = new Random();

    Image redTriangle = new Image(Objects.requireNonNull(getClass().getResourceAsStream("red.png")));
    Image blueCircle = new Image(Objects.requireNonNull(getClass().getResourceAsStream("blue.png")));
    Image greenSquare = new Image(Objects.requireNonNull(getClass().getResourceAsStream("green.png")));
    Image yellowStar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("yellow.png")));

    Image[] images = {redTriangle, blueCircle, greenSquare, yellowStar};

    public boolean nextTo(ImageView imageView1, ImageView imageView2) {
        ObservableList<Node> imageList = pane.getChildren();
        return (imageList.indexOf(imageView1) + 1 == imageList.indexOf(imageView2)) || (imageList.indexOf(imageView1) - 1 == imageList.indexOf(imageView2));
    }

    public void selectImage(ImageView imageView) {
        ObservableList<Node> imageList = pane.getChildren();
        for (int i = 0; i < imageList.size(); i++) {
            if (nextTo((ImageView)imageList.get(i), imageView)) {
                System.out.println("these are next to each other: " + ((ImageView) imageList.get(i)).getImage() + " and " + imageView.getImage());
            }
        }
    }

    public void imageClick(MouseEvent e) {
        ImageView currentImage = (ImageView)e.getSource();
        selectImage(currentImage);
        //currentImage.setImage(images[rand.nextInt(images.length)]);
    }
}