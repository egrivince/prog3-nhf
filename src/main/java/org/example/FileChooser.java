package org.example;

import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

/**
 * Manages the saving and loading of games.
 */
public class FileChooser extends JFileChooser{
    GuiListener guiListener;

    public FileChooser(GuiListener l) {
        super(".");
        guiListener = l;
    }   

    /**
     * Manages the game saving.
     * Writes the gamemanager object to the selected file to read later.
     */
    public void saveGame() {
        int option = this.showSaveDialog(null);
        
        if(option == JFileChooser.APPROVE_OPTION) {
            File saveFile = this.getSelectedFile();
            //System.out.println(saveFile.getAbsolutePath());
            try {
                FileOutputStream fos = new FileOutputStream(saveFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                GameManager gameManagerToWrite = guiListener.getGameManager();
                oos.writeObject(gameManagerToWrite);
                oos.close();
                fos.close();
                System.err.println("successful save");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Manages the game loading.
     * Reads the gamemanager object and calls gamemanager's loadgame.
     */
    public void loadGame() {
        int option = this.showOpenDialog(null);

        if(option == JFileChooser.APPROVE_OPTION) {
            File loadFile = this.getSelectedFile();
            try {
                FileInputStream fis = new FileInputStream(loadFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                GameManager gameManagerToRead = (GameManager) ois.readObject();
                ois.close();
                fis.close();
                System.out.println("Successful read");
                guiListener.loadGame(gameManagerToRead);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
        
}
