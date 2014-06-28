/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simpledraw.drawable;

import java.awt.Graphics;

/**
 * This class represents drawable point with coordinates x,y.
 * This class stores mark(picture painted to canvas) and size of mark.
 * @author kramarm
 */
public class Point implements Drawable{
    
    public int x;
    public int y;
    private int mark;
    private int markSize;
    
    public static final int MARK_NOMARK = 0;
    public static final int MARK_CROSS = 1;
    public static final int MARK_PLUS = 2;
    
    private static final int SIZE_OF_MARK_DEFAULT = 5;
    private static final int MARK_DEFAULT = MARK_CROSS;

    /**
     * Creates new object
     * @param x
     * @param y
     * @param mark
     * {@link Point#MARK_CROSS}
     * {@link Point#MARK_PLUS}
     * {@link Point#MARK_NOMARK}
     * @param markSize in pixels
     */
    private Point(int x, int y, int mark, int markSize) {
        this.x = x;
        this.y = y;
        this.mark = mark;
        this.markSize = markSize;
    }
    
    /**
     * Sets size of mark which will be painted.
     * @param size in pixels
     */
    public void setMarkSize(int size){
        this.markSize = size;
    }
    
    /**
     * Sets type of mark
     * @param mark options: 
     * {@link Point#MARK_CROSS}
     * {@link Point#MARK_PLUS}
     * {@link Point#MARK_NOMARK}
     */
    public void setMark(int mark){
        this.mark = mark;
    }
    
    


    @Override
    public void draw(Graphics g) {
        if(mark == MARK_NOMARK){
            //no action..point is not displayed
        }else if(mark == MARK_CROSS){
            paintCrossMark(g);
        }else if(mark == MARK_PLUS){
            paintPlusMark(g);
        }
    }
    
    private void paintCrossMark(Graphics g){
        int x1 = x - (int)Math.round(0.5*markSize);
        int y1 = y - (int)Math.round(0.5*markSize);
        int x2 = x + (int)Math.round(0.5*markSize);
        int y2 = y + (int)Math.round(0.5*markSize);
        g.drawLine(x1, y1, x2, y2);
        
        x1 = x + (int)Math.round(0.5*markSize);
        y1 = y - (int)Math.round(0.5*markSize);
        x2 = x - (int)Math.round(0.5*markSize);
        y2 = y + (int)Math.round(0.5*markSize);
        g.drawLine(x1, y1, x2, y2);
    }
    
    private void paintPlusMark(Graphics g){
        int x1 = x;
        int y1 = y - (int)Math.round(0.5*markSize);
        int x2 = x;
        int y2 = y + (int)Math.round(0.5*markSize);
        g.drawLine(x1, y1, x2, y2);
        
        x1 = x + (int)Math.round(0.5*markSize);
        y1 = y;
        x2 = x - (int)Math.round(0.5*markSize);
        y2 = y;
        g.drawLine(x1, y1, x2, y2);
    }
    
    /**
     * Creates new point with default mark(cross) and size(5).
     * @param x
     * @param y
     * @return 
     */
    public static Point createPoint(int x, int y){
        return new Point(x, y, MARK_DEFAULT, SIZE_OF_MARK_DEFAULT);
    }
    
    /**
     * Creates new point with default mark(cross) and specified size.
     * @param x
     * @param y
     * @param markSize in pixels
     * @return 
     */
    public static Point createPoint(int x, int y, int markSize){
        return new Point(x, y, MARK_DEFAULT, markSize);
    }
    
    /**
     * Creates Point without mark.
     * It means this object will not be painted
     * to canvas.
     * @param x
     * @param y
     * @return 
     */
    public static Point createPointNoMark(int x, int y){
        return new Point(x, y, MARK_NOMARK, SIZE_OF_MARK_DEFAULT);
    }
    
    /**
     * Creates Point with 'cross' mark.
     * It means this object will be painted similar to 'X'
     * to canvas.
     * @param x
     * @param y
     * @return 
     */
    public static Point createPointCrossMark(int x, int y){
        return new Point(x, y, MARK_CROSS, SIZE_OF_MARK_DEFAULT);
    }
    
    /**
     * Creates Point with 'cross' mark.
     * It means this object will be painted similar to 'X'
     * to canvas.
     * @param x
     * @param y
     * @param markSize in pixels
     * @return 
     */
    public static Point createPointCrossMark(int x, int y, int markSize){
        return new Point(x, y, MARK_CROSS, markSize);
    }
    
    /**
     * Creates Point with 'plus' mark.
     * It means this object will be painted similar to '+'
     * to canvas.
     * @param x
     * @param y
     * @return 
     */
    public static Point createPointPlusMark(int x, int y){
        return new Point(x, y, MARK_PLUS, SIZE_OF_MARK_DEFAULT);
    }
    
    /**
     * Creates Point with 'plus' mark.
     * It means this object will be painted similar to '+'
     * to canvas.
     * @param x
     * @param y
     * @param markSize in pixels
     * @return 
     */
    public static Point createPointPlusMark(int x, int y, int markSize){
        return new Point(x, y, MARK_PLUS, markSize);
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + ", mark=" + mark + ", markSize=" + markSize + '}';
    }
    
    
}
