package org.example;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class MoveLine extends JComponent{
    
    //private List<Point> endPoints;
    private List<List<Point>> segmentList;
    private Color lineColor = new Color(255,0,0,150);

    public MoveLine() {
        //endPoints = new ArrayList<>();
        segmentList = new ArrayList<>();
    }

    public void addPoint(Point point) {
        //endPoints.add(point);
        segmentList.getLast().add(point);
    }
    public void clearPoints() {
        //endPoints.clear();
        segmentList.clear();
        this.newLineSegment();
    }
    public void newLineSegment() {
        segmentList.add(new ArrayList<>());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        

        BasicStroke lineStroke = new BasicStroke(
            4.0f,
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_BEVEL,
            10.0f,
            null,
            0.0f
        );
        g2d.setStroke(lineStroke);
        g2d.setColor(lineColor);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for(List<Point> segment : segmentList) {
            for(int ind=1; ind<segment.size(); ind++) {
                Point from = segment.get(ind-1);
                Point to = segment.get(ind);
                Line2D.Double line = new Line2D.Double(from.x, from.y, to.x, to.y);
                g2d.draw(line);
            }
        }
    }

    public void setColor(Color color) {
        this.lineColor = color;
    }
}
