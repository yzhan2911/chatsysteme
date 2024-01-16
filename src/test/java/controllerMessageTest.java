

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.controllerMessage;
import model.BaseDeDonnee;
import model.user;
import model.contact.contact;


public class controllerMessageTest  {

    private user testUser1;
    private contact user1;
    private controllerMessage testControllerMessage1;
  
    public static final int PORT_COMMUNICATION = 1650;

    @Before
    public void setUp() throws IOException {
        this.user1 = new contact("User1", InetAddress.getByName("127.0.0.1"));
        this.testUser1 = new user(user1);
        System.out.println("user cree");
        this.testControllerMessage1 = new controllerMessage(testUser1, PORT_COMMUNICATION);
        this.testControllerMessage1.getBdd().create_new_basededonne(); // la base de données est créée
        this.testControllerMessage1.getBdd().create_new_table(); //  la table est créée
        System.out.println("BDD");
        this.testControllerMessage1.connexion(); // Démarrer le TCPrecever
        System.out.println("connexion");
        
    }

    @After
    public void tearDown() throws InterruptedException, IOException {
        // Arrêter le TCPrecever
        this.testControllerMessage1.getTcpr().stopConnection(); 
    }

  
      @Test
      public void testCommunicationBetweenUsers() throws InterruptedException {
          try {
              // Envoyer un message de user1 à lui meme 
                String message = "Hello, user2!";
                System.out.println(message);
                Date time = new Date();
                //   System.out.println(time);
                testControllerMessage1.envoyermsg(testUser1.getUserlocal().getUserName() + "_" + user1.getUserName() + "_" + time + "_" + message, user1.getUserIP(), time);
                Thread.sleep(1000);
            
                List<BaseDeDonnee.dataMessage> history = testControllerMessage1.getBdd().gethistory(testUser1.getUserlocal().getUserName(), user1.getUserName());
                //System.out.println(history);
                assertTrue("Le message envoyé n'a pas été correctement enregistré dans la base de données.", !history.isEmpty());

          } catch (IOException e) {
              e.printStackTrace();
              fail("Exception inattendue: " + e.getMessage());
          }
      }
  }
  

