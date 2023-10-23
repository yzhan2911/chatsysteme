package protocols;

import java.io.IOException;
import java.net.*;
import java.net.*;

public class UDPreceiver extends  Thread{
   private  int port;
   private boolean begin=false;
    private DatagramSocket socket;
public UDPreceiver(String name) {
    super(name);
}

public void beginning(){
    this.begin=true;
}
public void run(){
    try {
        this.socket= new DatagramSocket(port);
    } catch (SocketException e) {
        throw new RuntimeException(e);
    }
    while(begin){

        byte[] buf = new byte[1024*5];
        try {
            DatagramPacket packet=new DatagramPacket(buf, buf.length,InetAddress.getByName(getName()),port );
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

    }
}
}