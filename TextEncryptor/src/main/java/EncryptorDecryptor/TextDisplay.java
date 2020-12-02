package EncryptorDecryptor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class TextDisplay implements ActionListener {

    private JPanel panelMain;
    private String frameTitle = "Text secret";
    private JFrame frame;
    private JTextArea textArea;
    private JScrollPane panelScroll;
    private JMenuBar menuBar;
    private JMenuItem itemNew;
    private JMenuItem itemOpen;
    private JMenuItem itemSave;
    private JMenuItem itemPassword;
    private JLabel tag;
    private TextSecret process;
    private boolean textAreaChanged;
    private boolean openfile;

    public static void main(String[] args) {
        new TextDisplay(new TextSecret());
    }

    public TextDisplay(TextSecret fileProcessor) {
        this.process = fileProcessor;
        this.textAreaChanged = false;
        this.openfile = false;
        frame = new JFrame(frameTitle);

        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        itemNew = new JMenuItem("New");
        itemNew.addActionListener(this);
        itemOpen = new JMenuItem("Open");
        itemOpen.addActionListener(this);
        itemSave = new JMenuItem("Save");
        itemSave.addActionListener(this);
        fileMenu.add(itemNew);
        fileMenu.add(itemOpen);
        fileMenu.add(itemSave);
        itemSave.setEnabled(false);
        menuBar.add(fileMenu);
        JMenu editMenu = new JMenu("Edit");
        itemPassword = new JMenuItem("Change password", KeyEvent.VK_T);
        itemPassword.addActionListener(this);
        itemPassword.setEnabled(false);
        editMenu.add(itemPassword);
        menuBar.add(editMenu);
        this.tag = new JLabel("|| - Load or Create a new file -", SwingConstants.LEFT);
        tag.setFont(new Font("Italic", Font.PLAIN, 14));
        menuBar.add(tag);
        frame.getContentPane().add(menuBar, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (openfile) {
                    itemSave.setEnabled(true);
                }
                textAreaChanged = true;
                tag.setText("|| - Modified file -");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (openfile) {
                    itemSave.setEnabled(true);
                }
                textAreaChanged = true;
                tag.setText("|| - Modified file -");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (openfile) {
                    itemSave.setEnabled(true);
                }
                textAreaChanged = true;
                tag.setText("|| - Modified file -");
            }
        });
        textArea.setEditable(false);
        textArea.setBackground(Color.GRAY);
        panelScroll = new JScrollPane(textArea);
        panelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelScroll.setPreferredSize(new Dimension(200, 200));
        frame.getContentPane().add(panelScroll, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 400);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (textAreaChanged && openfile) {
                    do {
                        int dialogValue = JOptionPane.showConfirmDialog(frame,
                                "Would you like to save the current file?",
                                "Save the changes",
                                JOptionPane.YES_NO_OPTION);
                        if (dialogValue == JOptionPane.NO_OPTION) {
                            break;
                        }
                    } while (!saveFile());
                }
                super.windowClosing(e);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == itemNew) {
            if (newFile()) {
                textAreaChanged = false;
                openfile = true;
                itemPassword.setEnabled(true);
                textArea.setEditable(true);
                textArea.setBackground(Color.WHITE);
                this.tag.setText("");
            }
        }
        if (e.getSource() == itemOpen) {
            if (openFile()) {
                textAreaChanged = false;
                openfile = true;
                itemPassword.setEnabled(true);
                textArea.setEditable(true);
                textArea.setBackground(Color.WHITE);
                this.tag.setText("");
            }
        }
        if (e.getSource() == itemSave) {
            if (saveFile()) {
                textAreaChanged = false;
                itemSave.setEnabled(false);
                this.tag.setText("");
            }
        }
        if (e.getSource() == itemPassword) {
            changePassword();
        }
    }

    private boolean newFile() {
        if (textAreaChanged && openfile) { //Check if the textArea had changed and if there is a file opened
            do {
                int dialogValue = JOptionPane.showConfirmDialog(frame,
                        "Would you like to save the current file?",
                        "Save the changes",
                        JOptionPane.YES_NO_OPTION);
                if (dialogValue == JOptionPane.NO_OPTION) {
                    break;
                }
            } while (!saveFile());
        }
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showSaveDialog(panelScroll);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (file.exists()) {
                JOptionPane.showMessageDialog(frame,
                        "The file already exists.",
                        "File error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
            PasswordChangeDialog changePassword = new PasswordChangeDialog(frame, "Set new password");
            char[] password = changePassword.getPassword();
            changePassword.dispose();
            if (password != null) {
                if (process.newFile(file, password)) {
                    textArea.setText("");
                    frame.setTitle(new String(frameTitle + " - File: " + file.getName()));
                    return true;
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Error on creation file process.",
                            "New file error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            return true;
        }
        return false;
    }

    private boolean openFile() {
        if (textAreaChanged && openfile) {  /////////check first if the text had changed.
            do {
                int dialogValue = JOptionPane.showConfirmDialog(frame,
                        "Would you like to save the current file?",
                        "Save the changes",
                        JOptionPane.YES_NO_OPTION);
                if (dialogValue == JOptionPane.NO_OPTION) {
                    break;
                }
            } while (!saveFile());
        }
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(panelScroll);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            PasswordDialog checkPassword = new PasswordDialog(frame, "Insert password");
            if (checkPassword.getPassword() == null) {
                return false;
            }
            if (process.checkPasswordFile(checkPassword.getPassword(), file)) {
                textArea.setText("");
                process.loadFile(file, textArea);
                frame.setTitle(new String(frameTitle + " - File: " + file.getName()));
                return true;
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Incorrect password.",
                        "Password error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    private boolean saveFile() {
        // Save on the current file, ask for the password and check it.
        PasswordDialog checkPassword = new PasswordDialog(frame, "Insert password");
        if (checkPassword.getPassword() == null) {
            return false;
        }
        if (process.checkPasswordFile(checkPassword.getPassword())) {
            //Save the current document.
            process.saveFile(textArea);
            return true;
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Incorrect password.",
                    "Password error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private boolean changePassword() {
        PasswordDialog checkPassword = new PasswordDialog(frame, "Insert current password");
        if (checkPassword.getPassword() == null) {
            return false;
        }
        if (process.checkPasswordFile(checkPassword.getPassword())) {
            PasswordChangeDialog changePassword = new PasswordChangeDialog(frame, "Set new password");
            char[] password = changePassword.getPassword();
            changePassword.dispose();
            if (process.changePassword(password)) {
                return true;
            }
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Incorrect password.",
                    "Password error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

}
