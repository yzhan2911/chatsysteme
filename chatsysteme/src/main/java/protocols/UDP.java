package protocols;

import model.contact.contact;
import java.net.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UDP {
    private DatagramSocket socket;
    private List<contact> contacts;
    private int port;

    public UDP(int port) {
        this.contacts = new ArrayList<>();
        this.port = port;
        try {
            socket = new DatagramSocket(port);
            socket.setBroadcast(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendDiscoveryRequest() {
        try {
            String discoveryMessage = "DISCOVER_REQUEST";
            byte[] buf = discoveryMessage.getBytes();

            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("255.255.255.255"), port);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenForResponses() {
        new Thread(() -> {
            try {
                long listenDuration = 10000; // Écoute pendant 10 secondes
                long startTime = System.currentTimeMillis();

                while (System.currentTimeMillis() - startTime < listenDuration) {
                    DatagramPacket responsePacket = new DatagramPacket(new byte[512], 512);
                    socket.receive(responsePacket);

                    String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
                    if (response.startsWith("DISCOVER_RESPONSE")) {
                        String contactName = response.split(":")[1];
                        InetAddress contactAddress = responsePacket.getAddress();
                        contacts.add(new contact(contactName, contactAddress));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public List<contact> getContacts() {
        return new ArrayList<>(contacts);
    }

    // Inclure le code existant de UdpDiscovery, puis ajouter :

    public void listenForRequestsAndRespond() {
        new Thread(() -> {
            try {
                DatagramSocket responseSocket = new DatagramSocket();
                while (true) {
                    DatagramPacket requestPacket = new DatagramPacket(new byte[512], 512);
                    socket.receive(requestPacket);

                    String request = new String(requestPacket.getData(), 0, requestPacket.getLength());
                    if (request.equals("DISCOVER_REQUEST")) {
                        String responseMessage = "DISCOVER_RESPONSE:YourName";
                        byte[] responseData = responseMessage.getBytes();
                        DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, requestPacket.getAddress(), requestPacket.getPort());
                        responseSocket.send(responsePacket);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}

