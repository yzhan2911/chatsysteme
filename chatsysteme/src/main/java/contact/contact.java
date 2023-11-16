package contact;

import java.net.InetAddress;


public class contact { 
    private String username;
    private InetAddress userip;
    private etat useretat;

    public contact(String username,InetAddress userip)
    {
        this.username=username;
        this.userip=userip;
        this.useretat=etat.DISCONNECTED;
    }   

public String toString(){

    return this.getusername()+":"+this.getuserip();
}

public void setusername(String name){
    this.username=name;
}

public void setuserip(InetAddress ip){
    this.userip=ip;
}

public void setuseretat(etat etat){
    this.useretat=etat;
}
public InetAddress getuserip() {
    return this.userip;
}

public  String getusername() {
    return this.username;
}

public etat getuseretat(){
    return this.useretat;
}
}
   





