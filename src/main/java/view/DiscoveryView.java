package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.user;
import java.util.List;
public class DiscoveryView {
    private JFrame frame;
    private JList<user> userList;
    private JButton discoverButton;

    public DiscoveryView() {
        frame = new JFrame("UDP User Discovery");
        userList = new JList<>();
        discoverButton = new JButton("Discover Users");

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(userList), BorderLayout.CENTER);
        frame.add(discoverButton, BorderLayout.SOUTH);

        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void setDiscoverButtonListener(ActionListener listener) {
        discoverButton.addActionListener(listener);
    }

    public void updateUserList(List<user> users ) {
        userList.setListData((users).toArray(new user[0]));
    }
}
