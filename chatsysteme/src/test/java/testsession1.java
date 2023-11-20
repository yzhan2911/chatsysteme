import java.net.InetAddress;
import java.net.UnknownHostException;

import controller.controller;
import controller.controllerDecouvert;
import model.user;
import model.contact.contact;
import protocols.*;
public class testsession1 {
public static void main(String[] args) throws UnknownHostException, InterruptedException{
        UDPsender udps = new UDPsender();
        UDPrecever udpr = new UDPrecever(1129); 
        user userlocal = new  user(new contact("yu", InetAddress.getByName("10.10.10.1")));
        controller app =new controller(userlocal, udpr, udps);
        controllerDecouvert decou = new controllerDecouvert(app);
        decou.connexion(1129);

        udpr.stopReceiver();
}
}