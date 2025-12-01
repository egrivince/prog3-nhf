package org.example;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The JPanels representing the tiles on the screen.
 */
public class CellPanel extends JPanel{
    /**
     * Used to give signals to the game managing functions.
     */
    private GuiListener guiListener;
    /**
     * Sets the guilistener
     */
    public void setGuiListener(GuiListener l) {
        guiListener = l;
    }

    /**
     * The position of the tile on the screen.
     * The same as the tile's position in the board.
     */
    private Pos pos;
    /**
     * True if the mouse is dragging.
     * To differentiate clicks and the mouse being released at the end of a drag.
     */
    private boolean isDragging = false;

    public CellPanel(Pos pos) {
        this.pos = pos;
        this.setLayout(new GridBagLayout());
        this.setBackground(Params.TILECOLOR); 
        //this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
    }

    public Pos getPos() {
        return pos;
    }

    /**
     * Adds all of the neccessary mouse listeners to the cellpanel.
     * Adds the clicking, mouse released and mouse dragged functions.
     * Manages the isDragged boolean value.
     */
    public void addAllListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                guiListener.clickedOnTile(pos);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if(isDragging) {
                    Component source = (Component) e.getSource();
                    Component grandpa = source.getParent().getParent();
                    Point point = SwingUtilities.convertPoint(source, e.getPoint(), grandpa);
                    Component target = SwingUtilities.getDeepestComponentAt(grandpa, point.x, point.y);
                    while(target != null && !(target instanceof CellPanel)) {
                        target = target.getParent(); //go upwards to the cellpanel(in case of a jlabel)
                    }
                    if(target instanceof CellPanel) {
                        guiListener.mouseDragReleased();
                    }
                    else {
                        guiListener.failedMouseDragRelease();
                    }
                    
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                isDragging = false;
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                isDragging = true;

                Component source = (Component) e.getSource();
                Component grandpa = source.getParent().getParent();
                Point point = SwingUtilities.convertPoint(source, e.getPoint(), grandpa);
                Component target = SwingUtilities.getDeepestComponentAt(grandpa, point.x, point.y);
                while(target != null && !(target instanceof CellPanel)) {
                    target = target.getParent(); //go upwards to the cellpanel(in case of a jlabel)
                }
                if(target instanceof CellPanel) {
                    CellPanel targetCellPanel = (CellPanel) target;
                    guiListener.tileDrag(targetCellPanel.pos); //its also a cellpanel so i dont need a getter
                }
            }
        });

    }
}
