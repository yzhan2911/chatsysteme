

import model.user;
import model.contact.contact;
import protocols.UDPrecever;
import protocols.UDPsender;

import java.net.InetAddress;
import java.net.UnknownHostException;

import controller.controller;
import controller.controllerDecouvert;

public class ChatApplication {

    public static final int PORT_DISCOVERY = 1929;

    public static void main(String[] args) throws UnknownHostException, InterruptedException  {
            user userlocal = new  user(new contact("yuu5452", InetAddress.getLocalHost()));
            controller app =new controller(userlocal, new UDPsender(), PORT_DISCOVERY);
           
            controllerDecouvert decou = new controllerDecouvert(app);
            decou.connexion(PORT_DISCOVERY);    
    }
}
