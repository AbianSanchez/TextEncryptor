package EncryptorDecryptor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class PasswordDialog extends JDialog {
    private PasswordDialog dialog;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField passwordField;
    private char[] password;

    public PasswordDialog(Frame frame, String title) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        this.pack();
        this.setTitle(title);
        this.setLocationRelativeTo(frame);
        this.setSize(300, 150);
        this.setVisible(true);
    }

    private void onOK() {
        // add your code here
        if (this.passwordField.getPassword().length < 1) {
            JOptionPane.showMessageDialog(dialog,
                    "The password field is empty.",
                    "Password empty error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            password = passwordField.getPassword();
            this.setVisible(false);
            //dispose();
        }
        passwordField.setText("");
    }

    private void onCancel() {
        // add your code here if necessary
        //dispose();
        password = null;
        this.setVisible(false);
    }

    public char[] getPassword() {
        return password;
    }

    /*public static void main(String[] args) {
        PasswordDialog dialog = new PasswordDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
