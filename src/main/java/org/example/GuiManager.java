package org.example;

import javax.swing.*;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.LogManager;

public final class GuiManager implements GameListener {
    private GuiListener guiListener;
    public void setGuiListener(GuiListener l) {
        guiListener = l;
    }

    private final GuiMainFrame mainFrame;


    private final List<List<CellPanel>> cellPanels;
    private final CellPanel topCellPanel;
    private final CellPanel bottomCellPanel;

    //private Map<Coordinate, CellPanel> cellPanelMap;
    
    public GuiManager(GuiListener l) {
        this.setGuiListener(l);

        mainFrame = new GuiMainFrame(l); //all of the panels and other graphics objects are here to clean up
        
        cellPanels = new ArrayList<>();
        for(int row=0; row<6; row++) {
            List<CellPanel> CellPanelRow = new ArrayList<>();
            for(int col=0; col<6; col++) {
                CellPanel cellPanel = new CellPanel(Pos.at(row, col));
                cellPanel.setGuiListener(guiListener);
                cellPanel.addAllListeners();

                CellPanelRow.add(cellPanel);
                mainFrame.gridBoardPanel.add(cellPanel);
            }
            cellPanels.add(CellPanelRow);
        }

        topCellPanel = mainFrame.topGoalPanel;
        bottomCellPanel = mainFrame.bottomGoalPanel;
        
        topCellPanel.setGuiListener(guiListener);
        topCellPanel.addAllListeners();
        bottomCellPanel.setGuiListener(guiListener);
        bottomCellPanel.addAllListeners();



        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    @Override
    public void boardChanged(BoardReadOnly board) {
        for(int row=0; row<6; row++) {
            for(int col=0; col<6; col++) {
                CellPanel cp = getCellPanel(Pos.at(row, col));
                cp.removeAll();
                Piece piece = board.getTile(Pos.at(row, col)).getPiece();
                if(piece != null) {
                    PieceLabel pl = new PieceLabel(piece.getType());
                    cp.add(pl);
                    
                    //System.out.println("pl added"+i+j);
                }
                cp.revalidate();
                cp.repaint();
            }
        }
        for(CellPanel cp : List.of(topCellPanel, bottomCellPanel)) {
            cp.removeAll();
                Piece piece = board.getTile(cp.getPos()).getPiece();
                if(piece != null) {
                    PieceLabel pl = new PieceLabel(piece.getType());
                    cp.add(pl);
                    
                    //System.out.println("pl added"+i+j);
                }
                cp.revalidate();
                cp.repaint();
        }
    }

    @Override
    public void addTileDragged(Pos pos) {
        JPanel cell = getCellPanel(pos);
        Point topLeftPointConverted = SwingUtilities.convertPoint(cell, new Point(0,0), mainFrame.overPanel);
        int centerX = topLeftPointConverted.x + cell.getWidth()/2;
        int centerY = topLeftPointConverted.y + cell.getHeight()/2;
        mainFrame.moveLine.addPoint(new Point(centerX, centerY));
        mainFrame.overPanel.revalidate();
        mainFrame.overPanel.repaint();
    }

    @Override
    public void startNewDrag() {
        mainFrame.moveLine.clearPoints();
        mainFrame.overPanel.repaint();
    }

    @Override
    public void moveStarted(Pos pos) { //make the piece label transparent or other color to signal that it is not really there, its the one making the move
        CellPanel cp = getCellPanel(pos);
        JComponent jc = (JComponent) cp.getComponent(0);
        jc.setBackground(Params.ACTIVE_PIECE_COLOR);
        cp.revalidate();
        cp.repaint();
    }

    @Override
    public void newDragSegment() {
        mainFrame.moveLine.newLineSegment();
    }

    @Override
    public void setActiveRow(Player player, int row) { //set the active row pointers for the given player, no color changes
        JPanel cell = getCellPanel(Pos.at(row, 5));
        Point topLeftPoint = cell.getLocation();
        int centerY = topLeftPoint.y + cell.getHeight()/2;
        int coordX = topLeftPoint.x + cell.getWidth() + 8;
        Point activeRowPoint = new Point(coordX, centerY);
        Point activeRowPointConverted = SwingUtilities.convertPoint(mainFrame.gridBoardPanel, activeRowPoint, mainFrame.overPanel);


        if(player == Player.BOTTOM) { //bottom player
            mainFrame.bottomTriangle.setTip(activeRowPointConverted);
        }
        else { //top player
            mainFrame.topTriangle.setTip(activeRowPointConverted);
        }

        mainFrame.overPanel.repaint();
    }

    @Override
    public void setTriangleColors(Player player) {
        
        if(player == Player.BOTTOM) { //bottom player
            mainFrame.bottomTriangle.setColor(Params.ACTIVE_RED);    
            mainFrame.topTriangle.setColor(Params.PASSIVE_BLUE);
        }
        else { //top player
            mainFrame.bottomTriangle.setColor(Params.PASSIVE_RED);    
            mainFrame.topTriangle.setColor(Params.ACTIVE_BLUE);
        }
    }

    @Override
    public void setLineColors(Player player) {
        if(player == Player.BOTTOM) { //bottom player
            mainFrame.moveLine.setColor(Params.LINE_RED);
        }
        else { //top player
            mainFrame.moveLine.setColor(Params.LINE_BLUE);
        }
    }

    public CellPanel getCellPanel(Pos pos) {
        if(pos.col == Pos.COL_SPEC) {
            if(pos.row == Pos.BOTTOMGOAL) return bottomCellPanel;
            else return topCellPanel;
        }
        return cellPanels.get(pos.row).get(pos.col);
    }

    @Override
    public void logMessage(String message) {
        mainFrame.logArea.append(message+"\n");
        mainFrame.logArea.setCaretPosition(mainFrame.logArea.getDocument().getLength());
    }


}
