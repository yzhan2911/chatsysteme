
import protocols.UDP;
import model.contact.contact;
import java.util.List;

public class testsession1 {

        public static void main(String[] args) {
            UDP udpDiscovery = new UDP(8887);
            udpDiscovery.listenForRequestsAndRespond();
        }


}

