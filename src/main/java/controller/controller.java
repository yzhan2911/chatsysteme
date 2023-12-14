package controller;

import javax.swing.DefaultListModel;

import model.user;
import model.contact.contact;
import protocols.UDPrecever;
import protocols.UDPsender;
public class controller {
    private user userlocal;
    private UDPrecever udpr;
    private UDPsender udps;
    private controllerDecouvert conDecou;

    public controller(user userlocal,UDPsender udps,int port){
        this.userlocal=userlocal;
        this.udpr= new UDPrecever(port, this);
        this.udps=udps;
        this.conDecou=new controllerDecouvert(userlocal,udps,udpr); 
    }


    //get et set
    public controllerDecouvert getConDecou() {
        return conDecou;
    }
    public void setConDecou(controllerDecouvert conDecou) {
        this.conDecou = conDecou;
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

    public user getUser(){
        return this.userlocal;
    }

    public void setUser(user usernew){
        this.userlocal=usernew;
    }

    public boolean exist_nickname(String newname){
        DefaultListModel<contact> listFriend=this.userlocal.getUserlist();
        boolean res=false;
        for(int i=0;i<listFriend.size();i++){
            contact friend=listFriend.getElementAt(i);
            if(newname.equals(friend.getUserName())){
                return true;
            }
        }
        return res;
    }
}
