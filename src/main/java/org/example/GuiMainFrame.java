package org.example;

import javax.swing.*;
import java.awt.*;

public final class GuiMainFrame extends JFrame{
    private GuiListener guiListener;
    public void setGuiListener(GuiListener l) {
        guiListener = l;
    }

    public JPanel overPanel;
    public JPanel boardPanel;
    public JPanel gridBoardPanel;
    public JPanel topContainer;
    public JPanel bottomContainer;
    public CellPanel topGoalPanel;
    public CellPanel bottomGoalPanel;
    public JTextArea logArea;

    public MoveLine moveLine;
    public ActiveRowTriangle topTriangle;
    public ActiveRowTriangle bottomTriangle;


    public GuiMainFrame(GuiListener l) {
        this.setGuiListener(l);

        this.setTitle("Gyges");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.black);
        this.setLayout(new BorderLayout());

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(Params.BOARDCOLOR);
        sidePanel.setPreferredSize(new Dimension(Params.SIDEPANEL_WIDTH,Params.HEIGHT+2*Params.BORDERSIZE));
        GameButton newGameButton = new GameButton("New Game");
        newGameButton.addActionListener( actionEvent -> guiListener.newGamePressed() );
        newGameButton.setAlignmentX(0.5f);
        sidePanel.add(newGameButton);
        logArea = new JTextArea(10, 30); //rows, 30 long
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setFont(Params.LOG_FONT);
        logArea.setBackground(Params.BOARDCOLOR);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        scrollPane.setAlignmentX(0.5f);
        scrollPane.setPreferredSize(new Dimension(Params.SIDEPANEL_WIDTH,250));
        scrollPane.setMaximumSize(new Dimension(Params.SIDEPANEL_WIDTH,250));
        sidePanel.add(scrollPane);
        logArea.append("Message Log\n");



        overPanel = new JPanel();
        overPanel.setOpaque(false);
        overPanel.setBounds(0,0,Params.WIDTH+2*Params.BORDERSIZE,Params.HEIGHT+2*Params.BORDERSIZE);
        overPanel.setLayout(null);
        moveLine = new MoveLine();
        moveLine.setBounds(0,0,Params.WIDTH+2*Params.BORDERSIZE,Params.HEIGHT+2*Params.BORDERSIZE);
        overPanel.add(moveLine);
        topTriangle = new ActiveRowTriangle(new Point());
        bottomTriangle = new ActiveRowTriangle(new Point());
        topTriangle.setBounds(0,0,Params.WIDTH+2*Params.BORDERSIZE,Params.HEIGHT+2*Params.BORDERSIZE);
        bottomTriangle.setBounds(0,0,Params.WIDTH+2*Params.BORDERSIZE,Params.HEIGHT+2*Params.BORDERSIZE);
        overPanel.add(topTriangle);
        overPanel.add(bottomTriangle);
        

        gridBoardPanel = new JPanel();
        gridBoardPanel.setLayout(new GridLayout(6,6,Params.GAPSIZE,Params.GAPSIZE));
        gridBoardPanel.setBackground(Params.BOARDCOLOR);
        gridBoardPanel.setPreferredSize(new Dimension(Params.WIDTH,Params.HEIGHT));
        gridBoardPanel.setMaximumSize(new Dimension(Params.WIDTH,Params.HEIGHT));
        gridBoardPanel.setBounds(0,0,Params.WIDTH+2*Params.BORDERSIZE,Params.HEIGHT+2*Params.BORDERSIZE);
        

        topGoalPanel = new CellPanel(new Pos(Player.TOP));
        topContainer = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        topContainer.setOpaque(false);
        topGoalPanel.setPreferredSize(new Dimension(Params.TILESIZE,Params.TILESIZE));
        topContainer.add(topGoalPanel);
        bottomGoalPanel = new CellPanel(new Pos(Player.BOTTOM));
        bottomContainer = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        bottomContainer.setOpaque(false);
        bottomGoalPanel.setPreferredSize(new Dimension(Params.TILESIZE,Params.TILESIZE));
        bottomContainer.add(bottomGoalPanel);
        

        boardPanel = new JPanel();
        boardPanel.setBackground(Params.BOARDCOLOR);
        //boardPanel.setPreferredSize(new Dimension(width+2*borderSize,height+2*borderSize));
        boardPanel.setBounds(0,0,Params.WIDTH+2*Params.BORDERSIZE,Params.HEIGHT+2*Params.BORDERSIZE);
        boardPanel.setLayout(new BorderLayout(0,Params.GAPSIZE));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(Params.BORDERSIZE, Params.BORDERSIZE, Params.BORDERSIZE, Params.BORDERSIZE));
        boardPanel.add(topContainer, BorderLayout.NORTH);
        boardPanel.add(gridBoardPanel, BorderLayout.CENTER);
        boardPanel.add(bottomContainer, BorderLayout.SOUTH);
        
        JLayeredPane layered = new JLayeredPane();
        layered.setPreferredSize(new Dimension(Params.WIDTH+2*Params.BORDERSIZE,Params.HEIGHT+2*Params.BORDERSIZE));
        layered.add(boardPanel, JLayeredPane.DEFAULT_LAYER);
        layered.add(overPanel, JLayeredPane.PALETTE_LAYER);

        this.add(layered, BorderLayout.WEST);
        this.add(sidePanel, BorderLayout.CENTER);
    }
}
