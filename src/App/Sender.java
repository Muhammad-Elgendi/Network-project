package App;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Sender {

    public static ArrayList<Packet> packets = new ArrayList<Packet>();
    public static ArrayList<Acknowledgement> acknowledgements;
    public static VBox labelsContainer;
    public static VBox sentPackets;
    public static VBox lossPackets;
    public static GridPane packetsContainer;
    public Timeline timeline;
    public  KeyFrame keyFrame;
    public Sender(){
        sentPackets = new VBox();
        lossPackets = new VBox();
        acknowledgements=Receiver.acknowledgements;
        labelsContainer=Main.labelsContainer;
        packetsContainer = Main.packetsContainer;
        timeline = new Timeline();
    }

    public void createNewPacket(int i){
        packets.add(i,new Packet(i));
    }

    public Packet getPacket(int i){
        return packets.get(i);
    }

    public PathTransition getPathTranstion(int i){
        return packets.get(i).getPt();
    }

//    public void setEventFinished(int i){
//        packets.get(i).getPt().setOnFinished(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                labelsContainer.getChildren().add(new Label("------------------> Packet " + i));
//                sentPackets.add(i,i);
//            }
//        });
//    }

//    public void setEventStopped(int i){
//        packets.get(i).getRectangle().setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                packets.get(i).getPt().pause();
//                labelsContainer.getChildren().add(new Label("-------------------------X " + i));
//                lossPackets.add(i,i);
//            }
//        });
//    }

    public boolean is_lost(int i){
        for (Node object:lossPackets.getChildren()) {
            if(Integer.parseInt( lossPackets.getChildren().get(i).getAccessibleText())==i)
                return true;
        }
        return false;
    }

//    public boolean is_sent(int i){
//        for (Integer object:sentPackets) {
//            if(sentPackets.get(object)==i)
//                return true;
//        }
//        return false;
//    }

    public void sendPacket(int i){
//        if(!is_sent(i)) {
            packetsContainer.add(packets.get(i).getContainer(), i, 0);
            packets.get(i).getPt().play();
//        }
    }

//    public void movePacket(int i) {
//        if(!is_sent(i)){
//            keyFrame= new KeyFrame(Duration.seconds(6), ev -> {
//                sendPacket(i);
//            });
//            timeline.getKeyFrames().setAll(keyFrame);
//            timeline.setCycleCount(1);
//            timeline.play();
//        }
//    }

//    public void printArrayList(int i){
//
//        System.out.println(sentPackets.getChildren().contains(new Label(""+i)));
//
//    }

}
