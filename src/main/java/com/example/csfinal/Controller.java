package com.example.csfinal;

import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Controller {
    @FXML
    Rectangle playerRectangle;
    double x;
    Circle playBall;

    public void move(KeyEvent e) {
        switch (e.getCode()) {
            case A:
                if ((x - 5) >= -(playerRectangle.getParent().getLayoutBounds().getWidth()/3)) {
                    playerRectangle.setX(x -= 5);
                }
                break;
            case D:
                if ((x + 5) <= (playerRectangle.getParent().getLayoutBounds().getWidth()/3)) {
                    playerRectangle.setX(x += 5);
                }
                break;
            default:
                break;
        }
    }

    //Timeline loop = new Timeline()

}