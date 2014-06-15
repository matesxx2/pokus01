/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph;

import java.util.Calendar;
import java.util.Date;
import simpledraw.GraphPane;

/**
 * Paints grid, and grid's labels
 * @author Martin
 */
public abstract class GraphBase extends GraphPane{
    
    private final Graph graph;
    
    protected GraphBase(Graph graph){
        super(1,1);
        this.graph = graph;
        
    }
    
    /**
     * Computes width for displaying all data from dataset
     * @return width in pixels
     */
    private int getRequiredWidth(){
        Date min = null;
        Date max = null;
        Calendar cmin = Calendar.getInstance();
        cmin.setTime(min);
        Calendar cmax = Calendar.getInstance();
        cmax.setTime(max);
        
        int diff = diffDates(min, max, TimeLevel.SECOND);
        
        
       
        
        
        
        return -1;
    }
    
    private int diffDates(Date d1, Date d2, TimeLevel timeLevel){
        return -1;
    }

    @Override
    protected void drawAreaResized() {
        super.drawAreaResized();
        
        /* draw area was resized ->
         * x,y cordinates has to be rescaled*/
        
    }
    
    
    
}
