package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * The class responsible for the genera loadout of the window.
 * Manages the swing objects when launching the program.
 */
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
    public FileChooser fileChooser;

    public MoveLine moveLine;
    public ActiveRowTriangle topTriangle;
    public ActiveRowTriangle bottomTriangle;

    /**
     * Constructor, sets the layout of the panels and buttons and adds their listeners.
     */
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
        sidePanel.setBorder(BorderFactory.createEmptyBorder(Params.BORDERSIZE, Params.BORDERSIZE, Params.BORDERSIZE, Params.BORDERSIZE));

        GameButton newGameButton_PvP = new GameButton("New Game (Player vs Player)");
        newGameButton_PvP.addActionListener( actionEvent -> guiListener.newGamePvPPressed() );
        sidePanel.add(newGameButton_PvP);
        GameButton newGameButton_PvAi = new GameButton("New Game (Player vs Ai");
        newGameButton_PvAi.addActionListener( actionEvent -> guiListener.newGamePvAiPressed() );
        sidePanel.add(newGameButton_PvAi);
        GameButton newGameButton_AivAi = new GameButton("New Game (Ai vs Ai");
        newGameButton_AivAi.addActionListener( actionEvent -> guiListener.newGameAivAiPressed() );
        sidePanel.add(newGameButton_AivAi);

        //fileChooser = new JFileChooser("./");
        fileChooser = new FileChooser(guiListener);
        GameButton saveGameButton = new GameButton("Save Game");
        saveGameButton.addActionListener(actionEvent -> fileChooser.saveGame());
        sidePanel.add(saveGameButton);
        GameButton loadGameButton = new GameButton("Load Game");
        loadGameButton.addActionListener(actionEvent -> fileChooser.loadGame());
        sidePanel.add(loadGameButton);

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
        topGoalPanel.setPreferredSize(new Dimension(Params.WIDTH,Params.TILESIZE));
        topContainer.add(topGoalPanel);
        bottomGoalPanel = new CellPanel(new Pos(Player.BOTTOM));
        bottomContainer = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        bottomContainer.setOpaque(false);
        bottomGoalPanel.setPreferredSize(new Dimension(Params.WIDTH,Params.TILESIZE));
        bottomContainer.add(bottomGoalPanel);
        

        boardPanel = new JPanel();
        boardPanel.setBackground(Params.BOARDCOLOR);
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
