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

public class Acknowledgement {

    public static VBox labelsContainer;
    public static GridPane packetsContainer;
    public static Pane packetWithWin;
    public static Pane slidingWindowAckContainer;
    public static int slidingFactor = 0;
    public static int lastReceivedAck=0;
    public static boolean slidingPermissionAck = false;
    public static int waitForAck = 0;
    public static ArrayList<Double> ackPositions = new ArrayList<Double>();
    public static ArrayList<Integer> acksWinIds = new ArrayList<Integer>();
    public static ArrayList<Integer> missedAcksWinIds = new ArrayList<Integer>();
    public static int reminderAck = 0;
    public static int acksTimeOutInt;
    public static int packetsTimeOutInt;
    public static int endToEndDelayInt;
    public static int numberOfPacketsInt;
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
        acksTimeOutInt=Main.acksTimeOutInt;
        packetsTimeOutInt=Main.packetsTimeOutInt;
        endToEndDelayInt=Main.endToEndDelayInt;
        numberOfPacketsInt=Main.numberOfPacketsInt;
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
        pt.setDuration(Duration.millis((endToEndDelayInt*1000)-500));
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
                text.setFill(Color.TRANSPARENT);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setDisable(true);
                labelsContainer.getChildren().add(new Label((i) + " X---------Ack------------- "));
                pt.setPath(line);
                Packet packet = new Packet(i, count);
                packet.getTextOnPacket().setFill(Color.TRANSPARENT);
                packet.getRectangle().setFill(Color.TRANSPARENT);
                packetsContainer.add(packet.getContainer(), i, 0);
                KeyFrame mainkeyFrame = new KeyFrame(Duration.seconds(acksTimeOutInt), ev -> {
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
                ackPositions.add(-15.0 + (slidingFactor * 35));
                acksWinIds.add(i);

                if (reminderAck == 0) {
                    slidingWindowAckContainer.getChildren().add(window);
                }

                if (waitForAck == i) {
                    if (missedAcksWinIds.contains(i))
                        missedAcksWinIds.clear();
                    waitForAck++;
                    for (int y = waitForAck; y < count; y++) {
                        if (acksWinIds.contains(y))
                            waitForAck=y+1;
                        else{
                            missedAcksWinIds.add(y);
                        }
                    }
                    slidingPermissionAck = true;
                } else {
                    slidingPermissionAck = false;
                }

                /**
                 * Verbose Logic parameters
                 */
//                System.out.println("Packet : " + packetsWinIds.get(reminder));
//                System.out.println("Position : " + positions.get(reminder));
                System.out.println("Waiting for Ack: " + waitForAck);
//                System.out.println("Permission : " + slidingPermission);

                if (slidingPermissionAck ) {
                    window.setX(-15 + (slidingFactor * 35));
                }

                reminderAck++;
                lastReceivedAck=i;
                if (slidingPermissionAck) {
                    slidingWindowAckContainer.getChildren().clear();
                    slidingWindowAckContainer.getChildren().add(window);
                }
                if(i==lastReceivedAck+1) {
                    window.setX(-15 + (slidingFactor * 35));
                }
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
