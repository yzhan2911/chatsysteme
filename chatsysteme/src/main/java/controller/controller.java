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

    public UDPrecever getUdpr(){
        return this.udpr;
    }

    public void setUdpr(UDPrecever udpr) {
        this.udpr = udpr;
    }

    public UDPsender getudps(){
        return this.udps;
    }

    public void setUdps(UDPsender udps){
        this.udps = udps;
    }

    public user getuser(){
        return this.userlocal;
    }

    public void setuser(user usernew){
        this.userlocal=usernew;
    }
}
