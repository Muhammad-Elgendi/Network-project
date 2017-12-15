package App;

import java.util.ArrayList;

public class Receiver {
    public static ArrayList<Acknowledgement> acknowledgements;
    public static ArrayList<Packet> packets;

    public Receiver(){
        packets=Sender.packets;
        acknowledgements = new ArrayList<Acknowledgement>();
    }

}
