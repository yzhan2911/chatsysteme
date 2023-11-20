package model.contact;

import java.net.InetAddress;


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

    return this.getUserName()+":"+this.getUserName();
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
}






