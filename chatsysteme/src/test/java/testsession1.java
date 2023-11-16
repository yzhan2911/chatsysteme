import contact.contact;
import org.junit.Before;

import java.net.*;

public class testsession1 {
    contact userlocal;
    contact userajout;
    @Before
    public  void prepa(){
        try {
                this.userlocal = new contact("local", InetAddress.getLocalHost());
                this.userajout =new contact("ajout",InetAddress.getByName("10.10.10.10"));
        } catch (UnknownHostException e) {
                throw new RuntimeException(e);
        }
    }

}
