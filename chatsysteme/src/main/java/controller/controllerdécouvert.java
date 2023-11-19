package controller;

import model.contact.contact;
import model.contact.etat;
import protocols.*;




import javax.swing.DefaultListModel;

public class controllerdécouvert {
    private  controller app;
    private UDPrecever udpr;
    private UDPsender udps;
    private DefaultListModel<contact> friendlist;
    public controllerdécouvert(controller app){
        this.app=app;
        this.udpr=app.getUdpr();
        this.udps=app.getudps();
        friendlist= app.getuser().getUserlist();
        
    }


    public  controller getcontroller(){
        return app;
    }
    public void setcontrpller(controller app){
        this.app=app;
    }
    public void envoyermessagedecouvert(String username, int port){
        udps.sendBroadcast("DECOUVERTE_"+username, port);
    }
    public boolean connexion(int port) throws InterruptedException{
        udpr.start();
        udps.sendBroadcast("Connection_"+getcontroller().getuser().getUserlocal(),port );
        Thread.sleep(2000);
        app.getuser().getUserlocal().setuseretat(etat.CONNECTED);
    
        return true;
    }
}
