package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.*;

import controller.controller;
import controller.controllerDecouvert;
import controller.controllerMessage;
import model.user;
import model.contact.contact;
import protocols.UDPsender;

public class Connexion  {
   
    public static final int PORT_DISCOVERY = 1929;
    public static final int PORT_COMMUNICATION = 1650;
    
    public Connexion() throws UnknownHostException{
        JFrame frame = new JFrame("Log in!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
      

        //blackground
        // frame.setBackground(Color.getHSBColor(1,1,1)); //changer la couleur de blackgraound
        URL url;
        try {
            url = new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSpM722T0T1P4KeZconZY8NT-hoDGQqybYuA49fcGUQapNPqTjnYOH-qnrZavx5TgofbXg&usqp=CAU");
            ImageIcon imageIcon = new ImageIcon(url);
            JLabel backgroundLabel = new JLabel(imageIcon){   
                // redimensionner l'image en cas de redimensionnement de la fenêtre
                @Override   
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Image image = imageIcon.getImage();
                    Dimension size = this.getSize();
                    g.drawImage(image, 0, 0, size.width, size.height, this);
                }
            };
            frame.addComponentListener(new ComponentAdapter() {
                // pour détecter les changements de taille de la fenêtre 
                @Override   
                public void componentResized(ComponentEvent e) {
                    Dimension newSize = e.getComponent().getSize();
                    Image img = imageIcon.getImage().getScaledInstance(newSize.width, newSize.height, Image.SCALE_SMOOTH);
                    imageIcon.setImage(img);
                    backgroundLabel.repaint();
                }
            });
            frame.setContentPane(backgroundLabel);
         } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        

        JPanel mainPanel = new JPanel(new GridLayout(3, 2)); 
        mainPanel.setOpaque(false); //panel en transparent
        JLabel adresseLabel = new JLabel("Adresse:");
        adresseLabel.setForeground(Color.white);//changer la couleur de JLabel
        JLabel adresseField=new JLabel(""+contact.getCurrenAddress());
        adresseField.setForeground(Color.white);
        JLabel usernameLabel = new JLabel("UserName:");
        usernameLabel.setForeground(Color.white);
        JTextField usernameField = new JTextField(20);  

        mainPanel.add(adresseLabel);
        mainPanel.add(adresseField);
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);

        JLabel errorNickname =new JLabel();
        errorNickname.setForeground(Color.RED);
        mainPanel.add(errorNickname);
        
        //button login
        JButton loginButton = new JButton("log in!");
        
        loginButton.addActionListener(e->{
            String username=usernameField.getText();
            try {
            
                user userlocal = new  user(new contact(username, contact.getCurrenAddress()));
                controller app =new controller(userlocal, PORT_DISCOVERY, PORT_COMMUNICATION);
                controllerDecouvert decou = app.getConDecou();
                    controllerMessage conMsg=app.getconMessage();
                decou.connexion(PORT_DISCOVERY); 
            
                if (app.exist_nickname(username)){
                    errorNickname.setText("ce nale est deja existe!!!");
                }else{
                SwingUtilities.invokeLater(() -> new ChatPage(app));
                        conMsg.connexion();
                
                frame.dispose();
                }
            } catch (InterruptedException | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
         });
 

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);

        frame.setLayout(new BorderLayout());
        frame.add(mainPanel,BorderLayout.NORTH);
        frame.add(buttonPanel,BorderLayout.CENTER);
        
        
 
        frame.pack();
        // Display the window.
        frame.setVisible(true);
       
    }



   
}
