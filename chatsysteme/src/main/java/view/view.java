package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.contact.contact;
import java.util.List;

public class view {
    private JFrame frame;
    private JList<contact> contactList;
    private JButton discoverButton;

    public view() {
        frame = new JFrame("UDP Contact Discovery");
        contactList = new JList<>();
        discoverButton = new JButton("Discover Contacts");

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(contactList), BorderLayout.CENTER);
        frame.add(discoverButton, BorderLayout.SOUTH);

        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void setDiscoverButtonListener(ActionListener listener) {
        discoverButton.addActionListener(listener);
    }

    public void updateContactList(List<contact> contacts) {
        DefaultListModel<contact> model = new DefaultListModel<>();
        for (contact contact : contacts) {
            model.addElement(contact);
        }
        contactList.setModel(model);
    }


}

