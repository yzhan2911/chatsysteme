package controller;

import model.user;
import protocols.UDPrecever;
import protocols.UDPsender;
public class controller {
    private user userlocal;
    private UDPrecever udpr;
    private UDPsender udps;

    public controller(user userlocal,UDPrecever udpr,UDPsender udps){
        this.userlocal=userlocal;
        this.udpr=udpr;
        this.udps=udps;
    }


    //get et set

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
}
