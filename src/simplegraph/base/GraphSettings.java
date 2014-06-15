/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.base;

/**
 * 
 * 
 * @author petr
 */
public class GraphSettings {
    
    
    
    private static final int NUM_DISP_TIMES_DEFAULT = 10;
    
    private TimeLevel timeLevel = TimeLevel.DAY;
    private int numOfDislplayedUnits = NUM_DISP_TIMES_DEFAULT;
    
    public GraphSettings(){
        
    }

    public TimeLevel getTimeLevel() {
        return timeLevel;
    }

    public void setTimeLevel(TimeLevel timeLevel) {
        this.timeLevel = timeLevel;
    }

    public int getNumOfDislplayedUnits() {
        return numOfDislplayedUnits;
    }

    public void setNumOfDislplayedUnits(int numOfDislplayedTimes) {
        this.numOfDislplayedUnits = numOfDislplayedTimes;
    }
    
    
}
