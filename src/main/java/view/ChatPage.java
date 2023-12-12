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
        this.listFriend = this.user.getUserlist();
        //pour tester
        this.listFriend.addElement(new contact("ZY", adresse));
        this.listFriend.addElement(new contact("GJJ", adresse));
        this.listFriend.addElement(new contact("Bob", adresse));
        PagePrincipal();
    }



    public void PagePrincipal(){
        JFrame frame = new JFrame("Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        //zone info personnelle
        JPanel infoPanel = new JPanel(new GridLayout(2, 2)); // GridLayout with 2 rangs, 2 colonnes
        infoPanel.setBackground(Color.LIGHT_GRAY);
        JLabel usernameLabel = new JLabel("UserName:"+ this.username);
        JLabel adresseLabel = new JLabel("Adresse:"+this.adresse);
        JButton changeUsername=new JButton("Change Nickname");
        JButton deconneButton=new JButton("deconnection");
        deconneButton.setMargin(new Insets(10, 20, 10, 20));

        infoPanel.add(usernameLabel);
        infoPanel.add(changeUsername);
        infoPanel.add(adresseLabel);
        infoPanel.add(deconneButton);
       

        //changer le nickname
        changeUsername.addActionListener(e -> {
            //JOptionPane:une boîte de dialogue modale pour demander de saisir le nouveau pseudonyme
            String newUsername = JOptionPane.showInputDialog(frame, "Nouveau pseudonyme:");
            if (newUsername != null && !newUsername.isEmpty()) {
                this.user.getUserlocal().setUserName(newUsername);

                usernameLabel.setText("UserName: " + this.user.getUserlocal().getUserName());
            }
        });


        //Deconnecter
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
        JPanel listFriendPanel = new JPanel(new GridLayout(listFriend.getSize() + 1,2));
        listFriendPanel.setBackground(Color.WHITE);
        
        JLabel listFriendJLabel = new JLabel("  List Friend: ");
       
        //un boutton renouvller le list
        JButton renouvellerButton=new JButton();
        renouvellerButton.setText("renouveller List ");
        ImageIcon renouvellerIcon=resizeImageIconFromURL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRo-sqzTj9yUuS8qP5oAQ0Tc2apn4YZZuSaIhlNnzTEuQzTGeNmCN2KrCerJt3FPP4dK68&usqp=CAU"
                                                            ,30,30);
        renouvellerButton.setIcon(renouvellerIcon);
        renouvellerButton.addActionListener(e -> {
                System.out.println("bien renouveller le list");
                frame.dispose();
                SwingUtilities.invokeLater(() -> new ChatPage(this.appdecou));
        });

        listFriendPanel.add(listFriendJLabel);
        listFriendPanel.add(renouvellerButton);
        
        for (int i = 0; i < listFriend.getSize(); i++) {
            contact currentContact = listFriend.getElementAt(i);
    
            JButton contactButton = new JButton();
            contactButton.setText(currentContact.getUserName()+" : "+currentContact.getUserIP());
            ImageIcon icon=resizeImageIconFromURL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUuljTu0ME5vw04dWtw_ra0GUXSusHwID3dQWXvu1hM7cXqCGKG7uFmYPhw8QvKcPZdNM&usqp=CAU"
                                                                ,50,50);
            contactButton.setIcon(icon);
    
            contactButton.addActionListener(e -> {
                System.out.println("Message envoyé à : " + currentContact.getUserName());
                SwingUtilities.invokeLater(() -> new messagerie(this.user,currentContact));
            });

            listFriendPanel.add(contactButton);
        }

        frame.add(infoPanel,BorderLayout.NORTH);
        frame.add(listFriendPanel,BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }
    
    public static ImageIcon resizeImageIconFromURL(String urlString,int width,int height){
        try {
            URL url = new URL(urlString);
            ImageIcon imageIcon = new ImageIcon(url);
            Image image = imageIcon.getImage();
            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
