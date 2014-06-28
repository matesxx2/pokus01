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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class contains drawing area (JPanel with scrollbars if drawing ares is 
 * bigger then displayed area) and container with {@link Drawable} objects 
 * which are painted in the drawing area.
 * @author Martin Kramar
 */
public class DrawArea implements ComponentListener, ChangeListener{

    /**
     * Base panel where each {@link Drawable} object is painted.
     */
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
            
        } 
        
        @Override
        public Dimension getPreferredSize(){
            return new Dimension(width, height);
        }
        
    }
    
    /**list of object to be painted*/
    private List<Drawable> listOfDrawables;
    
    /** Visible draw area, if drawingPanel is bigger then visible area
     then scrollbars are shown*/
    private final JScrollPane scrollPane;
    
    /**Base panel for drawing {@link Drawable} objects*/
    private final DrawingPanel drawingPanel;
    
    /**
     * Creates drawing area with specified size.
     * When drawing drawing will be bigger then actual visible area then
     * scrollbars will be shown.
     * @param width in pixels
     * @param height in pixels
     */
    public DrawArea(int width, int height){
        listOfDrawables = new ArrayList();
        drawingPanel = new DrawingPanel(width, height);
        
        /*adding scrollbars for drawing area*/
        scrollPane = new JScrollPane(drawingPanel){ 
            @Override
            public void paint(Graphics g) {
                super.paint(g); //To change body of generated methods, choose Tools | Templates.
                paintAtScrollPane(g);//method for aditional painting on scrollPane
                repaint();
            }
        };
        
        /*draw area is in "background" and can not be changed
         all changes are cathed in scroll pane*/
        scrollPane.addComponentListener(this);
        scrollPane.getViewport().addChangeListener(this);
    }
    
    /**
     * Resizes based drawing area (area where all objects are painted).
     * @param width in pixels
     * @param height in pixels
     */
    public void resizeDrawingArea(int width, int height){
        drawingPanel.width = width;
        drawingPanel.height = height;
        drawingPanel.setSize(width, height);
    }
    
    /**
     * @return panel with scrollbars where visible
     * part of drawing area
     */
    public final JScrollPane getDrawingArea(){
        return scrollPane;
    }
    
    /**
     * Sets size of drawing panel.
     * @param width in pixels
     * @param height in pixels
     */
    public void setAreaSize(int width, int height){
        drawingPanel.width = width;
        drawingPanel.height = height;
    }
    
    /**
     * Adds {@link Drawable} object into container
     * @param drawable
     * @return true if success
     */
    public boolean addDrawable(Drawable drawable){
        return listOfDrawables.add(drawable);
    }
    
    /**
     * Sets new list of {@link Drawable} objects
     * @param drawables
     */
    public void setListOfDrawables(List<Drawable> drawables){
        listOfDrawables = drawables;
    }
    
    /**
     * Removes {@link Drawable} object from container.
     * @param drawable - removed element
     * @return true if container contains removed element
     */
    public boolean removeDrawable(Drawable drawable){
        return listOfDrawables.remove(drawable);
    }
    
    /**
     * Removes {@link Drawable} object from container at position i
     * @param i - index of removed element
     * @return removed element
     */
    public Drawable removeDrawable(int i){
        return listOfDrawables.remove(i);
    }
    
    /**
     * Returns place and size of displayed components with scroll bars.
     * Place is position of component at screen and size is size of
     * displayed component.
     * @return 
     */
    public Rectangle getScrollVisibleArea(){
        return scrollPane.getVisibleRect();
    }
    
    /**
     * Return place and size of visible part of drawing area.
     * Place is position of visible part of drawing area and size is size of 
     * drawing area.
     * @return 
     */
    public Rectangle getDrawVisibleArea(){
        return drawingPanel.getVisibleRect();
    }
    
    /**
     * Return size of whole drawing area
     * @return 
     */
    public Rectangle getDrawAreaBounds(){
        return drawingPanel.getBounds();
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
     * Paints at scrollPane.
     * This can be used for some additional drawing at scrollPane
     * @param g 
     */
    protected void paintAtScrollPane(Graphics g){
       
    }
    
    /**
     * If scrollPane is resized you can react here...
     */
    protected void drawAreaResized(){
    }
    
    /**
     * If visible area at scrollPane is moved by scrollbars
     * you can react here...
     */
    protected void visibleAreaMoved(){
        
    }
    
    @Override
    public void componentResized(ComponentEvent e) {
        drawAreaResized();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentShown(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        visibleAreaMoved();
    }
            
}
