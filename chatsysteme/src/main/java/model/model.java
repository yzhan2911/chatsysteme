package model;
import contact.contact;
import contact.etat;

import javax.swing.*;
import java.net.InetAddress;
import java.nio.file.*;


public class model {
    private contact userlocal;
    private DefaultListModel<contact> userlist;
    private Path localog;

    public model (contact userlocal){
        this.userlocal=userlocal;
        this.userlist= new DefaultListModel<contact>();
        this.userlocal.setuseretat(etat.DISCONNECTED);

    }

    public contact getUserlocal(){
        return userlocal;
    }

    public DefaultListModel<contact> getUserlist() {
        return userlist;
    }

    public void adduser(contact userajout){
        contact use=null;
        for (int i=0; i<this.userlist.getSize();i++){
            use = this.userlist.getElementAt(i);
            if(use.getuserip().equals(userajout.getuserip())){
                return;
            }

        }
        this.userlist.addElement(userajout);
        System.out.println("[Model] List of user: "+this.userlist.toString());
    }

    public void removeuser(contact useraremo){
        contact use=null;
        for (int i=0; i<this.userlist.getSize();i++){
            use = this.userlist.getElementAt(i);
            if(use.getuserip().equals(useraremo.getuserip())){
                this.userlist.removeElementAt(i);
            }

        }
        System.out.println("[Model] List of user: "+this.userlist.toString());
    }

    public contact getUserbyname(String username){
        contact use=null;
        for(int i=0;i<this.userlist.getSize();i++){
            use=this.userlist.getElementAt(i);
            if(use.getusername().equals(username)){
                return  use;
            }
        }
        return  use;
    }

    public contact getUserbyip(InetAddress ipad){
        contact use=null;
        for(int i=0;i<this.userlist.getSize();i++){
            use=this.userlist.getElementAt(i);
            if(use.getuserip().equals(ipad)){
                return  use;
            }
        }
        return  use;
    }

    public void removeall(){
        this.userlist.removeAllElements();
    }

}
