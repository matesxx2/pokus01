/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.base;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import simpledraw.GraphPane;

/**
 * This is a base graph class. It is responsible for
 * computing drawingArea size and their resizing after
 * graphPane's size is changed. This class is also responsible for
 * correct settings of grid and grid labels in graphPane.
 * 
 * @author Martin Kramar
 */
public abstract class GraphBase extends GraphPane{ 
    
    private static final int FORMAT_DATE_FULL = 1;
    private static final int FORMAT_DATE_ONLYTIME = 2;
    
    /**reference to graph which contains {@link Dataset}*/
    private Graph graph;
     
    protected GraphBase(){
        /*we need to set some initial size of drawing area
        correct values are when graph is set*/
        super(1000,1000);
        graph = null;
    }
    
    protected void setGraph(Graph graph) {
        this.graph = graph;
        setHorizontalGridSettings();
        drawAreaResized();//is called to set correct size of drawingArea
    }
    
    /**
     * Sets gap and offset according {@link GraphSettings#horizontalGridGap}
     * and {@link GraphSettings#horizontalGridOffset} and sets labels for 
     * horizontal grid lines
     */
    protected void setHorizontalGridSettings(){
        setHorizontalGridGap(graph.getSettings().getHorizontalGridGap());
        setHorizontalGridOffset(graph.getSettings().getHorizontalGridOffset());
        
        /*//used from constant space between grid lines
        String labels[] = new String[countNumberOfHorizontalGridLines()];
        int pixel = getSpaceNorth() + getHorizontalGridOffset();
        for(int i=0; i<labels.length; i++){
            labels[i] = String.valueOf(getValueFromPixel(pixel));
            pixel += getHorizontalGridGap();
        }
        */
        
        //used for constant count of grid lines
        String labels[] = new String[graph.getSettings().getNumOfDisplayedValues()];
        int pixel = getSpaceNorth() + getHorizontalGridOffset();
        int gridGap = (getBorderRectangle().height-2*getHorizontalGridOffset())/(graph.getSettings().getNumOfDisplayedValues()-1);
        setHorizontalGridGap(gridGap);//newly computed grid gap
        for(int i=0; i<labels.length; i++){
            String tmp = String.valueOf(getValueFromPixel(pixel));
            if(tmp.length() > 6)
                labels[i] = tmp.substring(0, 6);
            else
                labels[i] = tmp;
            pixel += getHorizontalGridGap();
        }
        setHorizontalLabels(labels);
    }
    
    private int countNumberOfHorizontalGridLines(){
        int count = 0;
        int pixel = getSpaceNorth() + getHorizontalGridOffset();
        while(pixel < getBorderRectangle().y+getBorderRectangle().height){
            count++;
            pixel += getHorizontalGridGap();
        }
        return count;
    }
    
    
    /**
     * Computes required width for displaying all data from dataset
     * @return width in pixels<br>-1 if graph is null
     */
    private int getRequiredWidth(){
        if(graph == null)
            return -1;
        
        //num of total units
        int diff = diffDates(graph.getDataset().getMinDate(), graph.getDataset().getMaxDate(), graph.getSettings().getTimeLevel());
        //size of currently visible area
        int graphAreaWidth = getBorderRectangle().width;
        
        //num of displayed unit in visible window
        int numOfDisplayedUnit = graph.getSettings().getNumOfDislplayedUnits();
       
        //num of pixels for displaying all data
        int width = (int)Math.ceil((float)diff/numOfDisplayedUnit*graphAreaWidth);
        
        //add spaces betwenn boundaries of visible area and component
        System.out.println("return of getRequiredWidth():" + (width + getSpaceEast() + getSpaceWest()));
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
    protected long diffDatesInMillis(Date d1, Date d2){
        return createCalendarFromDate(d2).getTimeInMillis() - createCalendarFromDate(d1).getTimeInMillis();
    }
    
    protected Calendar createCalendarFromDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
    
    /**
     * In input calendar sets to 0 all fields in array calendarUnits.
     * @param calendar
     * @param calendarUnits array of {@link Calendar#SECOND}, {@link Calendar#MINUTE}, ...
     */
    private void setToZeroFollowingCalendarUnits(Calendar calendar, int calendarUnits[]){
        for (int field : calendarUnits) {
            calendar.set(field, 0);
        }
    }
    
    /**
     * Returns array of calendar fiels which are lower than timelevel.<br><br>
     * <b>Example:<b/><br>
     * If timeLevel is {@link TimeLevel#HOUR} then following array is returned:
     * {@link Calendar#MILLISECOND}, {@link Calendar#SECOND}, {@link Calendar#MINUTE} 
     * @param timeLevel
     * @return 
     */
    private int[] getCalendarUnits(TimeLevel timeLevel){
        switch(timeLevel){
            case SECOND:
                return new int[]{Calendar.MILLISECOND};
            case MINUTE:
                return new int[]{Calendar.MILLISECOND, Calendar.SECOND};
            case HOUR:
                return new int[]{Calendar.MILLISECOND, Calendar.SECOND, Calendar.MINUTE};
            case DAY:
                return new int[]{Calendar.MILLISECOND, Calendar.SECOND, Calendar.MINUTE, Calendar.HOUR_OF_DAY};
            case WEEK:
                return new int[]{Calendar.MILLISECOND, Calendar.SECOND, Calendar.MINUTE, Calendar.HOUR_OF_DAY, Calendar.DAY_OF_WEEK};
            case MONTH:
                return new int[]{Calendar.MILLISECOND, Calendar.SECOND, Calendar.MINUTE, Calendar.HOUR_OF_DAY, Calendar.DAY_OF_WEEK, Calendar.WEEK_OF_MONTH};
            default:
                return new int[0];
        }
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
        
        /*new grid gap needs to be computed to have 
        same number of vertical grid lines when
        size of visible area is changed*/
        setVerticalGridGap(computeVerticalGridGap());
        
        //sets new labels
        setHorizontalGridSettings();
    }
    
    /**
     * Computes required grid gap in pixels.
     * @return 
     */
    private int computeVerticalGridGap(){
        Date d1 = new Date(System.currentTimeMillis());
        Date d2 = getFirstNextDate(d1, graph.getSettings().getTimeLevel());
        Date d3 = getFirstNextDate(d2, graph.getSettings().getTimeLevel());
        
        return getPixelFromCoordinate(d3)-getPixelFromCoordinate(d2);
    }

    @Override
    protected void visibleAreaMoved() {
        super.visibleAreaMoved();
        /*
        System.out.println("getBorderRectangle:" + getBorderRectangle());
        System.out.println("getDrawAreaBounds:" + getDrawAreaBounds());
        System.out.println("getDrawVisibleArea:" + getDrawVisibleArea());
        System.out.println("getScrollVisibleArea:" + getScrollVisibleArea());
        System.out.println("getXcoordinateWestBoundaryAtDrawArea:" + getXcoordinateWestBoundaryAtDrawArea());
        System.out.println("getPixelFromCoordinate(getDateFromPixel(150)):" + getPixelFromCoordinate(getDateFromPixel(150)));
        System.out.println("getPixelFromCoordinate(getValueFromPixel(200)):" + getPixelFromCoordinate(getValueFromPixel(200)));
        //*/
          
        /*Date at west boundary of visible area
        it means first displayed date*/
        Date dateAtWestBorder = getDateFromPixel(getXcoordinateWestBoundaryAtDrawArea());
        
        /*
        Remove one millisecond...
        If dateAtWestBorder will be 2014-02-22 11:22:00 and timelevel is minute then
        calling of getFirstNextDate() returns 2014-02-22 11:23:00 but we want to start
        at 2014-02-22 11:22:00 in such case
        */
        dateAtWestBorder.setTime(dateAtWestBorder.getTime()-1);

        /*
        If dateAtWestBorder will be 2014-02-22 11:22:34 and
        a) timelevel is a MINUTE then first date inside border is 2014-02-22 11:23:00
        b) timelevel is a HOUR   then first date inside border is 2014-02-22 12:00:00
        and so on...
        */
        Date dateInsideBorder = getFirstNextDate(dateAtWestBorder, graph.getSettings().getTimeLevel());
        
        int offset = getDistanceFromWestBoundary(getPixelFromCoordinate(dateInsideBorder));
        
        //Create all displayed labels
        String labels[] = new String[graph.getSettings().getNumOfDislplayedUnits()];
        for (int i=0; i<labels.length; i++) {
            if(i==0)
                labels[i] = formatDate(dateInsideBorder, FORMAT_DATE_FULL);
            else
                labels[i] = formatDate(dateInsideBorder, FORMAT_DATE_FULL);
            
            dateInsideBorder = getFirstNextDate(dateInsideBorder, graph.getSettings().getTimeLevel());
        }
        
        setVerticalGridOffset(offset);
        setVerticalLabels(labels);
    }
    
    /**
     * Returns first next date after input date.<br><br>
     * <b>Example:</b><br>
     * If date will be 2014-02-22 11:22:34 and<br>
     * a) timelevel is a {@link TimeLevel#MINUTE} then next date will be 2014-02-22 11:23:00<br>
     * b) timelevel is a {@link TimeLevel#HOUR} then next date will be 2014-02-22 12:00:00<br>
     * and so on...
     * @param date
     * @param timelevel
     * @return Warning: returns 2014-02-22 11:23:00 if input date 2014-02-22 11:22:00
     * and timelevel is minute
     */
    private Date getFirstNextDate(Date date, TimeLevel timeLevel){
        Calendar cDate = createCalendarFromDate(date);
        setToZeroFollowingCalendarUnits(cDate, getCalendarUnits(timeLevel));
        cDate.add(convertTimeLevelToCalendar(timeLevel), 1);
        return cDate.getTime();
    }
    
    /**
     * Returns string representing input date
     * @param date
     * @param format {@link GraphBase#FORMAT_DATE_ONLYTIME} or {@link GraphBase#FORMAT_DATE_FULL}
     * @return 
     * if format {@link GraphBase#FORMAT_DATE_ONLYTIME} returns hh:mm:ss <br>
     * if format {@link GraphBase#FORMAT_DATE_FULL} returns hh:mm:ss yyyy-mm-dd <br>
     * if other format return null
     */
    private String formatDate(Date date, int format){
        if(FORMAT_DATE_FULL == format)
            return new SimpleDateFormat("HH:mm:ss").format(date) + "\n" + new SimpleDateFormat("yyyy-MM-dd").format(date);
        else if(FORMAT_DATE_ONLYTIME == format)
            return new SimpleDateFormat("HH:mm:ss").format(date);
        else
            return null;
    }
    
    
    
    /**
     * Returns x- coordinate position for input date.
     * @param date point at x-coordinate (x in time)
     * @return x coordinate in pixels
     */
    protected int getPixelFromCoordinate(Date date){
        int min = getSpaceEast();
        int max = getDrawAreaBounds().width - getSpaceWest();
        long lengthTotal = diffDatesInMillis(graph.getDataset().getMinDate(), graph.getDataset().getMaxDate());
        long lengthActual = diffDatesInMillis(graph.getDataset().getMinDate(),date);
        double rate = (double)lengthActual/lengthTotal;
        int position = (int)Math.round(rate*(max-min));
        return min + position;
    }
    
    /**
     * Returns {@link Date} which is corresponds with
     * pixel at draw area.<br>
     * See inverse method {@link GraphBase#getPixelFromCoordinate(java.util.Date)}
     * @param pixel x-coordinate at draw area
     * @return 
     */
    protected Date getDateFromPixel(int pixel){
        int min = getSpaceEast();
        int max = getDrawAreaBounds().width - getSpaceWest();
        double ratio = (double)(pixel-min)/(max-min);
        
        long lengthTotal = diffDatesInMillis(graph.getDataset().getMinDate(), graph.getDataset().getMaxDate());
        return new Date(graph.getDataset().getMinDate().getTime()+Math.round(ratio*lengthTotal));
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
        //return max-position //small values are on top
        return min + position;//now small values are at bottom
    }
    
    /**
     * Return value which corresponds with input y-pixel.
     * See inverse method {@link GraphBase#getPixelFromCoordinate(java.lang.Double) }
     * @param pixel at y-axis of drawing area
     * @return 
     */
    protected double getValueFromPixel(int pixel){
        int min = getSpaceNorth();
        int max = getDrawAreaBounds().height - getSpaceSouth();
        double ratio = (double)(pixel-min)/(max-min);
        double lengthTotal = graph.getDataset().getMaxValue()-graph.getDataset().getMinValue();
        //return graph.getDataset().getMinValue() + ratio*lengthTotal;//small values are displayed on top
        return graph.getDataset().getMaxValue() - ratio*lengthTotal;//small values are at bottom
    }
    
    /**
     * Returns number of pixels between position and west boundary
     * of border rectangle measured in drawArea coordination system.
     * <br>
     * Border rectangle is transformed into drawArea coordinate system
     * and difference between x-coordinates of position and west boundary
     * is returned
     * @param position x-coordinate at drawArea in pixels 
     * @return if result > 0 then position is on the right side of west boundary<br>
     * if result < 0 then position is on the left side of west boundary <br>
     * if result == 0 then position is on the west boundary
     */
    protected int getDistanceFromWestBoundary(int position){
        return position-getXcoordinateWestBoundaryAtDrawArea();
    }
    
    /**
     * @return x-coordinate of west boundary of border rectangle at drawArea (in pixels)
     */
    protected int getXcoordinateWestBoundaryAtDrawArea(){
        return getDrawVisibleArea().x + getSpaceWest();
    }

    
    
}
