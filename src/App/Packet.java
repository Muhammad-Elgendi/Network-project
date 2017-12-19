package App;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
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

import java.util.ArrayList;
import java.util.Collections;

public class Packet {

    public static VBox labelsContainer;
    public static GridPane packetsContainer;
    public static Pane packetWithWin;
    public static Pane slidingWindowPacketContainer;
    public static Scene scene;
    public static Point2D windowCoord;
    public static Point2D sceneCoord;
    public static int slidingFactor = 0;
    public static int lastReceivedPacket = 0;
    public static boolean slidingPermission = false;
    public static int waitFor = 0;
    public static ArrayList<Double> positions = new ArrayList<Double>();
    public static ArrayList<Integer> packetsWinIds = new ArrayList<Integer>();
    public static int reminder = 0;
    public Pane container;
    public PathTransition pt;
    public Rectangle rectangle;
    public Text text;
    public Rectangle window;

    public Packet(int i, int count) {

        labelsContainer = Main.labelsContainer;
        packetsContainer = Main.packetsContainer;
        packetWithWin = Main.packetWithWin;
        slidingWindowPacketContainer = Main.slidingWindowPacketContainer;
        scene = Main.scene;
        windowCoord = Main.windowCoord;
        sceneCoord = Main.sceneCoord;
        window = new Rectangle();
        window.setFill(Color.TRANSPARENT);
        window.setStroke(Color.BLACK);
        window.setY(535);
        window.setHeight(30);
        window.setWidth((25 * count) + ((count - 1) * 10) + count);
        container = new Pane();
        rectangle = new Rectangle(0, 0, 25, 25);
        rectangle.setFill(Color.ORANGE);
        Line line = new Line();
        line.setStartX(0.0f);
        line.setStartY(50);
        line.setEndX(0.0f);
        line.setEndY(550.0f);
        line.setStroke(Color.TRANSPARENT);

        pt = new PathTransition();
        pt.setDuration(Duration.millis(6000));
        pt.setPath(line);
        text = new Text("" + i);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rectangle, text);
        pt.setNode(stack);

//        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//        pt.play();
        rectangle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pt.stop();
                labelsContainer.getChildren().add(new Label("--------Packet-----------X " + i));
                pt.setPath(line);
                KeyFrame mainkeyFrame = new KeyFrame(Duration.seconds(12), ev -> {
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
                labelsContainer.getChildren().add(new Label("------------------> Packet " + i));
                slidingFactor = (slidingFactor++) + 1;
                Acknowledgement acknowledgement = new Acknowledgement(i, count);
                packetsContainer.add(acknowledgement.getContainer(), i, 0);
                acknowledgement.getPt().play();
                positions.add(-15.0 + (slidingFactor * 35));
                packetsWinIds.add(i);

                if (waitFor == i) {
                    waitFor++;
                    for (int y=waitFor;y<count;y++){
                        if (packetsWinIds.contains(y))
                            waitFor++;
                    }

                    slidingPermission = true;

                } else {
                    slidingPermission = false;
                }

                System.out.println("Packet : " + packetsWinIds.get(reminder));
                System.out.println("Position : " + positions.get(reminder));
                System.out.println("Waiting for : " + waitFor);
                System.out.println("Permission : " + slidingPermission);
                if (slidingPermission) {
                    window.setX(-15 + (slidingFactor * 35));
                }
                reminder++;
                lastReceivedPacket = i;
                if (slidingPermission) {
                    slidingWindowPacketContainer.getChildren().clear();
                    slidingWindowPacketContainer.getChildren().add(window);
                }
            }
        });
//        rectangle.setOnMouseReleased(e -> pt.play());
//        pt.setNode(text);
        container.getChildren().addAll(line, stack);
    }

    public Pane getContainer() {
        return container;
    }

    public PathTransition getPt() {
        return pt;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Text getTextOnPacket() {
        return text;
    }


}
