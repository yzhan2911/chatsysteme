package view;

import java.awt.*;

import java.net.*;


import javax.swing.*;

import model.user;
import model.contact.contact;

public class ChatPage  {
    private String username;
    private String adresse;
    private DefaultListModel<contact> listFriend; 

    public ChatPage(String username, String adresse) {
       this.username=username;
       this.adresse=adresse;
       this.listFriend = new DefaultListModel<>(); // Initialisation du modèle de liste;
       populateList(); 
       PagePrincipal();
    }

    //just pour test de afficher le list friends
    private void populateList() {
        try {
            contact p1 = new contact("zy", InetAddress.getByName("10.10.10.1"));
            contact p2 = new contact("zyj", InetAddress.getByName("10.10.10.2"));
            listFriend.addElement(p1);
            listFriend.addElement(p2);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    public void PagePrincipal(){
        JFrame frame = new JFrame("Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //zone info personnelle
        JPanel infoPanel = new JPanel(new GridLayout(2, 2)); // GridLayout with 2 rangs, 2 colonnes

        JLabel usernameLabel = new JLabel("UserName:"+ this.username);
        JButton changeUsername=new JButton("Change Nickname");
        JLabel adresseLabel = new JLabel("Adresse:"+this.adresse);

        infoPanel.add(usernameLabel);
        infoPanel.add(changeUsername);
        infoPanel.add(adresseLabel);
        infoPanel.add(new JLabel());
       
   
        changeUsername.addActionListener(e -> {
            //JOptionPane:une boîte de dialogue modale pour demander de saisir le nouveau pseudonyme
            String newUsername = JOptionPane.showInputDialog(frame, "Nouveau pseudonyme:");
            if (newUsername != null && !newUsername.isEmpty()) {
                username = newUsername;
                usernameLabel.setText("UserName: " + username);
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
