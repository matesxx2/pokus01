/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.base;

/**
 * 
 * 
 * @author Martin Kramar
 */
public class GraphSettings {
     
    private static final int NUM_DISP_TIMES_DEFAULT = 10;
    private static final int NUM_DISP_VALUES_DEFAULT = 5;
    private static final int OFFSET_DEFAULT = 30;
    private static final int GAP_DEFAULT = 80;
    
    private TimeLevel timeLevel = TimeLevel.DAY;
    private int numOfDislplayedUnits = NUM_DISP_TIMES_DEFAULT;
    private int numOfDisplayedValues = NUM_DISP_VALUES_DEFAULT;
    
    private int horizontalGridOffset = OFFSET_DEFAULT;
    private int horizontalGridGap = GAP_DEFAULT;
    
    public GraphSettings(){
        
    }
    /**
     * Timelevel sets units of x-axis
     * @return 
     */
    public TimeLevel getTimeLevel() {
        return timeLevel;
    }
    /**
     * Timelevel sets units of x-axis 
     * @param timeLevel 
     */
    public void setTimeLevel(TimeLevel timeLevel) {
        this.timeLevel = timeLevel;
    }
    
    /**
     * Returns number vertical grid lines
     * @return 
     */
    public int getNumOfDislplayedUnits() {
        return numOfDislplayedUnits;
    }
    
    /**
     * Sets number of vertical grid lines
     * @param numOfDislplayedTimes 
     */
    public void setNumOfDislplayedUnits(int numOfDislplayedTimes) {
        this.numOfDislplayedUnits = numOfDislplayedTimes;
    }
    /**
     * 
     * @return space (in pixels) between north border of graphArea and
     * first horizontal grid line
     */
    public int getHorizontalGridOffset() {
        return horizontalGridOffset;
    }
    /**
     * Sets space (in pixels) between north border of graphArea and
     * first horizontal grid line.
     * @param horizontalGridOffset in pixels
     */
    public void setHorizontalGridOffset(int horizontalGridOffset) {
        this.horizontalGridOffset = horizontalGridOffset;
    }
    /**
     * 
     * @return size (in pixels) between horizontal grid lines
     */
    public int getHorizontalGridGap() {
        return horizontalGridGap;
    }
    
    /**
     * Sets size (in pixels) between horizontal grid lines
     * @param horizontalGridGap in pixels
     */
    public void setHorizontalGridGap(int horizontalGridGap) {
        this.horizontalGridGap = horizontalGridGap;
    }
    
    /**
     * 
     * @return count of horizontal grid lines
     */
    public int getNumOfDisplayedValues() {
        return numOfDisplayedValues;
    }
    /**
     * Sets count of displayed horizontal grid lines
     * @param numOfDisplayedValues count of horizontal grid lines
     */
    public void setNumOfDisplayedValues(int numOfDisplayedValues) {
        this.numOfDisplayedValues = numOfDisplayedValues;
    }

   
    
    
    
}
