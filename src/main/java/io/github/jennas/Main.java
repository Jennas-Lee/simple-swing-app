package io.github.jennas;

import javax.swing.*;
import java.awt.*;

class Frame extends JFrame {
    public Frame() {
        setTitle("EC2 List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());



        cp.add((Component) setTable());

        setSize(1000, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private BorderLayout setTable() {
        BorderLayout layout = new BorderLayout();

        String[] tableHeader = {"Name", "AZ", "Type", "Public IP", "Private IP"};
        String[][] tableContents = new String[0][];

        JTable instanceTable = new JTable(tableContents, tableHeader);
        JScrollPane instanceTableScrollPane = new JScrollPane(instanceTable);

        layout.addLayoutComponent(instanceTableScrollPane, BorderLayout.CENTER);

        return layout;
    }
}

public class Main {
    public static void main(String[] args) {
        new Frame();
    }
}