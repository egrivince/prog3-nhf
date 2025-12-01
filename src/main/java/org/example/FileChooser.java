package org.example;

import java.awt.event.*;

import javax.swing.JFileChooser;

public class FileChooser extends JFileChooser implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        int r = this.showSaveDialog(null);

        if(r == JFileChooser.APPROVE_OPTION) {
            System.out.println("saved");
        }
    }
    
}
