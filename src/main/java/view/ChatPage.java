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
        //this.listFriend = new DefaultListModel<>();
        //this.listFriend.addElement(new contact("ZY", adresse));
        //this.listFriend.addElement(new contact("GJJ", adresse));
        //this.listFriend.addElement(new contact("Bob", adresse));
        PagePrincipal();
    }



    public void PagePrincipal(){
        JFrame frame = new JFrame("Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //zone info personnelle
        JPanel infoPanel = new JPanel(new GridLayout(2, 2)); // GridLayout with 2 rangs, 2 colonnes
        //infoPanel.setBackground(Color.LIGHT_GRAY);
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

        JPanel renouvellerJPanel=new JPanel();
        JButton renouvellerButton=new JButton();
        ImageIcon imageIconRenouveller=new ImageIcon("//home/yzhang5/Téléchargements/renouveller.png");
        Image imageRenouveller=imageIconRenouveller.getImage();
        Image resizedImage=imageRenouveller.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon=new ImageIcon(resizedImage);
        renouvellerButton.setIcon(resizedIcon);
        renouvellerJPanel.add(renouvellerButton);
       

        //zone list de friends
        JPanel listFriendPanel = new JPanel(new GridLayout(listFriend.getSize() + 1,2));
        listFriendPanel.setBackground(Color.WHITE);
        
        JLabel listFriendJLabel = new JLabel("  List Friend: ");
        
       


        listFriendPanel.add(listFriendJLabel);
        listFriendPanel.add(new JLabel());
 

        //JList<contact> contactsList = new JList<>(listFriend);       //n'affiche que infos des friends
        //listFriendPanel.add(new JScrollPane(contactsList), BorderLayout.CENTER);

        for (int i = 0; i < listFriend.getSize(); i++) {
            contact currentContact = listFriend.getElementAt(i);
    
            JButton contactButton = new JButton();
            contactButton.setText(currentContact.getUserName()+" : "+currentContact.getUserIP());
            ImageIcon icon = new ImageIcon("/home/yzhang5/Téléchargements/frank.png");
            contactButton.setIcon(icon);
    
            contactButton.addActionListener(e -> {
                System.out.println("Message envoyé à : " + currentContact.getUserName());
                SwingUtilities.invokeLater(() -> new messagerie(this.user,currentContact));
            });

            listFriendPanel.add(contactButton);
        }
       

        // frame.setLayout(new BorderLayout());
        frame.add(infoPanel,BorderLayout.NORTH);
        frame.add(renouvellerJPanel,BorderLayout.SOUTH);
        frame.add(listFriendPanel,BorderLayout.CENTER);
        // frame.add(myButton,BorderLayout.SOUTH);
        // frame.add(testLabel,BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }


}
