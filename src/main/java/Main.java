
import java.net.UnknownHostException;


import view.Connexion;

public class Main {

    public static final int PORT_DISCOVERY = 1929;

    public static void main(String[] args) throws UnknownHostException, InterruptedException  {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        new Connexion();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            });
    







    }
}
