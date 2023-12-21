package controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import model.BaseDeDonnee;
import model.user;
import protocols.TCPrecever;
import protocols.TCPsender;

public class controllerMessage {
      private TCPrecever tcpr;
      private TCPsender tcps;
      private user userlocal;
      private int port;
      private BaseDeDonnee bdd;
      public controllerMessage(user userlocal,int port) throws IOException{
         this.port=port;
         this.tcpr=new TCPrecever(port) ;
         this.tcps=new TCPsender();
         this.userlocal=userlocal;
         this.bdd= new BaseDeDonnee();

      }


      public void connexion() throws IOException{
         this.tcpr.start();
      }
      public void envoyermsg(String msg, InetAddress ipdes, Date time) throws UnknownHostException, IOException{
         this.tcps.envoyermessage(ipdes,port, msg);
         bdd.addmessageData(userlocal.getUserlocal().getUserName(), userlocal.getUserbyip(ipdes).getUserName(), time, msg);
      }

      //get 
      public BaseDeDonnee getBdd() {
         return bdd;
      }
      public TCPrecever getTcpr() {
         return tcpr;
      }
      public TCPsender getTcps() {
         return tcps;
      }
      
}
