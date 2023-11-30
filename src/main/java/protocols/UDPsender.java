package protocols;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * Classe simplifiée pour gérer l'envoi de messages UDP pour la phase 1.
 */
public class UDPsender {
    private DatagramSocket socket;

    // Constructor
    public UDPsender() {
        try {
            socket = new DatagramSocket();
            socket.setBroadcast(true);
        } catch (SocketException e) {
            System.err.println("Erreur lors de la création du socket");
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
            e.printStackTrace();
        }
    }

    // Méthode pour lister toutes les adresses de broadcast
    /*public static List<InetAddress> listAllBroadcastAddresses() throws SocketException {
        List<InetAddress> broadcastList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();

            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }

            networkInterface.getInterfaceAddresses().stream()
                    .map(a -> a.getBroadcast())
                    .filter(Objects::nonNull)
                    .forEach(broadcastList::add);
        }
        return broadcastList;
    }
*/
    // Fermer la connexion
    public void closeConnection() {
        if (!socket.isClosed()) {
            socket.close();
        }
    }
}
