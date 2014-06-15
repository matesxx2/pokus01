/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.base;

import java.util.Calendar;
import java.util.Date;

/**
 * Stores data for graphs
 * @author petr
 */
public class Dataset {   
    private final Date[] dates;
    private final double[]data;
    
    private Date minDate;
    private Date maxDate;
    
    private double minValue = Double.MAX_VALUE;
    private double maxValue = Double.MIN_VALUE;

    public Dataset(Date[] dates, double[] data) {
        this.dates = dates;
        this.data = data;
        setMinAndMaxDate();
        setMinAndMaxValue();
    }
    
    /**
     * Returns array of all x-coordinates 
     * @return 
     */
    public final Date[] getDates() {
        return dates;
    }

    /**
     * Returns array of all y-coordinates
     * @return 
     */
    public final double[] getData() {
        return data;
    }

    public Date getMinDate() {
        return minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }
    
    
    
    private void setMinAndMaxDate(){
        minDate = dates[0];
        maxDate = dates[0];
        for (int i = 1; i < dates.length; i++) {
            if(minDate.after(dates[i]))
                minDate = dates[i];
            else if(maxDate.before(dates[i]))
                maxDate = dates[i];
            
        }
    }
    
    private void setMinAndMaxValue(){
        for (int i = 0; i < data.length; i++) {
            if(data[i] < minValue)
                minValue = data[i];
            else if(data[i] > maxValue)
                maxValue = data[i];
        }
    }
    
    private Calendar convertDateToCalendar(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }
   
}
