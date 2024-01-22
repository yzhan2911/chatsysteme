package protocols;
import java.net.*;
import java.io.*;

public class TCPsender extends Thread {

        private Socket clientSocket;
        private OutputStream out;
        private  DataOutputStream dos;
    
        public synchronized void envoyermessage(InetAddress ip, int port ,String msg) throws UnknownHostException, IOException {
            clientSocket = new Socket(ip, port);
            try {out = clientSocket.getOutputStream();
            dos = new DataOutputStream(out);
            dos.writeUTF(msg); 
            }finally{
                closeResources();
            }
        }
        
        public void stopConnection() throws IOException {
            closeResources();
        }
    
        private void closeResources() throws IOException {
            if (dos != null) {
                dos.close();
            }
            if (out != null) {
                out.close();
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        }
}
