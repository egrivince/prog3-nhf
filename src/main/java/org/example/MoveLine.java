package org.example;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class MoveLine extends JComponent{
    
    private List<List<Point>> segmentList;
    private Color lineColor = new Color(255,0,0,150);

    public MoveLine() {
        segmentList = new ArrayList<>();
    }

    public void addPoint(Point point) {
        segmentList.getLast().add(point);
    }
    public void clearPoints() {
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

        Path2D path = new Path2D.Double();

        for(List<Point> segment : segmentList) {
            if(segment.isEmpty()) continue;
            path.moveTo(segment.get(0).x, segment.get(0).y);
            for(int ind=1; ind<segment.size(); ind++) {
                Point from = segment.get(ind-1);
                Point to = segment.get(ind);
                //Line2D.Double line = new Line2D.Double(from.x, from.y, to.x, to.y);
                path.lineTo(to.x, to.y);
                //g2d.draw(line);
            }
        }
        g2d.draw(path);
    }

    public void setColor(Color color) {
        this.lineColor = color;
    }
}
