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
        Thread.sleep(2000);
        System.out.println("sending broadcast");
        this.udps.sendBroadcast("DECOUVERTE_"+getController().getUser().getUserlocal(),port );
        Thread.sleep(2000);
        this.app.getUser().getUserlocal().setUserEtat(etat.CONNECTED);
        
        
    }
}
