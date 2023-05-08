package com.example.csfinal;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.application.Application;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Rectangle playerRectangle;
    double x;

    @FXML
    Circle playBall;

    @FXML
    AnchorPane pane;

    @FXML
    Text textScore;

    @FXML
    Button restartButton;

    int maxBounceAngle = 75;
    int score = 0;
    boolean gameOver = false;

    public void move(KeyEvent e) {
        switch (e.getCode()) {
            case A:
                if ((x + 10) >= -(playerRectangle.getParent().getLayoutBounds().getWidth() / 3)) {
                    playerRectangle.setX(x -= 10);
                }
                break;
            case D:
                if ((x + 10) <= (playerRectangle.getParent().getLayoutBounds().getWidth() / 3)) {
                    playerRectangle.setX(x += 10);
                }
                break;
            default:
                break;
        }
    }

    public void restart(ActionEvent e) {
        if (restartButton.getOpacity() > 0) { //It's a double, so I can't do == 1

        }
    }



    /* Sources for ball movement:
    https://www.youtube.com/watch?v=x6NFmzQHvMU
    https://gamedev.stackexchange.com/questions/4253/in-pong-how-do-you-calculate-the-balls-direction-when-it-bounces-off-the-paddl?noredirect=1&lq=1
    */

    Timeline bounce = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
        //Amount that the ball will move per frame
        //This is one frame per 10 milliseconds which is 100 FPS
        int deltaX = 3;
        int deltaY = 3;

        @Override
        public void handle(ActionEvent e) {
            //The movement of the ball per frame
            playBall.setLayoutX(playBall.getLayoutX() + deltaX);
            playBall.setLayoutY(playBall.getLayoutY() + deltaY);

            //Finding the borders of the AnchorPane so that the ball can bounce off of them instead of leaving the frame
            //Also need to find the bottom to implement lose condition
            Bounds bounds = pane.getBoundsInLocal();
            boolean rightBorder = playBall.getLayoutX() >= (bounds.getMaxX() - playBall.getRadius());
            boolean leftBorder = playBall.getLayoutX() <= (bounds.getMinX() + playBall.getRadius());
            boolean bottomBorder = playBall.getLayoutY() >= (bounds.getMaxY() - playBall.getRadius());
            boolean topBorder = playBall.getLayoutY() <= (bounds.getMinY() + playBall.getRadius());

            //If at one of the borders, change direction accordingly
            if (rightBorder || leftBorder) {
                deltaX *= -1;
            }
            if (topBorder) {
                deltaY *= -1;
            }
            if (bottomBorder) {
                restartButton.setOpacity(1);
            }

            //I need to find a better way to do this
            for (int i = 0; i < pane.getChildren().size(); i++) {
                if (pane.getChildren().get(i).getClass().getSimpleName().equals("Rectangle")) {
                    Rectangle hitRect = (Rectangle) pane.getChildren().get(i);
                    if (playBall.getBoundsInParent().intersects(hitRect.getBoundsInParent())) {
                        //System.out.println("bounce");
                        int relativeIntersectY = (int) ((hitRect.getY() + (hitRect.getHeight() / 2)) - playBall.getCenterY());
                        int normalizedRIY = (int) (relativeIntersectY / (hitRect.getHeight() / 2));
                        int bounceAngle = normalizedRIY * maxBounceAngle;
                        deltaX *= Math.cos(bounceAngle);
                        deltaY *= -1;
                        if (hitRect.getId() == null) {
                            score++;
                            pane.getChildren().remove(hitRect);
                            textScore.setText("Score: " + score);
                        }
                    }
                }
            }
        }
    }));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bounce.setCycleCount(-1); //Equivalent to Animation.INDEFINITE
        if (!gameOver) {
            bounce.play();
        }
    }
}