

import model.contact.contact;
import protocols.UDP;
import view.view;
import controller.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ChatApplication {

    public static void main(String[] args) throws UnknownHostException {
        // Créer l'instance du modèle (UdpDiscovery)
        UDP udpDiscovery = new UDP(8888); // Le port doit être configuré selon votre réseau


        // Créer l'instance de la vue (DiscoveryView)
        view discoveryView = new view();


        // Créer l'instance du contrôleur (DiscoveryController)
        controller discoveryController = new controller(udpDiscovery, discoveryView);


    }
}
