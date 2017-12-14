package App;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override

    public void start(Stage primaryStage) throws Exception{
        /**
         * Main Component Properties
         */
        HBox root =new HBox();
        BorderPane timeLine =new BorderPane();
        BorderPane viewer =new BorderPane();
        viewer.setPrefWidth(700);
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
        leftLine.setEndY(550.0f);

        /**
         * RightLine
         */
        Line rightLine = new Line();
        rightLine.setStartX(0.0f);
        rightLine.setStartY(0.0f);
        rightLine.setEndX(0.0f);
        rightLine.setEndY(550.0f);

        /**
         * LeftLine Container
         */
        Label Source =new Label("Source");
        HBox leftLineContainer =new HBox();
        leftLineContainer.setPadding(new Insets(0, 0, 0, 15));
        leftLineContainer.getChildren().add(leftLine);

        /**
         * Left Container
         */
        VBox leftContainer =new VBox();
        leftContainer.setAlignment(Pos.TOP_CENTER);
        leftContainer.setAlignment(Pos.TOP_CENTER);
        leftContainer.setSpacing(10);
        leftContainer.setPadding(new Insets(10, 30, 10, 30));
        leftContainer.getChildren().add(Source);
        leftContainer.getChildren().add(leftLineContainer);

        /**
         * Rightline Container
         */
        Label Destination =new Label("Destination");
        HBox rightLineContainer =new HBox();
        rightLineContainer.setPadding(new Insets(0, 0, 0, 30));
        rightLineContainer.getChildren().add(rightLine);

        /**
         * Right Container
         */
        VBox rightContainer =new VBox();
        rightContainer.setPadding(new Insets(10, 30, 10, 30));
        rightContainer.setAlignment(Pos.TOP_CENTER);
        rightContainer.setSpacing(10);
        rightContainer.getChildren().add(Destination);
        rightContainer.getChildren().add(rightLineContainer);

        /**
         * Labels Container
         */
        VBox labelsContainer = new VBox();
        labelsContainer.setPadding(new Insets(40, 0, 0, 0));
        labelsContainer.setSpacing(10);
        Label packet =new Label("------------------> Packet 1");
        Label packet1 =new Label("Ack 1 <----------------------");
        Label packet2 =new Label("-------------------------------X");
        Label packet3 =new Label("X-------------------------------");
        labelsContainer.getChildren().addAll(packet,packet1,packet2,packet3);

        /**
         * timeline Component Set
         */
        timeLine.setLeft(leftContainer);
        timeLine.setRight(rightContainer);
        timeLine.setCenter(labelsContainer);

        /**
         * Viewer Component
         */

        /**
         * Upper area (Input of simulation)
         */
        Label windowSizeLabel =new Label("Window Size : ");
        TextField windowSizeTextField =new TextField();
        Button startButton =new Button("Start");
        HBox upperArea = new HBox();
        upperArea.setAlignment(Pos.TOP_CENTER);
        upperArea.setPadding(new Insets(10, 0, 0, 0));
        upperArea.setSpacing(30);
        upperArea.getChildren().addAll(windowSizeLabel,windowSizeTextField,startButton);
//        Label packetsCount =new Label("Packets Count : ");
        /**
         * Packet Path and Component
         */
        Rectangle rectangle = new Rectangle(0, 0, 25, 25);
        rectangle.setFill(Color.ORANGE);
        Line line =new Line();
        line.setStartX(0.0f);
        line.setStartY(50);
        line.setEndX(0.0f);
        line.setEndY(550.0f);
        line.setStroke(Color.TRANSPARENT);
        viewer.getChildren().addAll(line, rectangle);
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.millis(8000));
        pt.setPath(line);
        pt.setNode(rectangle);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.play();
        rectangle.setOnMousePressed(e -> pt.pause());
//        rectangle.setOnMouseReleased(e -> pt.play());

        /**
         * Viewer Component Set
         */
        viewer.setTop(upperArea);


        /**
         * Set the scene
         */
        root.getChildren().add(timeLine);
        root.getChildren().add(viewer);
        primaryStage.setTitle("Selective Repeat");
        primaryStage.setScene(new Scene(root, 1100, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }



}
