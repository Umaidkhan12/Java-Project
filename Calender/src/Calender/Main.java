package Calender;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calender");
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        String USER = JOptionPane.showInputDialog("Enter User:");
        String PASS = JOptionPane.showInputDialog("Enter Password:");

        LocalDate date = LocalDate.now();
        Database database = new Database(frame,USER,PASS);

        mainPanel.add(new Calender(date.getYear(),date.getMonthValue(),date,mainPanel,database));
        mainPanel.add(new Events(date,database,mainPanel));

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }
}