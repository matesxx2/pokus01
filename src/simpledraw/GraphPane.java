/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simpledraw;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author kramarm
 */
public class GraphPane extends JScrollPane{
    
    final class DrawingPanel extends JPanel{
        private final int width;
        private final int height;
        
        public DrawingPanel(int width, int height){
            super();
            setSize(getPreferredSize());
            this.width = width;
            this.height = height;
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
    
    private final List<Drawable> listOfDrawables;
    
    public GraphPane(int width, int height){
        super();
        add(new DrawingPanel(width, height));
        listOfDrawables = new ArrayList();
    }
    
    public boolean addDrawable(Drawable drawable){
        return listOfDrawables.add(drawable);
    }
    
    private void paintDrawables(Graphics g){
        Iterator<Drawable> iterator = listOfDrawables.listIterator();
        while(iterator.hasNext())
            iterator.next().draw(g);
    }
}
