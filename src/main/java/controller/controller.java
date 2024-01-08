package controller;

import model.user;
import model.contact.contact;
import java.io.IOException;
import javax.swing.DefaultListModel;



public class controller {
    private user userlocal;
    private controllerMessage conMsg;
    private controllerDecouvert conDecou;

    public controller(user userlocal,int portUDP,int portTCP) throws IOException{
        this.userlocal=userlocal;
        this.conDecou=new controllerDecouvert(userlocal, portUDP); 
        this.conMsg = new controllerMessage(userlocal,portTCP);
    
    }

    public controllerMessage getconMessage(){
        return conMsg;
    }

    public void setConMessage(controllerMessage conMsg) {
        this.conMsg=conMsg;
    }

    public controllerDecouvert getConDecou() {
        return conDecou;
    }

    public void setConDecou(controllerDecouvert conDecou) {
        this.conDecou = conDecou;
    }

    public user getUser(){
        return this.userlocal;
    }

    public void setUser(user usernew){
        this.userlocal=usernew;
    }

    public boolean exist_nickname(String newname){
        DefaultListModel<contact> listFriend=this.userlocal.getUserlist();
        boolean exite=false;
        for(int i=0;i<listFriend.size();i++){
            contact friend=listFriend.getElementAt(i);
            if(newname.equals(friend.getUserName())){
                return true;
            }
        }
        return exite;
    }
}
