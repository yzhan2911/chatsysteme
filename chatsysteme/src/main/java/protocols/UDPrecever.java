package protocols;

import model.contact.contact;
import model.contact.etat;
import controller.controller;

import java.io.*;
import java.net.*;

public class UDPrecever extends Thread {
    private controller app;
    private DatagramSocket socket;
    private boolean stop;

    public UDPrecever(int port) {
        this.stop = false;
        try {
            this.socket = new DatagramSocket(port); // Port pour écouter les messages UDP
        } catch (SocketException e) {
            System.err.println("Erreur lors de la création du socket UDP");
            e.printStackTrace();
        }
    }

    public void répondreaumessagedecouvert(String message,contact user,InetAddress addressDes,int port) {
        String reponse="REPONSE_"+user.getusername()+user.getuserip()+user.getuseretat();
        byte[] buffer= reponse.getBytes();
        try(DatagramSocket socket = new DatagramSocket()){
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addressDes, port);
            socket.send(packet);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        System.out.println("[UDPReceiver] Démarré et en écoute sur le port " + socket.getLocalPort());

        while (!this.stop) {
            try {
                byte[] buffer = new byte[1024 ]; // Taille du buffer pour les paquets entrants
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(datagramPacket);

                // Traitement du paquet reçu
                String receivedMessage = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("Message UDP reçu : " + receivedMessage);
                InetAddress addressSource = datagramPacket.getAddress();
                int port = datagramPacket.getPort();

                // user ajouter
                if (receivedMessage.startsWith("DECOUVERTE_")){
                    répondreaumessagedecouvert(receivedMessage, app.getuser().getUserlocal(), addressSource, port);
                }else if (receivedMessage.startsWith("REPONSE_")){
                    String[] parts = receivedMessage.split("_");
                    if (parts.length>=4 && parts[0].equals("REPONSE")){
                        String name = parts[1];
                        InetAddress ip = InetAddress.getByName(parts[2]);
                        etat etatuser=etat.valueOf(parts[3]);
                        contact useradd = new contact(name, ip);
                        useradd.setuseretat(etatuser);
                        app.getuser().adduser(useradd);
                    } 
                    else {
                        System.out.println("error de decouvert");
                    }
                    
                Thread.sleep(1000);
                }

            } catch (IOException | InterruptedException e) {
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

    public void stopReceiver() {
        this.stop = true;
        if (this.socket != null && !this.socket.isClosed()) {
            this.socket.close();
        }
    }
}
