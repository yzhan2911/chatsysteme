package view;

import java.awt.BorderLayout;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.controllerMessage;
import model.BaseDeDonnee.dataMessage;
import model.user;
import model.contact.contact;

public class messagerie {
    private JFrame frame;
    private JTextArea chatHistoryArea;
    private JTextField messageField;
    private controllerMessage conMsg;

    public messagerie(user user, contact currentContact,controllerMessage conMsg) {
        this.conMsg = conMsg;
        String username= user.getUserlocal().getUserName();
	    String friendname= currentContact.getUserName();
        System.out.println(username +"envoyer à "+friendname);
        conMsg.getTcpr().setMessageListener(message->{
            try {
                String[] parts = message.split("_");
                if (parts.length >= 4) {
                    String sender = parts[0];
                    String receiver = parts[1];
                    Date time = convertir_string_date(parts[2]);
                    String msgContent = parts[3];
                    this.conMsg.getBdd().addmessageData(sender, receiver, time, msgContent);
				Thread.sleep(500);
                updateHistory(friendname,username);}
                else{System.out.println("[view] messagerie: recever un error message");}
            } catch (InterruptedException e) {
                System.out.println("[view] messagerie: erreur de sleep");
				e.printStackTrace();
			}
            
            
        });
        frame = new JFrame(currentContact.getUserName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //zone histoire de la conservation
        chatHistoryArea = new JTextArea();
        chatHistoryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatHistoryArea);
        updateHistory(username,friendname);
        this.conMsg.getBdd().get_all_history();
        //zone d'envoyer
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        messageField = new JTextField();
        JButton sendButton = new JButton("Envoyer");
        sendButton.addActionListener(e-> {
                    try {
                        Date now = new Date(); // Obtient la date et l'heure actuelles
                        String msg = messageField.getText();
                        messageField.setText("");
                        conMsg.envoyermsg(username+"_"+friendname+"_"+now+"_"+msg,currentContact.getUserIP(),now );
                        //appendToChatHistory("["+now+"]"+user.getUserlocal().getUserName()+": "+ msg);
                        updateHistory(username,friendname);
                    } catch (IOException e1) {
                      System.out.println("[view] messagerie: erreur de envoyer");
                        e1.printStackTrace();
                    }
                
            
        });

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        frame.add(mainPanel);
        frame.setVisible(true);
    }
 
    public void updateHistory(String name, String namefriend) {
        chatHistoryArea.setText(""); // Effacer le contenu actuel
        List<dataMessage> listdata = this.conMsg.getBdd().gethistory(name , namefriend); 
        for(dataMessage data:listdata){
            appendToChatHistory("["+data.time()+"]"+data.sender()+": "+data.message());
        }       
    }
    private Date convertir_string_date(String timestamp){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void appendToChatHistory(String message) {
        chatHistoryArea.append(message + "\n");
    }
    
    
}
