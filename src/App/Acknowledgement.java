package App;

import javafx.animation.PathTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Acknowledgement {

    public Pane container;
    public PathTransition pt;

    public Acknowledgement(){
        container =new Pane();
        Rectangle rectangle = new Rectangle(0, 0, 25, 25);
        rectangle.setFill(Color.GREEN);
        Line line =new Line();
        line.setStartX(0.0f);
        line.setStartY(550.0f);
        line.setEndX(0.0f);
        line.setEndY(50);
        line.setStroke(Color.TRANSPARENT);

        pt = new PathTransition();
        pt.setDuration(Duration.millis(8000));
        pt.setPath(line);
        pt.setNode(rectangle);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//        pt.play();
        rectangle.setOnMousePressed(e -> pt.pause());
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
