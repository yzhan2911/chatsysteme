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
    @Test 
    public void testdecouvert() throws UnknownHostException, InterruptedException{
             UDPsender udps = new UDPsender();
             UDPrecever udpr = new UDPrecever(1929);
            user userlocal = new  user(new contact("yu", InetAddress.getByName("10.10.10.2")));
             controller app =new controller(userlocal, udps,udpr);
            controllerDecouvert decou = new controllerDecouvert(app);
            decou.connexion(1129);
  }


}