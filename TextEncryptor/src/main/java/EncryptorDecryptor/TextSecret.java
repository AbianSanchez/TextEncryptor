package EncryptorDecryptor;

import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TextSecret {
    private char[] password;
    private String filePassword;
    private SecretKeySpec key;
    private File file;
    Cryptographer crypto;

    public static void main(String[] args) {
        new TextSecret();
    }

    public TextSecret(){
        crypto = new Cryptographer();
        this.key = null;
    }

    public boolean newPassword (char[] word) {
        // Set the new password encrypted in the file.
        try{
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
            writer = new PrintWriter(file);
            this.key = crypto.keyGenerator(new String(word));
            this.filePassword = crypto.encrypter(String.valueOf(key), key); //Encryp the encrypted password to save on file.
            writer.println(filePassword);
            writer.close();

            //Check if the password was saved
            Scanner scanner = new Scanner(file); //GET the first line with de password
            String firstLine = scanner.nextLine();

            if(filePassword.equals(""+firstLine)){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkPasswordFile (char[] password){
        //Check if the file's password match with the password given.
        try{
            Scanner scanner = new Scanner(file); //GET the first line with de password
            String firstLine = scanner.nextLine();

            SecretKeySpec keyTemp = crypto.keyGenerator(new String(password));
            String filePassword = crypto.encrypter(String.valueOf(keyTemp), keyTemp);

            if(filePassword.equals(""+firstLine)){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkPasswordFile (char[] password, File file){
        //Check if the file's password match with the password given and set file and key.
        try{
            Scanner scanner = new Scanner(file); //GET the first line with de password
            String firstLine = scanner.nextLine();

            SecretKeySpec keyTemp = crypto.keyGenerator(new String(password));
            String filePassword = crypto.encrypter(String.valueOf(keyTemp), keyTemp);

            if(filePassword.equals(""+firstLine)){
                this.file = file;
                this.key = keyTemp;
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean saveFile (JTextArea textArea){
        try {
            Scanner scanner = new Scanner(file); //GET the first line with de password
            String firstLine = scanner.nextLine();

            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
            writer = new PrintWriter(file);
            writer.println(firstLine); //Save the password in the first line
            for (String line : textArea.getText().split("\\n")){
                writer.println(crypto.encrypter(line,key));
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean newFile(File file, char[] password){  // <------------------------------------------
        this.file = file;
        this.password = password;
        try {
            file.createNewFile();
            if(newPassword(password)){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void loadFile(File file, JTextArea textArea) {  // <------------------------------------------
        this.file = file;
        try {
            Scanner readLine = new Scanner(file);
            readLine.nextLine();  //Skip the first line with password
            while (readLine.hasNextLine()) {
                String data = readLine.nextLine();
                textArea.append(crypto.decrypter(data,key)  + "\n");
            }
            readLine.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean changePassword(char[] password) {
        try{
            ArrayList content = new ArrayList();
            Scanner readLine = new Scanner(file);
            while (readLine.hasNextLine()) {
                content.add(crypto.decrypter(readLine.nextLine(),key)); //Decryp the file
            }
            this.key = crypto.keyGenerator(new String(password));
            this.filePassword = crypto.encrypter(String.valueOf(key), key);
            content.set(0,key);
            readLine.close();
            // Write the content in the file
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
            writer = new PrintWriter(file);
            for (int i = 0; i < content.size(); i++) {
                writer.println(crypto.encrypter(String.valueOf(content.get(i)), key)); //Encryp the file with the new key
            }
            writer.close();
            //Check if the password was saved
            Scanner scanner = new Scanner(file); //GET the first line with de password
            String firstLine = scanner.nextLine();
            if(filePassword.equals(""+firstLine)){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
