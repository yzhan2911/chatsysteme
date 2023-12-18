import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

import controller.controller;
import controller.controllerDecouvert;
import controller.controllerMessage;
import junit.framework.TestCase;
import model.user;
import model.contact.contact;
import protocols.*;
public class testsession1 extends TestCase{
  public static final int PORT_DISCOVERY = 1929;
  public static final int PORT_COMMUNICATION = 1650;
    @Test 
    public void testdecouvert() throws InterruptedException, IOException{
        user userlocal = new  user(new contact("yuu5452", InetAddress.getLocalHost()));
        controller app =new controller(userlocal, PORT_DISCOVERY,PORT_COMMUNICATION);
       
        controllerDecouvert decou = app.getConDecou();
        decou.connexion(PORT_DISCOVERY);    
  }

    @Test
    public void test() throws IOException{
      
      Thread tcp= new Thread(()->{
      TCPrecever tcpr;
      try {
        tcpr = new TCPrecever(PORT_COMMUNICATION);
        tcpr.start();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      });
       Thread tcp2 =new Thread(()->{ TCPsender tcps = new TCPsender();
      try {
        tcps.envoyermessage(InetAddress.getLocalHost(), PORT_DISCOVERY, "test Message");
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }});
     
    }
}