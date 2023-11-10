package protocols;

import java.io.IOException;

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

public void closethreaud(){
    this.begin=false;
}
public void run(){
    try {
        this.socket= new DatagramSocket(port);
    } catch (SocketException e) {
        throw new RuntimeException(e);
    }
    while(begin){
        byte[] buf = new byte[2048];
        
        DatagramPacket packet= new DatagramPacket(buf, buf.length);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
       
      

    }


}
}