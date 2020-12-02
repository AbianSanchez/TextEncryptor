package EncryptorDecryptor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class PasswordChangeDialog extends JDialog {
    private PasswordChangeDialog dialog;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private char[] password;

    public PasswordChangeDialog(Frame frame, String title) {
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
        this.setSize(300, 200);
        this.setVisible(true);
    }

    private void onOK() {
        // add your code here
        if (passwordField1.getPassword().length < 1 || passwordField2.getPassword().length < 1) {
            JOptionPane.showMessageDialog(dialog,
                    "The password field is empty.",
                    "Password empty error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (Arrays.equals(passwordField1.getPassword(), passwordField2.getPassword())) {
            password = passwordField1.getPassword();
            this.setVisible(false);
            //dispose();
        } else {
            JOptionPane.showMessageDialog(dialog,
                    "The two password are not equal.",
                    "Password error",
                    JOptionPane.ERROR_MESSAGE);
        }
        passwordField1.setText("");
        passwordField2.setText("");
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public char[] getPassword() {
        return password;
    }

    /*public void createGUI (Frame frame){
        this.pack();
        this.setLocationRelativeTo(frame);
        this.setSize(300, 200);
        this.setVisible(true);
    }*/

    /*public static void main(String[] args) {
        PasswordChangeDialog dialog = new PasswordChangeDialog();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setSize(200, 300);
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
