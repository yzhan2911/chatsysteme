import protocols.UDP;
import view.view;
import controller.controller;
public class testsession2 {

        public static void main(String[] args) {
            UDP udpDiscovery = new UDP(8888);
            view view = new view();
            controller controller = new controller(udpDiscovery, view);

            // Envoyer une requête de découverte et écouter les réponses
            udpDiscovery.sendDiscoveryRequest();
            udpDiscovery.listenForResponses();
        }


}
