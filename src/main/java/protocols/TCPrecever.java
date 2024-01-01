package protocols;

import java.net.*;

import controller.controllerMessage;

import java.io.*;

public class TCPrecever extends Thread{
        private ServerSocket serverSocket;

        public TCPrecever(int port) throws IOException{
            this.serverSocket=new ServerSocket(port);
            
        }
    @Override
        public void run() {

            while (true) {
                try {
                   
                    Socket clientSocket = serverSocket.accept();
                    
                    new ClientHandler(clientSocket).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    
        public void stopConnection() throws IOException {
            serverSocket.close();
        }

        class ClientHandler extends Thread {
            private Socket clientSocket;
        
            public ClientHandler(Socket socket) {
                this.clientSocket = socket;
            }
        @Override
            public void run() {
                try {
                    InputStream in =  clientSocket.getInputStream();
                    DataInputStream dis = new DataInputStream(in);
                    String clientMessage = dis.readUTF();
                    // update history et fenete text ici
                    controllerMessage.updateChatHistory();
                    dis.close();
                    clientSocket.close();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
}
