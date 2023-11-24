package view;

import java.awt.*;

import java.net.*;


import javax.swing.*;

import model.user;
import model.contact.contact;
import controller.controllerDecouvert;

public class ChatPage  {

    public static final int PORT_DISCOVERY = 1929;
    
    private controllerDecouvert appdecou;
    private user user;
    private String username;
    private InetAddress adresse;
    private DefaultListModel<contact> listFriend; 

    public ChatPage(controllerDecouvert appdecou) {
        this.appdecou=appdecou;
        this.user=appdecou.getController().getUser();
       this.username=this.user.getUserlocal().getUserName();
       this.adresse=this.user.getUserlocal().getUserIP();
       this.listFriend = this.user.getUserlist(); // Initialisation du modèle de liste; 
       PagePrincipal();
    }



    public void PagePrincipal(){
        JFrame frame = new JFrame("Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //zone info personnelle
        JPanel infoPanel = new JPanel(new GridLayout(2, 3)); // GridLayout with 2 rangs, 2 colonnes

        JLabel usernameLabel = new JLabel("UserName:"+ this.username);
        JButton changeUsername=new JButton("Change Nickname");
        JButton deconneButton=new JButton("deconnection");
        JLabel adresseLabel = new JLabel("Adresse:"+this.adresse);

        infoPanel.add(usernameLabel);
        infoPanel.add(changeUsername);
        infoPanel.add(deconneButton);
        infoPanel.add(adresseLabel);
        infoPanel.add(new JLabel());
       
   
        changeUsername.addActionListener(e -> {
            //JOptionPane:une boîte de dialogue modale pour demander de saisir le nouveau pseudonyme
            String newUsername = JOptionPane.showInputDialog(frame, "Nouveau pseudonyme:");
            if (newUsername != null && !newUsername.isEmpty()) {
                this.username = newUsername;
                usernameLabel.setText("UserName: " + username);
                
            }
        });

        deconneButton.addActionListener(e->{
           try {
            this.appdecou.deconnexion(PORT_DISCOVERY);
            frame.dispose();
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        });


       
   
        
        //zone list de friends
        // JLabel listFriendJLabel = new JLabel("list Friend: ");
        JList<contact> contactsList = new JList<>(listFriend);
        // Ajouter la JList dans un JScrollPane pour la gestion du défilement
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JScrollPane(contactsList), BorderLayout.CENTER);
        

        frame.setLayout(new BorderLayout());
        frame.add(infoPanel,BorderLayout.NORTH);
        frame.add(listPanel,BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }


}
