package App;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Acknowledgement {

    public static VBox labelsContainer;
    public Pane container;
    public PathTransition pt;
    public Rectangle rectangle;

    public Acknowledgement(int i){
        labelsContainer = Main.labelsContainer;

        container =new Pane();

        rectangle = new Rectangle(0, 0, 25, 25);
        rectangle.setFill(Color.GREEN);
        Line line =new Line();
        line.setStartX(0.0f);
        line.setStartY(550.0f);
        line.setEndX(0.0f);
        line.setEndY(50);
        line.setStroke(Color.TRANSPARENT);

        pt = new PathTransition();
        pt.setDuration(Duration.millis(6000));
        pt.setPath(line);
        pt.setNode(rectangle);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//        pt.play();
        rectangle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pt.stop();
                labelsContainer.getChildren().add(new Label("---------ack-------------X " + i));
                pt.setPath(line);
                KeyFrame mainkeyFrame = new KeyFrame(Duration.seconds(6), ev -> {
                    pt.play();
                });
                Timeline timelineTimer = new Timeline(mainkeyFrame);
                timelineTimer.setCycleCount(1);
                timelineTimer.play();
            }
        });

        pt.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                labelsContainer.getChildren().add(new Label("---------------------> Ack " + i));
            }
        });
//        rectangle.setOnMouseReleased(e -> pt.play());
        container.getChildren().addAll(line,rectangle);
    }

    public Pane getContainer(){
        return container;
    }

    public PathTransition getPt(){
        return pt;
    }
}
