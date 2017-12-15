package App;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;

public class Sender {

    public static ArrayList<Packet> packets;
    public static ArrayList<Acknowledgement> acknowledgements;
    public static VBox labelsContainer;
    public static ArrayList<Integer> sentPackets = new ArrayList<Integer>();
    public static ArrayList<Integer> lossPackets = new ArrayList<Integer>();
    public static GridPane packetsContainer;
    public Timeline timeline;
    public  KeyFrame keyFrame;
    public Sender(){
        packets = new ArrayList<Packet>();
//        sentPackets
//        lossPackets = new ArrayList<Integer>();
        acknowledgements=Receiver.acknowledgements;
        labelsContainer=Main.labelsContainer;
        packetsContainer=Main.packetsContainer;
        timeline = new Timeline();
    }

    public void createNewPacket(int i){
        packets.add(i,new Packet());
    }

    public Packet getPacket(int i){
        return packets.get(i);
    }

    public PathTransition getPathTranstion(int i){
        return packets.get(i).getPt();
    }

    public void setEventFinished(int i){
        packets.get(i).getPt().setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                labelsContainer.getChildren().add(new Label("------------------> Packet " + i));
                sentPackets.add(i,i);
            }
        });
    }

    public void setEventStopped(int i){
        packets.get(i).getRectangle().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                packets.get(i).getPt().pause();
                labelsContainer.getChildren().add(new Label("-------------------------------X " + i));
                lossPackets.add(i,i);
            }
        });
    }

    public boolean is_lost(int i){
        for (Integer object:lossPackets) {
            if(lossPackets.get(object)==i)
                return true;
        }
        return false;
    }

    public boolean is_sent(int i){
        for (Integer object:sentPackets) {
            if(sentPackets.get(object)==i)
                return true;
        }
        return false;
    }

    public void sendPacket(int i){
        if(!is_sent(i)) {
            packetsContainer.add(packets.get(i).getContainer(), i, 0);
            packets.get(i).getPt().play();
        }
    }

    public void movePacket(int i) {
        if(!is_sent(i)){
            keyFrame= new KeyFrame(Duration.seconds(3), ev -> {
                sendPacket(i);
            });
            timeline.getKeyFrames().setAll(keyFrame);
            timeline.setCycleCount(1);
            timeline.play();
        }
    }

}
