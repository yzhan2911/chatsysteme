package model;
import model.contact.contact;
import model.contact.etat;

import javax.swing.*;
import java.net.InetAddress;



public class user {
    private contact userlocal;
    private DefaultListModel<contact> userlist;

    public user(contact userlocal){
        this.userlocal=userlocal;
        this.userlist= new DefaultListModel<contact>();
        this.userlocal.setUserEtat(etat.DISCONNECTED);

    }



    public contact getUserlocal(){
        return userlocal;
    }

    public synchronized DefaultListModel<contact> getUserlist() {
        return userlist;
    }

    public synchronized void adduser(contact userajout){
        contact use=null;
        for (int i=0; i<this.userlist.getSize();i++){
            use = this.userlist.getElementAt(i);
            if(use.getUserIP().equals(userajout.getUserIP())){
                return;
            }

        }
        this.userlist.addElement(userajout);
        System.out.println("[Model] List of user: "+this.userlist.toString());
    }

    public synchronized void removeuser(contact useraremo){
        contact use=null;
        for (int i=0; i<this.userlist.getSize();i++){
            use = this.userlist.getElementAt(i);
            if(use.getUserIP().equals(useraremo.getUserIP())){
                this.userlist.removeElementAt(i);
            }

        }
        System.out.println("[Model] List of user: "+this.userlist.toString());
    }

    public contact getUserbyname(String username){
        contact use=null;
        for(int i=0;i<this.userlist.getSize();i++){
            use=this.userlist.getElementAt(i);
            if(use.getUserName().equals(username)){
                return  use;
            }
        }
        return  use;
    }

    public contact getUserbyip(InetAddress ipad){
        contact use=null;
        for(int i=0;i<this.userlist.getSize();i++){
            use=this.userlist.getElementAt(i);
            if(use.getUserIP().equals(ipad)){
                return  use;
            }
        }
        return  use;
    }

    public void removeall(){
        this.userlist.removeAllElements();
    }

}
