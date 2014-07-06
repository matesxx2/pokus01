/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simpledraw.drawable;

import java.awt.Graphics;

/**
 *
 * @author Martin
 */
public class Line implements Drawable{
    
    public final int x1,x2,y1,y2;

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    
    public Line(Point a, Point b){
        this(a.x,a.y,b.x,b.y);
    }
    
    
    @Override
    public void draw(Graphics g) {
        g.drawLine(x1, y1, x2, y2);
    }
    
}
