package controller;

import protocols.UDP;
import view.view;

public class principe {

    public static void main(String[] args) {
        controller  co= new controller(new UDP(8888),new view());
        controller  co2= new controller(new UDP(8887),new view());
        controller  co3= new controller(new UDP(8886),new view());
    }
}
