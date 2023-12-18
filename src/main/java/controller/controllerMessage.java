package controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import model.user;
import protocols.TCPrecever;
import protocols.TCPsender;

public class controllerMessage {
        private TCPrecever tcpr;
        private TCPsender tcps;
        private user userlocal;
        private int port;

        public controllerMessage(user userlocal,int port) throws IOException{
           this.port=port;
            this.tcpr=new TCPrecever(port) ;
           this.tcps=new TCPsender();
           this.userlocal=userlocal;

        }


        public void connexion() throws IOException{
           this.tcpr.start();
        }
        public void envoyermsg(String msg, InetAddress ipdes) throws UnknownHostException, IOException{
            this.tcps.envoyermessage(ipdes,port, msg);
        }
        
}
