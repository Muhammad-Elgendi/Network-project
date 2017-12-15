package App;

import javafx.animation.PathTransition;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Receiver {
    public static ArrayList<Acknowledgement> acknowledgements = new ArrayList<Acknowledgement>();
    public static ArrayList<Packet> packets;
    public static GridPane packetsContainer;
    public static VBox labelsContainer;
    public Receiver(){
        packets=Sender.packets;
    }

    public static void createNewAcknowlegement(int i,int count){
        acknowledgements.add(i,new Acknowledgement(i,count));
    }
    public static Acknowledgement getAcknowlegement(int i){
        return acknowledgements.get(i);
    }

    public static PathTransition getPathTranstion(int i){
        return acknowledgements.get(i).getPt();
    }

    public static void sendAck(int i){
        packetsContainer.add(acknowledgements.get(i).getContainer(), i, 0);
        acknowledgements.get(i).getPt().play();
    }
}
