package controller;

import model.user;
import model.contact.etat;
import protocols.*;






public class controllerDecouvert {
    private  user user;
    private UDPrecever udpr;
    private UDPsender udps;

    public controllerDecouvert(user userlocal,int port){
        this.user=userlocal;
        this.udpr= new UDPrecever(port, this.user);
        this.udps=new UDPsender();
        
    }

    public void UpdateChangeName(String newName,int port){
        udps.sendBroadcast("CHANGEDNAME_"+newName+"_"+user.getUserlocal(), port);
    }

    public void connexion(int port) throws InterruptedException{
        this.udpr.start();
        System.out.println("sending Conection_broadcast");
        this.udps.sendBroadcast("DECOUVERTE_"+user.getUserlocal().getUserName()+"_"+user.getUserlocal().getUserIP()+"_"+user.getUserlocal().getUserEtat(),port );
        Thread.sleep(1000);
        this.user.getUserlocal().setUserEtat(etat.CONNECTED);
    }
    public void deconnexion(int port) throws InterruptedException{
        System.out.println("sending DECONNECTION_broadcast");
        this.udps.sendBroadcast("DECONNECT_"+user.getUserlocal().getUserName()+"_"+user.getUserlocal().getUserIP(),port) ;
        Thread.sleep(1000);
        this.user.getUserlocal().setUserEtat(etat.DISCONNECTED);
        this.udpr.stopReceiver();
    }


    public UDPrecever getUDPr(){
        return this.udpr;
    }

    public void setUDPr(UDPrecever udpr) {
        this.udpr = udpr;
    }

    public UDPsender getUDPs(){
        return this.udps;
    }

    public void setUDPs(UDPsender udps){
        this.udps = udps;
    }
}
