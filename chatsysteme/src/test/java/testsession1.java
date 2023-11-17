
import protocols.UDP;
import model.contact.contact;
import java.util.List;

public class testsession1 {
    public static void main(String[] args) {

        UDP udpDiscovery = new UDP(8888);

        udpDiscovery.sendDiscoveryRequest();

        try {
            Thread.sleep(10000); // Attendre 10 secondes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<contact> discoveredContacts = udpDiscovery.getContacts();
        for (contact contact : discoveredContacts) {
            System.out.println("Découvert : " + contact);


        }
    }
}

