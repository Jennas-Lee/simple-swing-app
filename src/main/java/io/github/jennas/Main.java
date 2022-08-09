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
        cp.setLayout(new BorderLayout());

        JPanel tablePanel = new JPanel();
        JPanel formPanel = new JPanel();

        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(setTable());

        formPanel.setLayout(new BorderLayout());
        formPanel.add(setForm());

        cp.add(tablePanel, BorderLayout.WEST);
        cp.add(formPanel, BorderLayout.EAST);

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
        JButton button = new JButton("OK");
        JTextField accessKeyTextField = new JTextField(20);
        JTextField secretAccessKeyTextField = new JTextField(20);
        JComboBox<String> regionComboBox = new JComboBox<>(region);

        formPanel.setLayout(new GridLayout(4, 2));

        formPanel.add(new JLabel("Access Key "));
        formPanel.add(accessKeyTextField);

        formPanel.add(new JLabel("Secret Access Key "));
        formPanel.add(secretAccessKeyTextField);

        formPanel.add(new JLabel("Region "));
        formPanel.add(regionComboBox);

        button.addActionListener(new EventListener(accessKeyTextField, secretAccessKeyTextField, regionComboBox, instanceTable));
        formPanel.add(button);

        setSize(500, 300);

        return formPanel;
    }
}

public class Main {
    public static void main(String[] args) {
        new Frame();
    }
}