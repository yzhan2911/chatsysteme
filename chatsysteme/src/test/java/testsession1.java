import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

import controller.controller;
import controller.controllerDecouvert;
import junit.framework.TestCase;
import model.user;
import model.contact.contact;
import protocols.*;
public class testsession1 extends TestCase{
  public static final int PORT_DISCOVERY = 1929;
    @Test 
    public void testdecouvert() throws UnknownHostException, InterruptedException{
        user userlocal = new  user(new contact("yuu5452", InetAddress.getLocalHost()));
        controller app =new controller(userlocal, new UDPsender(), PORT_DISCOVERY);
       
        controllerDecouvert decou = new controllerDecouvert(app);
        decou.connexion(PORT_DISCOVERY);    
  }
}