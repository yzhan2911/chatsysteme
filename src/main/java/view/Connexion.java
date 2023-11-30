package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;

import controller.controller;
import controller.controllerDecouvert;
import model.user;
import model.contact.contact;
import protocols.UDPsender;

public class Connexion  {
   
    public static final int PORT_DISCOVERY = 1929;
    
    public Connexion() throws UnknownHostException{
        JFrame frame = new JFrame("Log in!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(2, 2)); 
        JLabel adresseLabel = new JLabel("Adresse:");
        JLabel adresseField=new JLabel(""+InetAddress.getLocalHost());
        JLabel usernameLabel = new JLabel("UserName:");
        JTextField usernameField = new JTextField(20);  

        mainPanel.add(adresseLabel);
        mainPanel.add(adresseField);
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);


        //button login
        JButton loginButton = new JButton("log in!");
        
        loginButton.addActionListener(e->{
            String username=usernameField.getText();
            try {
                user userlocal = new  user(new contact(username, InetAddress.getLocalHost()));
                controller app =new controller(userlocal, new UDPsender(), PORT_DISCOVERY);
                controllerDecouvert decou = new controllerDecouvert(app);
                SwingUtilities.invokeLater(() -> new ChatPage(decou));
                decou.connexion(PORT_DISCOVERY);    
                frame.dispose();
            } catch (UnknownHostException | InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
         });
 

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);

        frame.setLayout(new BorderLayout());
        frame.add(mainPanel,BorderLayout.NORTH);
        frame.add(buttonPanel,BorderLayout.CENTER);
 
        frame.pack();
        // Display the window.
        frame.setVisible(true);
    }



   
}
