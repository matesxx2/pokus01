/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simpledraw.drawable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * This class creates drawable area(with scroll bars if needed)
 * and stores {@link Drawable} objects which are painted at created area.
 * @author kramarm
 */
public class DrawArea{
    /*tuned JPanel (drawing area)*/
    final class DrawingPanel extends JPanel{
        public int width;
        public int height;
        
        public DrawingPanel(int width, int height){
            super();
            this.width = width;
            this.height = height;
            setBackground(Color.white);
            setSize(getPreferredSize());
            
        }
       
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); 
            paintDrawables(g);
            System.out.println("DA");
            
        } 
        
        @Override
        public Dimension getPreferredSize(){
            return new Dimension(width, height);
        }
        
    }
    
    /*list of object to be painted*/
    private List<Drawable> listOfDrawables;
    
    /*draw area*/
    private final JScrollPane scrollPane;
    private final DrawingPanel drawingPanel;
    
    /**
     * Creates drawing area with specified size
     * @param width in pixels
     * @param height in pixels
     */
    public DrawArea(int width, int height){
        listOfDrawables = new ArrayList();
        drawingPanel = new DrawingPanel(width, height);
        scrollPane = new JScrollPane(drawingPanel){ 
            @Override
            public void paint(Graphics g) {
                super.paint(g); //To change body of generated methods, choose Tools | Templates.
                paintAtScrollPane(g);
                repaint();
            }
        };    
    }  
    
    public final JScrollPane getDrawingArea(){
        return scrollPane;
    }
    
    public void setAreaSize(int width, int height){
        drawingPanel.width = width;
        drawingPanel.height = height;
    }
    
    public boolean addDrawable(Drawable drawable){
        return listOfDrawables.add(drawable);
    }
    
    public void setListOfDrawables(List<Drawable> drawables){
        listOfDrawables = drawables;
    }
    
    public boolean removeDrawable(Drawable drawable){
        return listOfDrawables.remove(drawable);
    }
    
    public Drawable removeDrawable(int i){
        return listOfDrawables.remove(i);
    }
    
    /**
     * Returns place and size of displayed components with scroll bars
     * @return 
     */
    public Rectangle getScrollVisibleArea(){
        return scrollPane.getVisibleRect();
    }
    
    /**
     * Return place and size of visible part of components without scrol bars
     * @return 
     */
    public Rectangle getDrawVisibleArea(){
        return drawingPanel.getVisibleRect();
    }
    
    /**
     * It paints all {@link Drawable} objects
     * @param g 
     */
    protected void paintDrawables(Graphics g){
        Iterator<Drawable> iterator = listOfDrawables.listIterator();
        while(iterator.hasNext())
            iterator.next().draw(g);
    }
    
    /**
     * Paints at scrollPane
     * 
     * @param g 
     */
    protected void paintAtScrollPane(Graphics g){
       
    }
            
}
