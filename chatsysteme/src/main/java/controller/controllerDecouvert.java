package controller;

import model.contact.contact;
import model.contact.etat;
import protocols.*;




import javax.swing.DefaultListModel;

public class controllerDecouvert {
    private  controller app;
    private UDPrecever udpr;
    private UDPsender udps;
    private DefaultListModel<contact> friendlist;

    public controllerDecouvert(controller app){
        this.app=app;
        this.udpr=app.getUDPr();
        this.udps=app.getUDPs();
        this.friendlist= app.getUser().getUserlist();
        
    }


    public  controller getController(){
        return app;
    }
    public void setController(controller app){
        this.app=app;
    }
    public void envoyerMessageDecouvert(String username, int port){
        udps.sendBroadcast("DECOUVERTE_"+username, port);
    }
    public void connexion(int port) throws InterruptedException{
        udpr.start();
        udps.sendBroadcast("Connection_"+getController().getUser().getUserlocal(),port );
        Thread.sleep(2000);
        app.getUser().getUserlocal().setUserEtat(etat.CONNECTED);
        System.out.println("bien connexion");
        
    }
}
