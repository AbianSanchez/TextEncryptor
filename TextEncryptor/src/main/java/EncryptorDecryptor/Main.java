package EncryptorDecryptor;

import java.io.File;

public class Main {
    private TextSecret fileProcessor;
    private TextDisplay guiWindow;

    public static void main(String[] args) {
        new Main();
    }

    public Main (){
        fileProcessor = new TextSecret();
        guiWindow = new TextDisplay(fileProcessor);
    }

}
