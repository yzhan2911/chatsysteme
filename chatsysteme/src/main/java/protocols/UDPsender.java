package protocols;
//reference https://www.baeldung.com/java-broadcast-multicast
import java.io.IOException;
import java.net.*;
import java.util.*;

public class UDPsender extends  Thread {
    private DatagramSocket socket;
    private int port;
    //construteur
    public UDPsender (String id ) throws SocketException {
        super(id);
        System.out.println("[UDP] hello , i am "+this.getId());
        this.socket = new DatagramSocket(port);
    }

   public static List<InetAddress> listAllBroadcastAddresses() throws SocketException {
        List<InetAddress> broadcastList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();//network connection tout
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

    public static  void sendUDP(String broadcastMessage, InetAddress address,int port) throws IOException{
        DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] buffer = broadcastMessage.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
        socket.close();
    }
    
    public List<InetAddress> addMesDes(InetAddress ipdes) throws SocketException{
        List<InetAddress> list;
        list = listAllBroadcastAddresses();
        list.add(ipdes);
        return list;
    }
    
    public List<InetAddress>enlMesDes(InetAddress ipdes) throws SocketException{
        List<InetAddress> list;
        list = listAllBroadcastAddresses();
        list.remove(ipdes);
        return list;
    }
    
    public static void broadcast(String broadcastMessage, int port) throws IOException {
        List<InetAddress> listaddr= listAllBroadcastAddresses();
        for (InetAddress addr :  listaddr){
            sendUDP(broadcastMessage,addr,port);
        }
        
    }
}


