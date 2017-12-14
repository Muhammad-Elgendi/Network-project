/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.shape.Line;

public class PathTransitionDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Rectangle rectangle = new Rectangle(0, 0, 25, 50);
        rectangle.setFill(Color.ORANGE);
//        Circle circle = new Circle(125, 100, 50);
//        circle.setFill(Color.WHITE);
//        circle.setStroke(Color.BLACK);
        Line line =new Line();
        line.setStartX(0.0f);
        line.setStartY(0.0f);
        line.setEndX(0.0f);
        line.setEndY(550.0f);
        line.setStroke(Color.TRANSPARENT);
        pane.getChildren().addAll(line, rectangle);
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.millis(10000));
        pt.setPath(line);
        pt.setNode(rectangle);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//        pt.setCycleCount(Timeline.INDEFINITE);
//        pt.setAutoReverse(true);
        pt.play();
        rectangle.setOnMousePressed(e -> pt.pause());
        rectangle.setOnMouseReleased(e -> pt.play());
        Scene scene = new Scene(pane, 800, 600);
        primaryStage.setTitle("PathTransitionDemo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}