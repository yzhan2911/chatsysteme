package view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Connexion  {
   

    public Connexion(){
        JFrame frame = new JFrame("Log in!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(2, 2)); 
        JLabel adresseLabel = new JLabel("Adresse:");
        JTextField adresseField = new JTextField(20); 
        JLabel usernameLabel = new JLabel("UserName:");
        JTextField usernameField = new JTextField(20);  

        mainPanel.add(adresseLabel);
        mainPanel.add(adresseField);
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);

        //button
        JButton loginButton = new JButton("log in!");
        
        
        loginButton.addActionListener(e->{
            String username=usernameField.getText();
            String adresse=adresseField.getText();
            SwingUtilities.invokeLater(() -> new ChatPage(username, adresse));
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



    public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Connexion();
            }
        });
    }
}
