package controller;

import model.user;
import model.contact.etat;
import protocols.*;






public class controllerDecouvert {
    private  user user;
    private UDPrecever udpr;
    private UDPsender udps;

    public controllerDecouvert(user userlocal,UDPsender udps,UDPrecever udpr){
        this.user=userlocal;
        this.udps=udps;
        this.udpr=udpr;
        
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
}
