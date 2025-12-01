package org.example;

import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import com.viaoa.image.jpg.JFIFInputStream;

public class FileChooser extends JFileChooser{
    GuiListener guiListener;

    public FileChooser(GuiListener l) {
        super(".");
        guiListener = l;
    }   

    
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
