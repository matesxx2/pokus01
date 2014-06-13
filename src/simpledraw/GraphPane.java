/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simpledraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import simpledraw.drawable.DrawArea;

/**
 * This class represent basic pane for graph.
 * It enables set colors, grids,...
 * @author kramarm
 */
public class GraphPane extends DrawArea{
    
    private final int BORDER_SPACE = 30;
    private final int GRID_SIZE_DEFAULT = 70;
    private final int GRID_OFFSET_DEFAULT = 20;
    
    private Rectangle borderRectangle;
    private boolean verticalGridFlag = true;
    private boolean horizontalGridFlag = true;
   
    
    
    
    public GraphPane(int width, int height){
        super(width, height);   
    }

    public boolean isVerticalGridFlag() {
        return verticalGridFlag;
    }

    public void setVerticalGridFlag(boolean verticalGridFlag) {
        this.verticalGridFlag = verticalGridFlag;
    }

    public boolean isHorizontalGridFlag() {
        return horizontalGridFlag;
    }

    public void setHorizontalGridFlag(boolean horizontalGridFlag) {
        this.horizontalGridFlag = horizontalGridFlag;
    }
    
    
    @Override
    protected void paintAtScrollPane(Graphics g) {
        super.paintAtScrollPane(g); //To change body of generated methods, choose Tools | Templates.
        paintAreaBorder(g);
        
        if(verticalGridFlag)
            paintVerticalGrid(g, GRID_SIZE_DEFAULT, GRID_OFFSET_DEFAULT);
        
        if(horizontalGridFlag)
            paintHorizontalGrid(g, GRID_SIZE_DEFAULT, GRID_OFFSET_DEFAULT);
        
    }
    
    
    
    /**
     * Paints border around drawing area and every outside Drawable object
     * is repaint by background color. The outside place is used for painting
     * scale's labels
     * @param g 
     */
    private void paintAreaBorder(Graphics g){
        
        Rectangle r = getScrollVisibleArea();
        Rectangle r2 = getDrawVisibleArea();
        Color cBorder = getDrawingArea().getBackground();
        //cBorder = Color.BLUE;
        Color cOrig = g.getColor();
        
        g.setColor(cBorder);
        g.fillRect(r.x, r.y, r2.width, BORDER_SPACE);
        g.fillRect(r.x, r2.height-BORDER_SPACE, r2.width, BORDER_SPACE+2);
        g.fillRect(r.x, r.y, BORDER_SPACE, r2.height);
        g.fillRect(r2.width-BORDER_SPACE, r.y, BORDER_SPACE+2, r2.height);
        
        g.setColor(cOrig);
        borderRectangle = new Rectangle(r.x+BORDER_SPACE, r.y+BORDER_SPACE, r2.width-2*BORDER_SPACE, r2.height-2*BORDER_SPACE);
        g.drawRect(borderRectangle.x, borderRectangle.y, borderRectangle.width, borderRectangle.height);
    }
    
    /**
     * Paints vertical grid lines
     * @param g
     * @param gap space between lines in pixels
     * @param offset space between border and first grid line 
     */
    private void paintVerticalGrid(Graphics g, int gap, int offset){
        Color cOrig = g.getColor();
        g.setColor(Color.GRAY);
        
        int lastX = borderRectangle.x + borderRectangle.width;
        int x = borderRectangle.x + offset;
        for(int i=0; x<lastX ;i++){
            x = borderRectangle.x + offset + i*gap;
            if(x>borderRectangle.x)
                g.drawLine(x, borderRectangle.y, x, borderRectangle.y+borderRectangle.height);
        }
        
        g.setColor(cOrig);
    }
    
    /**
     * Paints horizontal grid lines
     * @param g
     * @param gap space between lines in pixels
     * @param offset space between border and first grid line 
     */
    private void paintHorizontalGrid(Graphics g, int gap, int offset){
        Color cOrig = g.getColor();
        g.setColor(Color.GRAY);
        
        int lastY = borderRectangle.y + borderRectangle.height;
        int y = borderRectangle.y + offset;
        for(int i=0; y<lastY ;i++){
            y = borderRectangle.y + offset + i*gap;
            if(y>borderRectangle.y)
                g.drawLine(borderRectangle.x, y, borderRectangle.x+borderRectangle.width, y);
        }
        
        g.setColor(cOrig);
    }
    
    
    
   
}
