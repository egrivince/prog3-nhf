package org.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class ActiveRowTriangle extends JComponent{

    private Point tip;
    Color triangleColor = Color.BLACK;

    public ActiveRowTriangle(Point tip) {
        this.tip = tip;
    }
    public void setTip(Point tip) {
        this.tip = tip;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //triangleColor = new Color(0,0,0,255);
        g2d.setColor(triangleColor);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        int[] pointsX = {tip.x, tip.x+15, tip.x+15};
        int[] pointsY = {tip.y, tip.y+10, tip.y-10};
        Polygon triangle = new Polygon(pointsX, pointsY, 3);

        //g2d.drawRect(tip.x, tip.y, 10, 10);
        g2d.fill(triangle);
    }

    public void setColor(Color color) {
        this.triangleColor = color;
    }
}
