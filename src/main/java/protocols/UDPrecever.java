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

    public synchronized void RépondreAuMessageDecouvert(contact user,InetAddress addressDes,int port) {
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
                    if(receivedMessage.startsWith("DECONNECT")){
                        String[] parts = receivedMessage.split("_");
                        String name = parts[1];
                 
                        String ipAddressString = parts[2].substring(parts[2].indexOf("/") + 1);
                        InetAddress ip = InetAddress.getByName(ipAddressString);
        
                        contact useremo = new contact(name, ip);
                       
                        this.app.getUser().removeuser(useremo);
                       

                    }// user ajouter
                    else if (receivedMessage.startsWith("DECOUVERTE_")){
                        System.out.println("Répond_au_message DECOUVERTE");
                        String[] parts2 = receivedMessage.split("_");
                        if (parts2.length>=4){
                            
                            String name = parts2[1];
                 
                            String ipAddressString = parts2[2].substring(parts2[2].indexOf("/") + 1);
                            InetAddress ip = InetAddress.getByName(ipAddressString);
                        
                            etat etatuser=etat.valueOf(parts2[3]);
        
                            contact useradd = new contact(name, ip);
                     
                            useradd.setUserEtat(etatuser);
               
                            app.getUser().adduser(useradd);
                        }
                        RépondreAuMessageDecouvert( this.app.getUser().getUserlocal(), addressSource, port);
                        
                    }else if(receivedMessage.startsWith("REPONSE_")) {
                        System.out.println("Rucu le message REPONSE et traite les données");
                        String[] parts = receivedMessage.split("_");
                        if (parts.length>=4 && parts[0].equals("REPONSE")){
                            
                            String name = parts[1];
                 
                            String ipAddressString = parts[2].substring(parts[2].indexOf("/") + 1);
                            InetAddress ip = InetAddress.getByName(ipAddressString);
                        
                            etat etatuser=etat.valueOf(parts[3]);
        
                            contact useradd = new contact(name, ip);
                     
                            useradd.setUserEtat(etatuser);
               
                            app.getUser().adduser(useradd);
                            System.out.println("bien connexion");
                            System.out.println(this.app.getUser().getUserlist());
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

    public synchronized void setController(controller app){
        this.app=app;
    }
    public void stopReceiver() {
        this.stop = true;
        if (this.socket != null && !this.socket.isClosed()) {
            this.socket.close();
        }
    }
}
