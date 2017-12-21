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

import java.util.ArrayList;
import java.util.Collections;

public class Packet {

    public static VBox labelsContainer;
    public static GridPane packetsContainer;
    public static Pane packetWithWin;
    public static Pane slidingWindowPacketContainer;
    public static int slidingFactor = 0;
    public static int lastReceivedPacket = 0;
    public static boolean slidingPermission = false;
    public static int waitFor = 0;
    public static ArrayList<Double> positions = new ArrayList<Double>();
    public static ArrayList<Integer> packetsWinIds = new ArrayList<Integer>();
    public static ArrayList<Integer> missedPacketsWinIds = new ArrayList<Integer>();
    public static int reminder = 0;
    public static int acksTimeOutInt;
    public static int packetsTimeOutInt;
    public static int endToEndDelayInt;
    public static int numberOfPacketsInt;
    public Pane container;
    public PathTransition pt;
    public Rectangle rectangle;
    public Text text;
    public Rectangle window;


    /**
     *
     * @param i
     * @param count
     *
     */
    public Packet(int i, int count) {

        labelsContainer = Main.labelsContainer;
        packetsContainer = Main.packetsContainer;
        packetWithWin = Main.packetWithWin;
        slidingWindowPacketContainer = Main.slidingWindowPacketContainer;
        acksTimeOutInt=Main.acksTimeOutInt;
        packetsTimeOutInt=Main.packetsTimeOutInt;
        endToEndDelayInt=Main.endToEndDelayInt;
        numberOfPacketsInt=Main.numberOfPacketsInt;
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
        pt.setDuration(Duration.seconds(endToEndDelayInt));
        pt.setPath(line);
        text = new Text("" + i);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rectangle, text);
        pt.setNode(stack);

        rectangle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pt.stop();
                text.setFill(Color.TRANSPARENT);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setDisable(true);
                labelsContainer.getChildren().add(new Label("--------Packet-----------X " + i));
                pt.setPath(line);
                KeyFrame mainkeyFrame = new KeyFrame(Duration.seconds(packetsTimeOutInt), ev -> {
                    text.setFill(Color.BLACK);
                    rectangle.setFill(Color.ORANGE);
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

                if (reminder == 0) {
                    slidingWindowPacketContainer.getChildren().add(window);
                }

                if (waitFor == i) {
                    if (missedPacketsWinIds.contains(i))
                        missedPacketsWinIds.clear();
                    waitFor++;
                    for (int y = waitFor; y < count; y++) {
                        if (packetsWinIds.contains(y))
                            waitFor=y+1;
                        else{
                            missedPacketsWinIds.add(y);
                        }
                    }
                    slidingPermission = true;
                } else {
                    slidingPermission = false;
                }

                /**
                 * Verbose Logic parameters
                 */
//                System.out.println("Packet : " + packetsWinIds.get(reminder));
//                System.out.println("Position : " + positions.get(reminder));
//                System.out.println("Waiting for : " + waitFor);
//                System.out.println("Permission : " + slidingPermission);

                if (slidingPermission ) {
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
//        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
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
