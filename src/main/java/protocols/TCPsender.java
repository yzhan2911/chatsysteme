package protocols;
import java.net.*;
import java.io.*;

public class TCPsender extends Thread {

        private Socket clientSocket;
        private OutputStream out;
        private  DataOutputStream dos;
    
        public void envoyermessage(InetAddress ip, int port ,String msg) throws UnknownHostException, IOException {
            clientSocket = new Socket(ip, port);
            out = clientSocket.getOutputStream();
            dos = new DataOutputStream(out);
            dos.writeUTF(msg); 
            }
        

    
        public void stopConnection() throws IOException {
            dos.close();
            out.close();
            clientSocket.close();
        }
    
    
}
