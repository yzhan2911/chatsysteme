package model.contact;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


public class contact { 
    private String username;
    private InetAddress userip;
    private etat userEtat;

    public contact(String username,InetAddress userip)
    {
        this.username=username;
        this.userip=userip;
        this.userEtat=etat.DISCONNECTED;
    }   
    

public String toString(){

    return this.getUserName()+" : "+this.getUserName() + " , " + this.getUserIP();
}

public void setUserName(String name){
    this.username=name;
}

public void setUserIP(InetAddress ip){
    this.userip=ip;
}

public void setUserEtat(etat etat){
    this.userEtat=etat;
}
public InetAddress getUserIP() {
    return this.userip;
}

public  String getUserName() {
    return this.username;
}

public etat getUserEtat(){
    return this.userEtat;
}


public static InetAddress getCurrenAddress(){
       try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
                        .getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface ni = (NetworkInterface) networkInterfaces
                            .nextElement();
                    Enumeration<InetAddress> nias = ni.getInetAddresses();
                    while(nias.hasMoreElements()) {
                        InetAddress ia= (InetAddress) nias.nextElement();
                        if (!ia.isLinkLocalAddress() 
                         && !ia.isLoopbackAddress()
                         && ia instanceof Inet4Address) {
                            return ia;
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
            return null;

}

}






