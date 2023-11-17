package controller;

import view.view;
import protocols.UDP;

public class controller {
    private UDP udpDiscovery;
    private view view;

    public controller(UDP udpDiscovery, view view) {
        this.udpDiscovery = udpDiscovery;
        this.view = view;
        initView();
    }

    private void initView() {
        view.setDiscoverButtonListener(e -> {
            udpDiscovery.sendDiscoveryRequest();
            udpDiscovery.listenForResponses();
        });
    }
}

