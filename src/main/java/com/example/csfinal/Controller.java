package com.example.csfinal;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.application.Application;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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

    Stage stage;
    Scene scene;
    Parent root;

    public void move(KeyEvent e) {
        switch (e.getCode()) {
            case A:
                if ((x + 20) >= -(playerRectangle.getParent().getLayoutBounds().getWidth() / 3)) {
                    playerRectangle.setX(x -= 20);
                }
                break;
            case D:
                if ((x + 20) <= (playerRectangle.getParent().getLayoutBounds().getWidth() / 3)) {
                    playerRectangle.setX(x += 20);
                }
                break;
            default:
                break;
        }
    }

    public void restart(ActionEvent e) throws IOException {
        if (restartButton.getOpacity() > 0) { //It's a double, so I can't do == 1
            //Also, I check to make sure it's not transparent so people don't accidentally restart the game when they
            //click on the window
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /* Sources for ball movement:
    https://www.youtube.com/watch?v=x6NFmzQHvMU
    https://gamedev.stackexchange.com/questions/4253/in-pong-how-do-you-calculate-the-balls-direction-when-it-bounces-off-the-paddl?noredirect=1&lq=1
    */

    Timeline bounce = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
        //Amount that the ball will move per frame
        //This is one frame per 10 milliseconds which is 100 FPS
        int deltaX = 2;
        int deltaY = 2;

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
                bounce.stop();
                restartButton.setText("Press to restart");
                restartButton.setOpacity(1);
            }

            //I need to find a better way to do this
            //If the ball hits something at the wrong angle, it'll intersect weirdly and glitch out. No idea how to fix
            for (int i = 0; i < pane.getChildren().size(); i++) {
                if (pane.getChildren().get(i).getClass().getSimpleName().equals("Rectangle")) {
                    Rectangle hitRect = (Rectangle) pane.getChildren().get(i);
                    if (playBall.getBoundsInParent().intersects(hitRect.getBoundsInParent())) {
                        int relativeIntersectY = (int) ((hitRect.getY() + (hitRect.getHeight() / 2)) - playBall.getCenterY());
                        int normalizedRIY = (int) (relativeIntersectY / (hitRect.getHeight() / 2));
                        int bounceAngle = normalizedRIY * maxBounceAngle;
                        //Just some math which basically sees where the ball hits the rectangle and calculates the
                        //bounce angle based on that. If the ball hits the middle, it should fly basically straight, and
                        //if the ball hits the edges, it should bounce at the greatest angle
                        deltaX *= Math.cos(bounceAngle);
                        deltaY *= -1; //For some reason, the math that they did in the original post causes deltaY to
                        //always be -3, so I decided to just flip the direction instead
                        if (hitRect.getId() == null) {
                            score++;
                            pane.getChildren().remove(hitRect);
                            textScore.setText("Score: " + score);
                            if (score == 30) {
                                restartButton.setText("You win!\nPress to restart");
                                restartButton.setOpacity(1);
                            }
                        }
                    }
                }
            }
        }
    }));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bounce.setCycleCount(-1); //Equivalent to Animation.INDEFINITE
        bounce.play();
    }
}