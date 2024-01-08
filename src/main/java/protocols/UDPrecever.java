package protocols;

import model.BaseDeDonnee;
import model.user;
import model.contact.contact;
import model.contact.etat;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;


public class UDPrecever extends Thread {
    private user user;
    private DatagramSocket socket;
    private boolean stop;

    public UDPrecever(int port,user user ) {
        this.user=user;
        this.stop = false;
        try {
            this.socket = new DatagramSocket(port); 
        } catch (SocketException e) {
            System.err.println("[Protocols] UDPrecever: Erreur lors de la création du socket UDP");
            e.printStackTrace();
        }
    }

    public synchronized void RépondreAuMessageDecouvert(contact userlocal,InetAddress addressDes,int port) {
        String reponse="REPONSE_"+userlocal.getUserName()+"_"+userlocal.getUserIP()+"_"+userlocal.getUserEtat();
        byte[] buffer= reponse.getBytes();
        try(DatagramSocket socket = new DatagramSocket()){
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addressDes, port);
            socket.send(packet);
            System.out.println("[Protocols] UDPrecever: bien envoi:"+reponse+addressDes);
        }catch (IOException e){
            System.err.println("[Protocols] UDPrecever: Erreur quand recever répond son propre donnée");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int port =socket.getLocalPort();
        System.out.println("[Protocols] UDPrecever: Démarré et en écoute sur le port " + port);
        while (!this.stop) {
            try {
                byte[] buffer = new byte[1024 ]; 
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);               
                socket.receive(datagramPacket);              
                InetAddress addressSource = datagramPacket.getAddress();            
                System.out.println(addressSource+"/"+port);

                // éviter de rajouter lui meme
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
                    System.out.println("[Protocols] UDPrecever: message decouvert reçu : " + receivedMessage);

                    //Dans le cas de déconnection, enlever le user dans la liste
                    if(receivedMessage.startsWith("DECONNECT")){
                        String[] parts = receivedMessage.split("_");
                        String name = parts[1];
                        String ipAddressString = parts[2].substring(parts[2].indexOf("/") + 1);
                        InetAddress ip = InetAddress.getByName(ipAddressString);
                        contact useremo = new contact(name, ip);
                        user.removeuser(useremo);
                    }

                    // Dans le cas de connexion, recever Traitement le paquet et envoyer son donné 
                    else if (receivedMessage.startsWith("DECOUVERTE_")){
                        System.out.println("[Protocols] UDPrecever: Répond_au_message DECOUVERTE");
                        String[] parts2 = receivedMessage.split("_");
                        if (parts2.length>=4){                            
                            String name = parts2[1];                 
                            String ipAddressString = parts2[2].substring(parts2[2].indexOf("/") + 1);
                            InetAddress ip = InetAddress.getByName(ipAddressString);                        
                            etat etatuser=etat.valueOf(parts2[3]);        
                            contact useradd = new contact(name, ip);
                            useradd.setUserEtat(etatuser);
                            this.user.adduser(useradd);
                        }
                        RépondreAuMessageDecouvert( this.user.getUserlocal(), addressSource, port);
                    }

                    //Dans le cas de connexion, sender Traitement le paquet de recever 
                    else if(receivedMessage.startsWith("REPONSE_")) {
                        System.out.println("[Protocols] UDPrecever: Rucu le message REPONSE et traite les données");
                        String[] parts = receivedMessage.split("_");
                        if (parts.length>=4 && parts[0].equals("REPONSE")){
                            String name = parts[1];
                            String ipAddressString = parts[2].substring(parts[2].indexOf("/") + 1);
                            InetAddress ip = InetAddress.getByName(ipAddressString);
                            etat etatuser=etat.valueOf(parts[3]);
                            contact useradd = new contact(name, ip);
                            useradd.setUserEtat(etatuser);
                            this.user.adduser(useradd);
                            System.out.println("[Protocols] UDPrecever: bien connexion");
                            System.out.println(this.user.getUserlist());
                        }
                        else {
                            System.err.println("[Protocols] UDPrecever: error de decouvert");
                        }
                    }
                    
                    //Dans le cas de changement de name
                    else if(receivedMessage.startsWith("CHANGEDNAME")){
                        String[] parts = receivedMessage.split("_");
                        String newname = parts[1];
                        String ipAddressString = parts[2].substring(parts[2].indexOf("/") + 1);
                        InetAddress ip = InetAddress.getByName(ipAddressString);
                        BaseDeDonnee.changerUserName(this.user.getUserbyip(ip).getUserName(), newname);
                        this.user.getUserbyip(ip).setUserName(newname);
                    }
                }
            }catch (IOException e) {
                if (this.stop) {
                    System.out.println("[Protocols] UDPrecever: Arrêt du thread UDPReceiver.");
                } else {
                    System.out.println("[Protocols] UDPrecever: error d'arrêt du thread UDPReceiver.");
                    e.printStackTrace();
                }
            }
        }
        socket.close();
    }

    public void stopReceiver() {
        this.stop = true;
        if (this.socket != null && !this.socket.isClosed()) {
            this.socket.close();
        }
    }
}
