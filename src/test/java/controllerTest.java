import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;



import org.junit.Test;

import controller.controller;
import model.user;
import model.contact.contact;


public class controllerTest {

    @Test
    public void testExistNickname() throws IOException {
       
        
        contact contact1 = new contact("contact1", InetAddress.getByName("127.0.0.1"));
        user testUser = new user(new contact("TestUser", InetAddress.getByName("127.0.0.2")));
        testUser.adduser(contact1);  

        // un contrôleur avec l'utilisateur de test
        controller testController = new controller(testUser, 1234, 5678);


        // méthode exist_nickname renvoie true pour l'utilisateur existant
        assertTrue(testController.exist_nickname("contact1"));

        // méthode exist_nickname renvoie false pour un utilisateur inexistant
        assertFalse(testController.exist_nickname("NonExistingUser"));
    }
}
