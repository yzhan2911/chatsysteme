

import model.user;
import model.contact.contact;
import protocols.UDPrecever;
import protocols.UDPsender;

import java.net.InetAddress;
import java.net.UnknownHostException;

import controller.controller;
import controller.controllerDecouvert;
import view.Connexion;

public class ChatApplication {

    public static final int PORT_DISCOVERY = 1929;

    public static void main(String[] args) throws UnknownHostException, InterruptedException  {
    
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        new Connexion();
                    } catch (UnknownHostException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
    







    }
}
