package com.mybank.gui;

import com.mybank.domain.Bank;
import com.mybank.domain.CheckingAccount;
import com.mybank.domain.Customer;
import com.mybank.domain.SavingsAccount;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Alexander 'Taurus' Babich
 */
public class SWINGDemo {

    private final JEditorPane log;
    private final JButton show;
    private final JComboBox<String> clients;

    public SWINGDemo() {
        log = new JEditorPane("text/html", "");
        log.setPreferredSize(new Dimension(250, 150));
        show = new JButton("Show");
        clients = new JComboBox<>();
        loadCustomersFromFile("src/com/mybank/gui/test.dat");
    }

    private void loadCustomersFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                String firstName = data[0];
                String lastName = data[1];
                String accountType = data[2];
                double balance = Double.parseDouble(data[3]);

                Bank.addCustomer(firstName, lastName);
                Customer customer = Bank.getCustomer(Bank.getNumberOfCustomers() - 1);

                if ("Checking".equalsIgnoreCase(accountType)) {
                    if (data.length == 5) {
                        double overdraftAmount = Double.parseDouble(data[4]);
                        customer.addAccount(new CheckingAccount(balance, overdraftAmount));
                    } else {
                        customer.addAccount(new CheckingAccount(balance));
                    }
                } else if ("Savings".equalsIgnoreCase(accountType)) {
                    double interestRate = Double.parseDouble(data[4]);
                    customer.addAccount(new SavingsAccount(balance, interestRate));
                }

                clients.addItem(lastName + ", " + firstName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void launchFrame() {
        JFrame frame = new JFrame("MyBank clients");
        frame.setLayout(new BorderLayout());
        JPanel cpane = new JPanel();
        cpane.setLayout(new GridLayout(1, 2));

        cpane.add(clients);
        cpane.add(show);
        frame.add(cpane, BorderLayout.NORTH);
        frame.add(log, BorderLayout.CENTER);

        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer current = Bank.getCustomer(clients.getSelectedIndex());
                StringBuilder custInfo = new StringBuilder();
                custInfo.append("<br>&nbsp;<b><span style=\"font-size:2em;\">")
                        .append(current.getLastName()).append(", ")
                        .append(current.getFirstName()).append("</span><br><hr>");
                for (int i = 0; i < current.getNumberOfAccounts(); i++) {
                    String accType = current.getAccount(i) instanceof CheckingAccount ? "Checking" : "Savings";
                    custInfo.append("&nbsp;<b>Acc Type: </b>").append(accType)
                            .append("<br>&nbsp;<b>Balance: <span style=\"color:red;\">$")
                            .append(current.getAccount(i).getBalance()).append("</span></b><br>");
                }
                log.setText(custInfo.toString());
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SWINGDemo demo = new SWINGDemo();
        demo.launchFrame();
    }
}
