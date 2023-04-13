package com.example.csfinal;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.*;

import java.io.IOException;

public class Application extends javafx.application.Application {
    int columns = 10, rows = 3, horizontal = 100, vertical = 100;
    int breakableHorizontalMin = 45, breakableHorizontalMax = 90, breakableVertical = 15;
    Random rand = new Random();

    public Node[] addBreakableRectangles() {
        Node[] groupOfBreakables = new Node[columns];
        for (int i = 0; i < columns; i++) {
            groupOfBreakables[i] = new Rectangle(rand.nextInt(breakableHorizontalMin, breakableHorizontalMax), breakableVertical);
        }
        return groupOfBreakables;
    }

    public GridPane createBreakables() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(3);
        for (int i = 0; i < rows; i++) {
            gridPane.add(new HBox(5, addBreakableRectangles()), 0, i);
        }
        return gridPane;
    }

    @Override
    public void start(Stage stage) {
        AnchorPane root = new AnchorPane();
        GridPane gridPane = createBreakables();

        gridPane.add(new Rectangle(300, 20), 0, 50);

        AnchorPane.setLeftAnchor(gridPane, 0.0);
        AnchorPane.setRightAnchor(gridPane, 0.0);

        root.getChildren().add(gridPane);

        Scene scene = new Scene(root);
        stage.setTitle("Atari Breakout");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}