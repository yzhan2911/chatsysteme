import controller.controller;
import protocols.*;
public class testsession1 {
public static void main(String[] args){
        UDPsender udps = new UDPsender();
        UDPrecever udpr = new UDPrecever(1129); 
        controller app = new controller(null, udpr, udps);

        
        udpr.start();
        udps.sendBroadcast("client",1129);
        System.out.println("[SERVER] Broadcast envoyé");
    }
     
}