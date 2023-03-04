package io.github.jennas;

import com.amazonaws.services.identitymanagement.model.Policy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EventListener implements ActionListener {
    JTextField accessKeyTextField;
    JTextField secretAccessKeyTextField;
    JTable instanceTable;
    JTextArea logTextArea;
    boolean eventType;

    EventListener(
            JTextField accessKeyTextField,
            JTextField secretAccessKeyTextField,
            JTable instanceTable,
            JTextArea logTextArea,
            boolean eventType
    ) {
        this.accessKeyTextField = accessKeyTextField;
        this.secretAccessKeyTextField = secretAccessKeyTextField;
        this.instanceTable = instanceTable;
        this.logTextArea = logTextArea;
        this.eventType = eventType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String accessKey = this.accessKeyTextField.getText();
        String secretAccessKey = this.secretAccessKeyTextField.getText();

        if (this.eventType) {    // delete event
            int result = JOptionPane.showConfirmDialog(
                    null,
                    "Do you want to delete IAM policies?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) { // delete
                System.out.println("push YES");

                ArrayList<String> policyArn = new ArrayList<>();

                for (int i : this.instanceTable.getSelectedRows()) {
                    policyArn.add(this.instanceTable.getModel().getValueAt(i, 1).toString());
                }

                IamPolicy iamPolicy = new IamPolicy(accessKey, secretAccessKey);
                iamPolicy.deleteIamPolicyList(policyArn);
            }
        }

        removeAllTableRows();
        getIamPolicy(accessKey, secretAccessKey);
    }

    private void removeAllTableRows() {
        DefaultTableModel instanceTableModel = (DefaultTableModel) this.instanceTable.getModel();

        instanceTableModel.setRowCount(0);
    }

    private void getIamPolicy(String accessKey, String secretAccessKey) {
        try {
            IamPolicy iamPolicy = new IamPolicy(accessKey, secretAccessKey);
            DefaultTableModel instanceTableModel = (DefaultTableModel) this.instanceTable.getModel();

            List<Policy> policyList = iamPolicy.getIamPolicyList();

            for (Policy policy : policyList) {
                instanceTableModel.addRow(new String[]{policy.getPolicyName(), policy.getArn()});
            }
        } catch (Exception e) {
            this.logTextArea.setText(
                    e.toString()
            );
        }
    }
}
