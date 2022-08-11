package io.github.jennas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class Frame extends JFrame {
    private final String[] region = {
            "us-east-1",
            "us-east-2",
            "us-west-1",
            "us-west-2",
            "af-south-1",
            "ap-east-1",
            "ap-south-1",
            "ap-northeast-1",
            "ap-northeast-2",
            "ap-northeast-3",
            "ap-southeast-1",
            "ap-southeast-2",
            "ap-southeast-3",
            "ca-central-1",
            "eu-central-1",
            "eu-west-1",
            "eu-west-2",
            "eu-west-3",
            "eu-north-1",
            "eu-south-1",
            "me-south-1",
            "sa-east-1"
    };
    JTable instanceTable;

    public Frame() {
        setTitle("EC2 List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(1, 2));

        JPanel tablePanel = new JPanel();
        JPanel formPanel = new JPanel();

        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(setTable());

        formPanel.setLayout(new BorderLayout());
        formPanel.add(setForm());

        cp.add(tablePanel);
        cp.add(formPanel);

        setSize(1000, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JScrollPane setTable() {
        String[] tableHeader = {"ID", "Name", "AZ", "Type", "Public IP", "Private IP"};
        String[][] tableContents = new String[0][];

        DefaultTableModel instanceTableModel = new DefaultTableModel(tableContents, tableHeader);

        instanceTable = new JTable(instanceTableModel);

        return new JScrollPane(instanceTable);
    }

    private JPanel setForm() {
        JPanel formPanel = new JPanel();
        JLabel accessKeyLabel = new JLabel("Access Key ");
        JLabel secretAccessKeyLabel = new JLabel("Secret Access Key ");
        JLabel regionLabel = new JLabel("Region ");
        JTextField accessKeyTextField = new JTextField(20);
        JTextField secretAccessKeyTextField = new JTextField(20);
        JComboBox<String> regionComboBox = new JComboBox<>(region);
        JButton button = new JButton("OK");
        JTextArea logTextArea = new JTextArea();
        JScrollPane logScrollPane = new JScrollPane(logTextArea);

        logTextArea.setEnabled(false);
        logTextArea.setForeground(Color.black);

        formPanel.setLayout(null);

        formPanel.add(accessKeyLabel);
        formPanel.add(accessKeyTextField);
        formPanel.add(secretAccessKeyLabel);
        formPanel.add(secretAccessKeyTextField);
        formPanel.add(regionLabel);
        formPanel.add(regionComboBox);
        formPanel.add(button);
        formPanel.add(logScrollPane);
        button.addActionListener(new EventListener(accessKeyTextField, secretAccessKeyTextField, regionComboBox, instanceTable, logTextArea));

        accessKeyLabel.setBounds(30, 10, 120, 20);
        accessKeyTextField.setBounds(150, 10, 300, 20);
        secretAccessKeyLabel.setBounds(30, 40, 120, 20);
        secretAccessKeyTextField.setBounds(150, 40, 300, 20);
        regionLabel.setBounds(30, 70, 120, 20);
        regionComboBox.setBounds(150, 70, 300, 20);
        button.setBounds(30, 100, 420, 40);
        logScrollPane.setBounds(30, 160, 420, 270);

        setSize(500, 300);

        return formPanel;
    }
}

public class Main {
    public static void main(String[] args) {
        new Frame();
    }
}