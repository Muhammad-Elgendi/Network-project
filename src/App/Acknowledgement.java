package App;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Acknowledgement {

    public static VBox labelsContainer;
    public static GridPane packetsContainer;
    public static Pane packetWithWin;
    public static Pane slidingWindowAckContainer;
    public static int slidingFactor = -2;
    public static int lastReceivedAck=0;
    public Pane container;
    public PathTransition pt;
    public Rectangle rectangle;
    public Text text;
    public Rectangle window;

    public Acknowledgement(int i, int count) {
        labelsContainer = Main.labelsContainer;
        packetsContainer = Main.packetsContainer;
        packetWithWin = Main.packetWithWin;
        slidingWindowAckContainer = Main.slidingWindowAckContainer;
        window = new Rectangle();
        window.setFill(Color.TRANSPARENT);
        window.setStroke(Color.BLACK);
        window.setY(35);
        window.setHeight(30);
        window.setWidth((25 * count) + ((count - 1) * 10) + count);
        container = new Pane();

        rectangle = new Rectangle(0, 0, 25, 25);
        rectangle.setFill(Color.GREEN);
        Line line = new Line();
        line.setStartX(0.0f);
        line.setStartY(550.0f);
        line.setEndX(0.0f);
        line.setEndY(50);
        line.setStroke(Color.TRANSPARENT);

        pt = new PathTransition();
        pt.setDuration(Duration.millis(5500));
        pt.setPath(line);
        text = new Text("" + (i));
        text.setFill(Color.WHITE);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rectangle, text);
        pt.setNode(stack);
//        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//        pt.play();
        rectangle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pt.stop();
                rectangle.setFill(Color.TRANSPARENT);
                labelsContainer.getChildren().add(new Label((i) + " X---------Ack------------- "));
                pt.setPath(line);
                Packet packet = new Packet(i, count);
                packet.getTextOnPacket().setFill(Color.TRANSPARENT);
                packet.getRectangle().setFill(Color.TRANSPARENT);
                packetsContainer.add(packet.getContainer(), i, 0);
                KeyFrame mainkeyFrame = new KeyFrame(Duration.seconds(12), ev -> {
                    packet.getTextOnPacket().setFill(Color.BLACK);
                    packet.getRectangle().setFill(Color.ORANGE);
                    packet.getPt().play();
                });
                Timeline timelineTimer = new Timeline(mainkeyFrame);
                timelineTimer.setCycleCount(1);
                timelineTimer.play();
            }
        });

        pt.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                labelsContainer.getChildren().add(new Label((i) + " Ack <---------------------"));
                slidingFactor = (slidingFactor++) + 1;
                if(i==lastReceivedAck+1) {
                    window.setX(-15 + (slidingFactor * 35));
                }
                lastReceivedAck=i;
                slidingWindowAckContainer.getChildren().clear();
                slidingWindowAckContainer.getChildren().add(window);

            }
        });
//        rectangle.setOnMouseReleased(e -> pt.play());
        container.getChildren().addAll(line, stack);
    }

    public Pane getContainer() {
        return container;
    }

    public PathTransition getPt() {
        return pt;
    }
}
