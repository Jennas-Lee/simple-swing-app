package io.github.jennas;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class Frame extends JFrame {
    JTable instanceTable;

    public Frame() {
        setTitle("IAM Policy Nuke");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(1, 2));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image icon = toolkit.getImage("./icon.png");
        setIconImage(icon);

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
        String[] tableHeader = {"Name", "ARN"};
        String[][] tableContents = new String[0][];

        DefaultTableModel instanceTableModel = new DefaultTableModel(tableContents, tableHeader);

        instanceTable = new JTable(instanceTableModel);

        return new JScrollPane(instanceTable);
    }

    private JPanel setForm() {
        JPanel formPanel = new JPanel();
        JLabel accessKeyLabel = new JLabel("Access Key ");
        JLabel secretAccessKeyLabel = new JLabel("Secret Access Key ");
        JLabel getCredentialFileLabel = new JLabel("Get Credential File");
        JTextField accessKeyTextField = new JTextField(20);
        JTextField secretAccessKeyTextField = new JTextField(20);
        JButton getCredentialFileButton = new JButton("Get File");
        JButton okButton = new JButton("OK");
        JButton deleteButton = new JButton("DELETE");
        JTextArea logTextArea = new JTextArea();
        JScrollPane logScrollPane = new JScrollPane(logTextArea);

        logTextArea.setEnabled(false);
        logTextArea.setForeground(Color.black);

        formPanel.setLayout(null);

        formPanel.add(accessKeyLabel);
        formPanel.add(accessKeyTextField);
        formPanel.add(secretAccessKeyLabel);
        formPanel.add(secretAccessKeyTextField);
        formPanel.add(getCredentialFileLabel);
        formPanel.add(getCredentialFileButton);
        formPanel.add(okButton);
        formPanel.add(deleteButton);
        formPanel.add(logScrollPane);

        getCredentialFileButton.addActionListener(
                new GetFileEventListener(accessKeyTextField, secretAccessKeyTextField, logTextArea)
        );
        okButton.addActionListener(
                new EventListener(accessKeyTextField, secretAccessKeyTextField, instanceTable, logTextArea, false)
        );
        deleteButton.addActionListener(
                new EventListener(accessKeyTextField, secretAccessKeyTextField, instanceTable, logTextArea, true)
        );

        accessKeyLabel.setBounds(30, 10, 120, 20);
        accessKeyTextField.setBounds(150, 10, 300, 20);
        secretAccessKeyLabel.setBounds(30, 40, 120, 20);
        secretAccessKeyTextField.setBounds(150, 40, 300, 20);
        getCredentialFileLabel.setBounds(30, 70, 120, 20);
        getCredentialFileButton.setBounds(150, 70, 300, 20);
        okButton.setBounds(30, 100, 200, 40);
        deleteButton.setBounds(250, 100, 200, 40);
        logScrollPane.setBounds(30, 160, 420, 270);

        setSize(500, 300);

        return formPanel;
    }
}

class GetFileEventListener implements ActionListener {
    JTextField accessKeyTextField;
    JTextField secretAccessKeyTextField;
    JTextArea logTextArea;

    GetFileEventListener(
            JTextField accessKeyTextField,
            JTextField secretAccessKeyTextField,
            JTextArea logTextArea
    ) {
        this.accessKeyTextField = accessKeyTextField;
        this.secretAccessKeyTextField = secretAccessKeyTextField;
        this.logTextArea = logTextArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.openFileChooser();
    }

    private void openFileChooser() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int fileChooserResult = fileChooser.showOpenDialog(null);

        if (fileChooserResult == 0) {    // chose file
            try {
                Path filePath = Paths.get(fileChooser.getSelectedFile().getPath());
                List<String> allLines = Files.readAllLines(filePath);

                String credentialLine = allLines.get(1);
                String[] credential = credentialLine.split(",");

                String accessKey = credential[2];
                String secretAccessKey = credential[3];

                this.accessKeyTextField.setText(accessKey);
                this.secretAccessKeyTextField.setText(secretAccessKey);
            } catch (Exception e) {
                this.logTextArea.setText(e.toString());
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        new Frame();
    }
}