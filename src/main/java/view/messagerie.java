package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.controllerMessage;
import model.user;
import model.contact.contact;

public class messagerie {
    private JFrame frame;
    private JTextArea chatHistoryArea;
    private JTextField messageField;
    
    public messagerie(user user, contact currentContact,controllerMessage conMsg) {
        frame = new JFrame(currentContact.getUserName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//ici a modifier
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());


        //zone histoire de la conservation
        chatHistoryArea = new JTextArea();
        chatHistoryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatHistoryArea);
        

        //zone d'envoyer
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        messageField = new JTextField();
        JButton sendButton = new JButton("Envoyer");
        sendButton.addActionListener(e-> {
             
                    try {
                        conMsg.envoyermsg(messageField.getText(),currentContact.getUserIP());
                        sendMessage();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
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

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            String formattedMessage = getFormattedTimestamp() + " Moi: " + message;
            appendToChatHistory(formattedMessage);
            messageField.setText(""); // Effacer le champ de texte après l'envoi
        }
    }

    private void appendToChatHistory(String message) {
        chatHistoryArea.append(message + "\n");
    }
    
    //message horodatés
    private String getFormattedTimestamp() {
        Date now = new Date(); // Obtient la date et l'heure actuelles
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss"); // Format de l'heure (modifiable selon vos besoins)
        return "[" + dateFormat.format(now) + "]";
    }

}