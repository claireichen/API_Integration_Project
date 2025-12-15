package org.example.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class StatusBar extends JPanel {

    private final JLabel lblMessage = new JLabel("Ready.");

    public StatusBar() {
        setLayout(new BorderLayout());
        add(lblMessage, BorderLayout.WEST);
    }

    public void setMessage(String message) {
        lblMessage.setText(message);
    }
}

