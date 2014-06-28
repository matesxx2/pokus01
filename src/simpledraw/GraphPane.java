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
 * This class is used as base panel for drawing graphs.
 * It uses {@link DrawArea} for painting at screen and
 * allows to display borders, grids, labels,...
 * and catches scroll bar movement in the method scrollBarMoved
 * @author Martin Kramar
 */
public class GraphPane extends DrawArea{
    
    private final int BORDER_SPACE = 30;
    private final int GRID_SIZE_DEFAULT = 70;
    private final int GRID_OFFSET_DEFAULT = 20;
    
    /**
     * It is a bordered area inside scrollPane.
     * All {@link Drawable} objects which are visible at scrollpane but are
     * outside this area are not painted because this outside are is used for
     * painting of grid labels
     */
    private Rectangle borderRectangle;
    private boolean verticalGridFlag = true;
    private boolean horizontalGridFlag = true;
    private boolean verticalLabelsFlag = true;
    private boolean horizontalLabelsFlag = true;
    
    //spaces betwenn boundary of displayed area and component
    private int spaceNorth = BORDER_SPACE;
    private int spaceSouth = BORDER_SPACE;
    private int spaceEast = BORDER_SPACE;
    private int spaceWest = BORDER_SPACE;
    
    //*pixels between grid lines*/
    private int horizontalGridGap = GRID_SIZE_DEFAULT;
    //*pixels between border and first grid line*/
    private int horizontalGridOffset = GRID_OFFSET_DEFAULT;
    //*pixels between grid lines*/
    private int verticalGridGap = GRID_SIZE_DEFAULT;
    //*pixels between border and first grid line*/
    private int verticalGridOffset = GRID_OFFSET_DEFAULT;
    
    private String[] horizontalLabels = null;
    private String[] verticalLabels = null;
   
    
    
    /**
     * Creates graphPane with specified drawing area size
     * @param width in pixels
     * @param height in pixels
     */
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
            paintVerticalGrid(g, verticalGridGap, verticalGridOffset);
        
        if(horizontalGridFlag)
            paintHorizontalGrid(g, horizontalGridGap, horizontalGridOffset);
        
        paintLabels(g);
        
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
    public void paintVerticalGrid(Graphics g, int gap, int offset){
        Color cOrig = g.getColor();
        g.setColor(Color.GRAY);
        
        int lastX = borderRectangle.x + borderRectangle.width;
        int x;
        for(int i=0; (x = borderRectangle.x + verticalGridOffset + i*verticalGridGap)<lastX ;i++){
            if(x>borderRectangle.x)
                g.drawLine(x, borderRectangle.y, x, borderRectangle.y+borderRectangle.height);
            
            x = borderRectangle.x + offset + i*gap;
        }
        
        g.setColor(cOrig);
    }
    
    
    
    /**
     * Paints horizontal grid lines
     * @param g
     * @param gap space between lines in pixels
     * @param offset space between border and first grid line 
     */
    public void paintHorizontalGrid(Graphics g, int gap, int offset){
        Color cOrig = g.getColor();
        g.setColor(Color.GRAY);
        
        int lastY = borderRectangle.y + borderRectangle.height;
        int y;
        for(int i=0; (y = borderRectangle.y + horizontalGridOffset + i*horizontalGridGap)<lastY ;i++){      
            if(y>borderRectangle.y)
                g.drawLine(borderRectangle.x, y, borderRectangle.x+borderRectangle.width, y);
        }
        
        g.setColor(cOrig);
    }
    
    /**
     * Paints horizontal and vertical labels if corespondent flags are set 
     * to <b>true</b>.
     * @param g 
     */
    private void paintLabels(Graphics g){
        if(verticalLabelsFlag)
            paintVerticalLabels(g);
        
        if(horizontalLabelsFlag)
            paintHorizontalLabels(g);
    }
   
    private void paintHorizontalLabels(Graphics g){
        int offset = 10;
        int lastY = borderRectangle.y + borderRectangle.height;
        int y;
        for(int i=0; (y = borderRectangle.y + horizontalGridOffset + i*horizontalGridGap)<lastY ;i++){   
            if(y>borderRectangle.y && horizontalLabels != null && i < horizontalLabels.length)
                g.drawString(horizontalLabels[i], borderRectangle.x-offset, y);
        }
    }
    
    private void paintVerticalLabels(Graphics g){
        int offset = 15;
        int lastX = borderRectangle.x + borderRectangle.width;
        int x;
        for(int i=0; (x = borderRectangle.x + verticalGridOffset + i*verticalGridGap)<lastX ;i++){
            if(x>borderRectangle.x && verticalLabels != null && i < verticalLabels.length)
                g.drawString(verticalLabels[i], x, borderRectangle.y+borderRectangle.height + offset);
        }
    }
    
    /**
     * 
     * @return area bordered area inside scrollPane
     */
    public Rectangle getBorderRectangle() {
        return borderRectangle;
    }
    /**
     * Returns space between north boundary of component and visible area
     * @return space in pixels
     */
    public int getSpaceNorth() {
        return spaceNorth;
    }
    /**
     * Sets space between north boundary of component and visible area
     * @param spaceNorth - size of space in pixels 
     */
    public void setSpaceNorth(int spaceNorth) {
        this.spaceNorth = spaceNorth;
    }
    /**
     * Returns space between south boundary of component and visible area
     * @return space in pixels
     */
    public int getSpaceSouth() {
        return spaceSouth;
    }
    /**
     * Sets space between south boundary of component and visible area
     * @param spaceSouth - size of space in pixels 
     */
    public void setSpaceSouth(int spaceSouth) {
        this.spaceSouth = spaceSouth;
    }
    /**
     * Returns space between east boundary of component and visible area
     * @return space in pixels
     */
    public int getSpaceEast() {
        return spaceEast;
    }
    /**
     * Sets space between east boundary of component and visible area
     * @param spaceEast - size of space in pixels 
     */
    public void setSpaceEast(int spaceEast) {
        this.spaceEast = spaceEast;
    }
    /**
     * Returns space between west boundary of component and visible area
     * @return space in pixels
     */
    public int getSpaceWest() {
        return spaceWest;
    }
    /**
     * Sets space between west boundary of component and visible area
     * @param spaceWest - size of space in pixels 
     */
    public void setSpaceWest(int spaceWest) {
        this.spaceWest = spaceWest;
    }
    /**
     * @return number of pixels between horizontal grid lines
     */
    public int getHorizontalGridGap() {
        return horizontalGridGap;
    }
    /**
     * Sets number of pixels between horizontal grid lines
     * @param horizontalGridGap 
     */
    public void setHorizontalGridGap(int horizontalGridGap) {
        this.horizontalGridGap = horizontalGridGap;
    }
    /**
     * 
     * @return number of pixels between border and first horizontal grid line
     */
    public int getHorizontalGridOffset() {
        return horizontalGridOffset;
    }
    
    /**
     * Sets number of pixels between border and first horizontal grid line.
     * @param horizontalGridOffset 
     */
    public void setHorizontalGridOffset(int horizontalGridOffset) {
        this.horizontalGridOffset = horizontalGridOffset;
    }
    /**
     * @return number of pixels between vertical grid lines
     */
    public int getVerticalGridGap() {
        return verticalGridGap;
    }
    /**
     * Sets number of pixels between vertical grid lines
     * @param verticalGridGap 
     */
    public void setVerticalGridGap(int verticalGridGap) {
        this.verticalGridGap = verticalGridGap;
    }
    /**
     * 
     * @return number of pixels between border and first vertical grid line
     */
    public int getVerticalGridOffset() {
        return verticalGridOffset;
    }
    /**
     * Sets number of pixels between border and first vertical grid line.
     * @param verticalGridOffset 
     */
    public void setVerticalGridOffset(int verticalGridOffset) {
        this.verticalGridOffset = verticalGridOffset;
    }
    /**
     * 
     * @return array of horizontal labels 
     */
    public String[] getHorizontalLabels() {
        return horizontalLabels;
    }
    /**
     * Sets horizontal labels
     * @param horizontalLabels 
     */
    public void setHorizontalLabels(String[] horizontalLabels) {
        this.horizontalLabels = horizontalLabels;
    }
    /**
     * 
     * @return array of vertical labels 
     */
    public String[] getVerticalLabels() {
        return verticalLabels;
    }
    
    /**
     * Sets vertical labels
     * @param verticalLabels 
     */
    public void setVerticalLabels(String[] verticalLabels) {
        this.verticalLabels = verticalLabels;
    }
    /**
     * 
     * @return vertical label's flag
     */
    public boolean isVerticalLabelsFlag() {
        return verticalLabelsFlag;
    }
    /**
     * Sets vertical label's flag. If <b>true</b> labels are displayed.
     * @param verticalLabelsFlag 
     */
    public void setVerticalLabelsFlag(boolean verticalLabelsFlag) {
        this.verticalLabelsFlag = verticalLabelsFlag;
    }
    /**
     * 
     * @return horizontal label's flag
     */
    public boolean isHorizontalLabelsFlag() {
        return horizontalLabelsFlag;
    }
    /**
     * Sets horizontal label's flag. If <b>true</b> labels are displayed.
     * @param horizontalLabelsFlag 
     */
    public void setHorizontalLabelsFlag(boolean horizontalLabelsFlag) {
        this.horizontalLabelsFlag = horizontalLabelsFlag;
    }
    
    
    
}
