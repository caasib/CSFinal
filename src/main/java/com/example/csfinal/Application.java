package com.example.csfinal;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.*;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setTitle("Atari Breakout");
        stage.setScene(scene);
        scene.setOnKeyPressed(controller::move);
        stage.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (oldScene != null) {
                oldScene.removeEventHandler(KeyEvent.KEY_PRESSED, controller::move);
            }
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_PRESSED, controller::move);
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}