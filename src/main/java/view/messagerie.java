package view;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import controller.controllerMessage;
import model.user;
import model.BaseDeDonnee.dataMessage;
import model.contact.contact;

public class messagerie {
    private JFrame frame;
    private JTextArea chatHistoryArea;
    private JTextField messageField;
    private controllerMessage conMsg;
    
    public messagerie(user user, contact currentContact,controllerMessage conMsg) {
        this.conMsg = conMsg;
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
        updateHistory();

        //zone d'envoyer
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        messageField = new JTextField();
        JButton sendButton = new JButton("Envoyer");
        sendButton.addActionListener(e-> {
             
                    try {
                        Date now = new Date(); // Obtient la date et l'heure actuelles
                        String msg = messageField.getText();
                        conMsg.envoyermsg(msg,currentContact.getUserIP(),now );
                        //appendToChatHistory("["+now+"]"+user.getUserlocal().getUserName()+": "+ msg);
                        updateHistory();
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

    public void updateHistory() {
        chatHistoryArea.setText(""); // Effacer le contenu actuel
        List<dataMessage> listdata = this.conMsg.getBdd().gethistory(); 
        for(dataMessage data:listdata){
            appendToChatHistory("["+data.time()+"]"+data.sender()+": "+data.message());
        }       
    }

    private void appendToChatHistory(String message) {
        chatHistoryArea.append(message + "\n");
    }
    
    
}