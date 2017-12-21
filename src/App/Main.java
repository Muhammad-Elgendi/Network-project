package App;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    public static VBox labelsContainer;
    public static GridPane packetsContainer;
    public static Pane packetWithWin;
    public static Pane slidingWindowPacketContainer;
    public static Pane slidingWindowAckContainer;
    public static int acksTimeOutInt;
    public static int packetsTimeOutInt;
    public static int endToEndDelayInt;
    public static int numberOfPacketsInt;
    public Button startButton;
    public Button stopButton;
    public TextField endToEndDelay;
    public TextField numberOfPackets;
    public TextField windowSizeTextField;
    public TextField packetsTimeOut;
    public TextField acksTimeOut;

    public static void main(String[] args) {
        launch(args);
    }

    @Override

    public void start(Stage primaryStage) throws Exception {
        /**
         * Main Component Properties
         */
        HBox root = new HBox();
        BorderPane timeLine = new BorderPane();
        BorderPane viewer = new BorderPane();
        viewer.setPrefWidth(800);
        timeLine.setPrefWidth(400);

        /**
         * Time Line Component
         */

        /**
         * Leftline
         */
        Line leftLine = new Line();
        leftLine.setStartX(0.0f);
        leftLine.setStartY(0.0f);
        leftLine.setEndX(0.0f);
        leftLine.setEndY(560.0f);

        /**
         * RightLine
         */
        Line rightLine = new Line();
        rightLine.setStartX(0.0f);
        rightLine.setStartY(0.0f);
        rightLine.setEndX(0.0f);
        rightLine.setEndY(560.0f);

        /**
         * LeftLine Container
         */
        Label Source = new Label("Source");
        HBox leftLineContainer = new HBox();
        leftLineContainer.setPadding(new Insets(0, 0, 0, 15));
        leftLineContainer.getChildren().add(leftLine);

        /**
         * Left Container
         */
        VBox leftContainer = new VBox();
        leftContainer.setAlignment(Pos.TOP_CENTER);
        leftContainer.setAlignment(Pos.TOP_CENTER);
        leftContainer.setSpacing(10);
        leftContainer.setPadding(new Insets(10, 30, 0, 20));
        leftContainer.getChildren().add(Source);
        leftContainer.getChildren().add(leftLineContainer);

        /**
         * Rightline Container
         */
        Label Destination = new Label("Destination");
        HBox rightLineContainer = new HBox();
        rightLineContainer.setPadding(new Insets(0, 0, 0, 30));
        rightLineContainer.getChildren().add(rightLine);

        /**
         * Right Container
         */
        VBox rightContainer = new VBox();
        rightContainer.setPadding(new Insets(10, 20, 0, 30));
        rightContainer.setAlignment(Pos.TOP_CENTER);
        rightContainer.setSpacing(10);
        rightContainer.getChildren().add(Destination);
        rightContainer.getChildren().add(rightLineContainer);

        /**
         * Labels Container
         */
        ScrollPane scroll =new ScrollPane();

        scroll.setPannable(true);
        labelsContainer = new VBox();
        labelsContainer.setPadding(new Insets(40, 0, 0, 0));
        labelsContainer.setSpacing(10);
//        labelsContainer.setStyle("-fx-background-color:  #E0E0E0;");
        scroll.setContent(labelsContainer);
//        scroll.setStyle("-fx-background-color:  #E0E0E0;");
//        scroll.setStyle("-fx-border-color:  transparent;");


        /**
         * Simulation Key
         */
        GridPane simKeys =new GridPane();
        simKeys.setPadding(new Insets(2, 0, 3, 0));
        simKeys.setAlignment(Pos.BOTTOM_CENTER);
        simKeys.setVgap(3);
        simKeys.setHgap(3);
        Label key1 =new Label("Packet : ");
        Label key2 =new Label("Ack      : ");
        Rectangle rectangle1 = new Rectangle(0, 0, 25, 25);
        rectangle1.setFill(Color.ORANGE);
        Rectangle rectangle2 = new Rectangle(0, 0, 25, 25);
        rectangle2.setFill(Color.GREEN);
        simKeys.add(key1,0,0);
        simKeys.add(rectangle1,1,0);
        simKeys.add(key2,0,1);
        simKeys.add(rectangle2,1,1);

        /**
         * timeline Component Set
         */
//        timeLine.setStyle("-fx-background-color:  #E0E0E0;");
        timeLine.setLeft(leftContainer);
        timeLine.setRight(rightContainer);
        timeLine.setCenter(scroll);
        timeLine.setBottom(simKeys);

        /**
         * Viewer Component
         */

        /**
         * Upper area (Input of simulation)
         */
        Label windowSizeLabel = new Label("Window Size");
        Label endToEndDelayLabel =new Label("End To End Delay");
        Label numberOfPacketsLabel =new Label("Number Of Packets");
        Label packetsTimeOutLabel =new Label("Sender timeout");
        Label acksTimeOutLabel =new Label("Receiver timeout");

        acksTimeOut= new TextField();
        acksTimeOut.setPrefWidth(100);
        packetsTimeOut =new TextField();
        packetsTimeOut.setPrefWidth(100);
        windowSizeTextField = new TextField();
        windowSizeTextField.setPrefWidth(100);
        endToEndDelay = new TextField();
        endToEndDelay.setPrefWidth(100);
        numberOfPackets = new TextField();
        numberOfPackets.setPrefWidth(100);

        startButton = new Button("Start");
        stopButton = new Button("Stop");

        VBox windowSizeBox =new VBox();
        windowSizeBox.setAlignment(Pos.TOP_CENTER);
        windowSizeBox.setSpacing(5);
        windowSizeBox.getChildren().addAll(windowSizeLabel,windowSizeTextField);

        VBox acksTimeOutBox =new VBox();
        acksTimeOutBox.setAlignment(Pos.TOP_CENTER);
        acksTimeOutBox.setSpacing(5);
        acksTimeOutBox.getChildren().addAll(acksTimeOutLabel,acksTimeOut);

        VBox packetsTimeOutBox =new VBox();
        packetsTimeOutBox.setAlignment(Pos.TOP_CENTER);
        packetsTimeOutBox.setSpacing(5);
        packetsTimeOutBox.getChildren().addAll(packetsTimeOutLabel,packetsTimeOut);

        VBox endToEndDelayBox =new VBox();
        endToEndDelayBox.setAlignment(Pos.TOP_CENTER);
        endToEndDelayBox.setSpacing(5);
        endToEndDelayBox.getChildren().addAll(endToEndDelayLabel,endToEndDelay);

        VBox numberOfPacketsBox =new VBox();
        numberOfPacketsBox.setAlignment(Pos.TOP_CENTER);
        numberOfPacketsBox.setSpacing(5);
        numberOfPacketsBox.getChildren().addAll(numberOfPacketsLabel,numberOfPackets);

        HBox upperArea = new HBox();
        upperArea.setAlignment(Pos.BOTTOM_CENTER);
        upperArea.setPadding(new Insets(10, 0, 0, 0));
        upperArea.setSpacing(10);
        upperArea.getChildren().addAll(windowSizeBox,numberOfPacketsBox,endToEndDelayBox,acksTimeOutBox,packetsTimeOutBox, startButton,stopButton);

        windowSizeTextField.setText("5");
        acksTimeOut.setText("12");
        packetsTimeOut.setText("12");
        endToEndDelay.setText("6");
        numberOfPackets.setText("10");

        /**
         * Set Packets View
         */

        packetWithWin = new Pane();
        slidingWindowPacketContainer = new Pane();
        slidingWindowAckContainer = new Pane();
        packetWithWin.getChildren().add(slidingWindowPacketContainer);
        packetWithWin.getChildren().add(slidingWindowAckContainer);
        packetsContainer = new GridPane();
        packetsContainer.setHgap(10);

        /**
         * Packet Path and Component
         */
//        windowCoord = new Point2D(startButton.getScene().getWindow().getX(), startButton.getScene().getWindow().getY());
//        sceneCoord = new Point2D(startButton.getScene().getX(), startButton.getScene().getY());
//       System.out.println(stopButton.);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                packetWithWin.getChildren().add(packetsContainer);
                int count = Integer.parseInt(windowSizeTextField.getText());
                acksTimeOutInt=Integer.parseInt(acksTimeOut.getText());
                packetsTimeOutInt=Integer.parseInt(packetsTimeOut.getText());
                numberOfPacketsInt=Integer.parseInt(numberOfPackets.getText());
                endToEndDelayInt=Integer.parseInt(endToEndDelay.getText());



//                ArrayList<Packet> packets = new ArrayList<Packet>();
//                ArrayList<Acknowledgement> acknowledgements = new ArrayList<Acknowledgement>();
//                for (i = 0; i < count;i++ ) {
//                    packets.add(new Packet());
//                    packetsContainer.add(packets.get(i).getContainer(), i, 0);
//                    packets.get(i).getPt().play();
//
//                    packets.get(i).getPt().setOnFinished(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event) {
//
//                            System.out.println("Packet Received !");
//                            labelsContainer.getChildren().add(new Label("Packet " + i));
//                            System.out.println(i);
////                            acknowledgements.add(new Acknowledgement());
////                            packetsContainer.add(acknowledgements.get(i).getContainer(), i, 0);
////                            acknowledgements.get(i).getPt().play();
////                            acknowledgements.get(i).getPt().setOnFinished(new EventHandler<ActionEvent>() {
////                                @Override
////                                public void handle(ActionEvent event) {
//
////                                    labelsContainer.getChildren().add(new Label("Ack " + i));
////                                }
////                            });
//                        }
//                    });
//                }

//                Timer timer = new Timer();
//                TimerTask timerTask = new TimerTask() {
//                    int counter = 0;
//                    @Override
//                    public void run() {
//                        System.out.println("Second passed !");
//                        packets.add(new Packet());
//                        packetsContainer.add(packets.get(counter).getContainer(), counter, 0);
//                        packets.get(counter).getPt().play();
//                        counter++;
//                        if(counter == count) {
//                            timer.cancel();
//                        }
//                    }
//                };
//                timer.scheduleAtFixedRate(timerTask, 1000, 1000);
//                final int[] counter = {0};
//                Timeline timelineTimer = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
//                    System.out.println("Second passed !");
//                    packets.add(new Packet());
//                    packetsContainer.add(packets.get(counter[0]).getContainer(), counter[0], 0);
//                    packets.get(counter[0]).getPt().play();
//                    packets.get(counter[0]).getPt().setOnFinished(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event) {
//
//                            System.out.println("Packet Received !");
//                            labelsContainer.getChildren().add(new Label("Packet " + counter[0]));
//                            System.out.println(counter[0]);
//                            acknowledgements.add(new Acknowledgement());
//                            packetsContainer.add(acknowledgements.get(counter[0]).getContainer(), counter[0], 0);
//                            acknowledgements.get(counter[0]).getPt().play();
//
//
//                            acknowledgements.get(counter[0]).getPt().setOnFinished(new EventHandler<ActionEvent>() {
//                                @Override
//                                public void handle(ActionEvent event) {
//
//                                    labelsContainer.getChildren().add(new Label("Ack " + counter[0]));
//                                }
//                            });
//                            counter[0]++;
//                        }
//                    });
//
//                }));
//                timelineTimer.setCycleCount(count);
//
//                timelineTimer.play();
                startButton.setDisable(true);
                windowSizeTextField.setDisable(true);
                endToEndDelay.setDisable(true);
                numberOfPackets.setDisable(true);
                packetsTimeOut.setDisable(true);
                acksTimeOut.setDisable(true);
                Sender sender = new Sender();
                final int[] counter = {0};
                KeyFrame mainkeyFrame = new KeyFrame(Duration.seconds(endToEndDelayInt), ev -> {
                    sender.createNewPacket(counter[0], count);
//                    sender.setEventFinished(counter[0]);
//                    sender.setEventStopped(counter[0]);
                    sender.sendPacket(counter[0]);
//                    sender.printArrayList(0);

                    counter[0]++;
                });
                Timeline timelineTimer = new Timeline(mainkeyFrame);
                timelineTimer.setCycleCount(numberOfPacketsInt);
                timelineTimer.play();
            }
        });



        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                packetWithWin.getChildren().clear();
//                startButton.setDisable(false);
//                windowSizeTextField.setDisable(false);
//                endToEndDelay.setDisable(false);
//                numberOfPackets.setDisable(false);
                System.exit(0);
            }
        });

        /**
         * Viewer Component Set
         */
        viewer.setTop(upperArea);
        viewer.setCenter(packetWithWin);

        /**
         * Set the scene
         */
        root.getChildren().add(timeLine);
        root.getChildren().add(viewer);
//        root.setStyle("-fx-background-color:  #E0E0E0");
        primaryStage.setTitle("Selective Repeat");
        primaryStage.setScene(new Scene(root, 1200, 655));
        primaryStage.show();
    }


}
