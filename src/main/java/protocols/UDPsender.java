package protocols;

import java.io.*;
import java.net.*;

public class UDPsender {
    private DatagramSocket socket;

    // Constructor
    public UDPsender() {
        try {
            socket = new DatagramSocket();
            socket.setBroadcast(true);
        } catch (SocketException e) {
            System.err.println("[Protocols] UDPsender: Erreur lors de la création du socket");
            e.printStackTrace();
        }
    }

    // Méthode pour envoyer un message broadcast UDP
    public void sendBroadcast(String message, int port) {
        try {
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), port);
            socket.send(packet);
        } catch (IOException e) {
            System.err.println("[Protocols] UDPsender: Erreur de sendBroadcast");
            e.printStackTrace();
        }
    }
    
    // Fermer la connexion
    public void closeConnection() {
        if (!socket.isClosed()) {
            socket.close();
        }
    }
}
