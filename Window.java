package com.chat.tec.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Window extends JFrame {
    ArrayList<JTextField> fields = new ArrayList<>();
    private JLabel appTitle;
    private JLabel costLabel;
    private JLabel taxes;
    private JLabel weigth;
    private JTextField costField;
    private JTextField taxesTextField;
    private JTextField weightField;
    private JButton sendButton;
    private JTextArea messagesArea;
    private int width = 500;
    private int height = 500;

    public Window() {
        super();
        setTitle("Chat");

        this.setSize(width, height);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // Components
        appTitle = new JLabel("Chat");
        costLabel = new JLabel("Product cost");
        costField = new JTextField();
        taxes = new JLabel("Product taxes");
        taxesTextField = new JTextField();
        weigth = new JLabel("Product weight");
        weightField = new JTextField();
        sendButton = new JButton("Send");
        messagesArea = new JTextArea();

        // Labels
        appTitle.setBounds(240, 20, 60, 20);
        costLabel.setBounds(230, 360, 100, 30);
        taxes.setBounds(230, 420, 100, 30);
        weigth.setBounds(230, 390, 100, 30);

        // Text fields
        costField.setBounds(10, 360, 220, 30);
        weightField.setBounds(10, 390, 220, 30);
        taxesTextField.setBounds(10, 420, 220, 30);

        fields.add(costField);
        fields.add(weightField);
        fields.add(taxesTextField);

        // Buttons
        sendButton.setBounds(400, 390, 80, 30);

        // Display Areas
        messagesArea.setBounds(12, 50, 460, 300);
        messagesArea.setEditable(false);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // show
        this.add(messagesArea);
        this.add(appTitle);
        this.add(taxes);
        this.add(weigth);
        this.add(costLabel);
        this.add(taxesTextField);
        this.add(weightField);
        this.add(sendButton);
        this.add(costField);

        this.setVisible(true);
    }

}
