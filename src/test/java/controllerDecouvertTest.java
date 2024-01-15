import static org.junit.Assert.*;
import org.junit.Test;

import controller.controllerDecouvert;
import model.user;
import model.contact.contact;
import model.contact.etat;

import org.junit.Before;
import org.junit.After;
import java.net.InetAddress;



public class controllerDecouvertTest {
    private contact user1;
    private contact amie1;
    private user testUser;
    private user testamie1;
    private controllerDecouvert testControllerDecouvert1;
    private controllerDecouvert testControllerDecouvert2;
    private final int PORT_DISCOVERY = 1929;

    @Before
    public void prepare() {
        try {
            
            this.user1 = new contact("User1", InetAddress.getByName("127.0.0.1"));
            this.testUser = new user(user1);
            this.amie1 = new contact("Amie 1", InetAddress.getByName("127.0.0.2"));
            this.testamie1 = new user(amie1);
            testUser.adduser(amie1);
            testamie1.adduser(user1);
            this.testControllerDecouvert1 = new controllerDecouvert(testUser, PORT_DISCOVERY);
            this.testControllerDecouvert2 = new controllerDecouvert(testamie1, PORT_DISCOVERY);
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanUp() throws InterruptedException {
        testControllerDecouvert1.deconnexion(PORT_DISCOVERY);
        testControllerDecouvert2.deconnexion(PORT_DISCOVERY);
    }

    @Test
    public void testUpdateChangeName() {
    try {
       
        String oldUsername = testUser.getUserlocal().getUserName();
        testControllerDecouvert1.UpdateChangeName("NewName", PORT_DISCOVERY);
        // contact friend=testUser.getUserlist().getElementAt(0);
        Thread.sleep(1000);

        assertTrue("Le nom d'utilisateur local n'a pas été mis à jour.", testUser.getUserlocal().getUserName().equals(testamie1.getUserlist().getElementAt(0).getUserName()));

        // Revenez au nom d'utilisateur d'origine
        testControllerDecouvert1.UpdateChangeName(oldUsername, PORT_DISCOVERY);
        
        } catch (Exception e) {
            e.printStackTrace();
    }
    }


    @Test
    public void testConnexion() throws InterruptedException {
        try {

            testControllerDecouvert1.connexion(PORT_DISCOVERY);
            testControllerDecouvert2.connexion(PORT_DISCOVERY);
            Thread.sleep(1000);
            assertTrue("L'état de l'utilisateur local n'est pas CONNECTED après la connexion.", testUser.getUserlocal().getUserEtat() == etat.CONNECTED);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeconnexion() throws InterruptedException {
        try {
           
            testControllerDecouvert1.deconnexion(PORT_DISCOVERY);
            testControllerDecouvert2.deconnexion(PORT_DISCOVERY);
            assertTrue("L'état de l'utilisateur local n'est pas DISCONNECTED après la deconnexion.", testUser.getUserlocal().getUserEtat() == etat.DISCONNECTED);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
