/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.base;

import java.util.Calendar;
import java.util.Date;
import simpledraw.GraphPane;

/**
 * Paints grid, and grid's labels
 * @author Martin
 */
public abstract class GraphBase extends GraphPane{
    
    private Graph graph;
    
    protected GraphBase(){
        super(1,2);
        graph = null;
    }

    protected void setGraph(Graph graph) {
        this.graph = graph;
    }
      
    
    /**
     * Computes width for displaying all data from dataset
     * @return width in pixels
     */
    private int getRequiredWidth(){
        //num of total units
        int diff = diffDates(graph.getDataset().getMinDate(), graph.getDataset().getMaxDate(), graph.getSettings().getTimeLevel());
        //size of currently visible area
        int graphAreaWidth = getBorderRectangle().width;
        
        //num of displayed unit in visible window
        int numOfDisplayedUnit = graph.getSettings().getNumOfDislplayedUnits();
       
        //num of pixels for displaying all data
        int width = (int)Math.ceil((float)diff/numOfDisplayedUnit*graphAreaWidth);
        
        //add spaces betwenn boundaries of visible area and component
        return width + getSpaceEast() + getSpaceWest();
    }
    
    /**
     * Returns d2-d1 measured in timeLevel
     * @param d1
     * @param d2
     * @param timeLevel sec,min,hour,...
     * @return result is ceiling
     */
    private int diffDates(Date d1, Date d2, TimeLevel timeLevel){
        Calendar c1 = createCalendarFromDate(d1);
        Calendar c2 = createCalendarFromDate(d2);
        int diff = -1;
        while(c1.before(c2)){
            diff++;
            c1.add(convertTimeLevelToCalendar(timeLevel), 1);
        }
        return ++diff;
    }
    
    /**
     * Returns d2-d1 in milliseconds
     * @param d1
     * @param d2
     * @return 
     */
    private long diffDatesInMillis(Date d1, Date d2){
        return createCalendarFromDate(d2).getTimeInMillis() - createCalendarFromDate(d1).getTimeInMillis();
    }
    
    private Calendar createCalendarFromDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
    
    private int convertTimeLevelToCalendar(TimeLevel timeLevel){
        switch(timeLevel){
            case SECOND:
                return Calendar.SECOND;
            case MINUTE:
                return Calendar.MINUTE;
            case HOUR:
                return Calendar.HOUR_OF_DAY;
            case DAY:
                return Calendar.DAY_OF_WEEK;
            case WEEK:
                return Calendar.WEEK_OF_MONTH;
            case MONTH:
                return Calendar.MONTH;
            default:
                return 0;          
        }
            
    }

    @Override
    protected void drawAreaResized() {
        super.drawAreaResized();
        
        /* visible area was resized ->
         * whole drawing area is rescaled*/
        resizeDrawingArea(getRequiredWidth(), getDrawVisibleArea().height);
        
        //and points a rescaled also
        setListOfDrawables(graph.getDataForDisplay());  
    }
    
    /**
     * Returns x- coordinate position for input date.
     * @param date point at x-coordinate (x in time)
     * @return x coordinate in pixels
     */
    protected int getPixelFromCoordinate(Date date){
        int min = getSpaceEast();
        int max = getDrawAreaBounds().width - getSpaceWest();
        long lengthTotal = diffDatesInMillis(graph.getDataset().getMaxDate(), graph.getDataset().getMinDate());
        long lengthActual = diffDatesInMillis(graph.getDataset().getMaxDate(), date);
        double rate = lengthActual/lengthTotal;
        int position = (int)Math.round(rate*(max-min));
        return max - position;
    }
    
    /**
     * Returns y- coordinate position for input value
     * @param value point at y-coordinate (y in double)
     * @return x coordinate in pixels
     */
    protected int getPixelFromCoordinate(Double value){
        int min = getSpaceNorth();
        int max = getDrawAreaBounds().height - getSpaceSouth();
        double lengthTotal = graph.getDataset().getMaxValue()-graph.getDataset().getMinValue();
        double lengthActual = graph.getDataset().getMaxValue()-value;
        double rate = lengthActual/lengthTotal;
        int position = (int)Math.round(rate*(max-min));
        return max - position;
    }
    
    
    
}
