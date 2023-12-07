package controller;

import model.contact.contact;
import model.contact.etat;
import protocols.*;






public class controllerDecouvert {
    private  controller app;
    private UDPrecever udpr;
    private UDPsender udps;

    public controllerDecouvert(controller app){
        this.app=app;
        this.udpr=app.getUDPr();
        this.udps=app.getUDPs();
        
    }


    public  controller getController(){
        return app;
    }
    public void setController(controller app){
        this.app=app;
    }
    public void envoyerMessageDecouvert(contact user, int port){
        udps.sendBroadcast("DECOUVERTE_"+user.getUserName(), port);
    }
    public void connexion(int port) throws InterruptedException{
        this.udpr.start();
        System.out.println("sending Conection_broadcast");
        this.udps.sendBroadcast("DECOUVERTE_"+getController().getUser().getUserlocal().getUserName()+"_"+getController().getUser().getUserlocal().getUserIP()+"_"+getController().getUser().getUserlocal().getUserEtat(),port );
        Thread.sleep(1000);
        this.app.getUser().getUserlocal().setUserEtat(etat.CONNECTED);
    }
    public void deconnexion(int port) throws InterruptedException{
        System.out.println("sending DECONNECTION_broadcast");
        this.udps.sendBroadcast("DECONNECT_"+getController().getUser().getUserlocal().getUserName()+"_"+getController().getUser().getUserlocal().getUserIP(),port) ;
        Thread.sleep(1000);
        this.app.getUser().getUserlocal().setUserEtat(etat.DISCONNECTED);
        this.udpr.stopReceiver();
    }
}
