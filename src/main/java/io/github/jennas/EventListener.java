package io.github.jennas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EventListener implements ActionListener {
    JTextField accessKeyTextField;
    JTextField secretAccessKeyTextField;
    JComboBox<String> regionComboBox;
    JTable instanceTable;
    JTextArea logTextArea;

    EventListener(
            JTextField accessKeyTextField,
            JTextField secretAccessKeyTextField,
            JComboBox<String> regionComboBox,
            JTable instanceTable,
            JTextArea logTextArea
    ) {
        this.accessKeyTextField = accessKeyTextField;
        this.secretAccessKeyTextField = secretAccessKeyTextField;
        this.regionComboBox = regionComboBox;
        this.instanceTable = instanceTable;
        this.logTextArea = logTextArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String accessKey = this.accessKeyTextField.getText();
        String secretAccessKey = this.secretAccessKeyTextField.getText();
        String region = this.regionComboBox.getItemAt(this.regionComboBox.getSelectedIndex());

        removeAllTableRows();
        getEC2Instance(accessKey, secretAccessKey, region);
    }

    private void removeAllTableRows() {
        DefaultTableModel instanceTableModel = (DefaultTableModel) this.instanceTable.getModel();

        instanceTableModel.setRowCount(0);
    }

    private void getEC2Instance(String accessKey, String secretAccessKey, String region) {
        try {
            GetEC2Instance ec2 = new GetEC2Instance(accessKey, secretAccessKey, region);
            DefaultTableModel instanceTableModel = (DefaultTableModel) this.instanceTable.getModel();

            ArrayList<String[]> ec2List = ec2.getEC2List();

            for (String[] ec2Items : ec2List) {
                instanceTableModel.addRow(ec2Items);
            }
        } catch (Exception e) {
            this.logTextArea.setText(
                    e.toString()
            );
        }
    }
}
