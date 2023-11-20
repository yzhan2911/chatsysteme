package protocols;

import model.contact.contact;
import model.contact.etat;
import controller.controller;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;


public class UDPrecever extends Thread {
    private controller app;
    private DatagramSocket socket;
    private boolean stop;

    public UDPrecever(int port,controller app ) {
        this.app=app;
        this.stop = false;
        try {
            this.socket = new DatagramSocket(port); // Port pour écouter les messages 
        } catch (SocketException e) {
            System.err.println("Erreur lors de la création du socket UDP");
            e.printStackTrace();
        }
    }

    public void RépondreAuMessageDecouvert(contact user,InetAddress addressDes,int port) {
        String reponse="REPONSE_"+user.getUserName()+"_"+user.getUserIP()+"_"+user.getUserEtat();

        byte[] buffer= reponse.getBytes();
        try(DatagramSocket socket = new DatagramSocket()){
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addressDes, port);
        
            socket.send(packet);
                System.out.println("bien envoi:"+reponse+addressDes);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        int port =socket.getLocalPort();
        System.out.println("[UDPReceiver] Démarré et en écoute sur le port " + port);
        System.out.println(this.stop);
        while (!this.stop) {
            try {
                byte[] buffer = new byte[1024 ]; // Taille du buffer pour les paquets entrants
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
               
                socket.receive(datagramPacket);
              
                InetAddress addressSource = datagramPacket.getAddress();
                
            
                System.out.println(addressSource+"/"+port);
                boolean  Pas_self =true;
                Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
                for (NetworkInterface netint : Collections.list(nets)){
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                    for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                        if (addressSource.equals(inetAddress)){
                            Pas_self=false;
                        }
                    }
                    
                }
                if (Pas_self){
                // Traitement du paquet reçu
                    String receivedMessage = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                    System.out.println("Message new UDPrecever(PORT_DISCOVERY) reçu : " + receivedMessage);
               
                // user ajouter
                    if (receivedMessage.startsWith("DECOUVERTE_")){
                        System.out.println("Répond_au_message DECOUVERTE");
                        RépondreAuMessageDecouvert( this.app.getUser().getUserlocal(), addressSource, port);
                    }else if(receivedMessage.startsWith("REPONSE_")) {
                        System.out.println("Rucu le message REPONSE et traite les données");
                        String[] parts = receivedMessage.split("_");
                        if (parts.length>=4 && parts[0].equals("REPONSE")){
                             System.out.println("before ");
                            String name = parts[1];
                             System.out.println("1");
                            InetAddress ip = InetAddress.getByName(parts[2]);
                             System.out.println("2");
                            etat etatuser=etat.valueOf(parts[3]);
                             System.out.println("3");
                            contact useradd = new contact(name, ip);
                             System.out.println("5");
                            useradd.setUserEtat(etatuser);
                             System.out.println("6");
                            app.getUser().adduser(useradd);
                            System.out.println("bien connexion");
                            System.out.println(this.app.getUser().getUserlist());
                             System.out.println("after");
                        } 
                        else {
                            System.out.println("error de decouvert");
                        }
                    
                    Thread.sleep(1000);
                    }
                }
            }catch (IOException | InterruptedException e) {
                if (this.stop) {
                    System.out.println("[UDPReceiver] Arrêt du thread UDPReceiver.");
                } else {
                    e.printStackTrace();
                }
            }
        
        }

        socket.close();
    }

    public boolean isDispo(){
        return !this.stop;
    }

    public void setController(controller app){
        this.app=app;
    }
    public void stopReceiver() {
        this.stop = true;
        if (this.socket != null && !this.socket.isClosed()) {
            this.socket.close();
        }
    }
}
