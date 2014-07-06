/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import simpledraw.drawable.Drawable;
import simpledraw.drawable.Line;
import simplegraph.base.Dataset;
import simplegraph.base.Graph;
import simplegraph.base.GraphSettings;

/**
 *
 * @author Martin
 */
public class BarGraph extends Graph{
    public static final String graphName = "Bar Graph";
    public static final int DEFAULT_GAP_BETWEEN_BARS = 12;
    
    private int gapSameTimeBars = DEFAULT_GAP_BETWEEN_BARS;
    private int gapDifferentTimeBars = DEFAULT_GAP_BETWEEN_BARS / 2;
    
    /**
     * Multi(can have more than one bar per time slot) graph bar.
     * @param dataset
     * @param settings
     * @param gap space(in pixels) between 2 bars from different time slot
     * @param gap2 space(in pixels between 2 bars from same time slot
     * @throws Exception if count of y-values is not an intergral multiplication of count x-values(dates) 
     */
    private BarGraph(Dataset dataset, GraphSettings settings, int gap, int gap2) throws Exception{
        super(dataset, settings);
        this.gapSameTimeBars = gap2;
        this.gapDifferentTimeBars = gap;
        
        //to see whole bars...
        if(dataset.getMinValue() > 0)
            dataset.setMinValueHardcoded(0);
        
        if(dataset.getMaxValue() < 0)
            dataset.setMaxValueHardcoded(0);
         
        checkDatasetSize();
    } 
    
    /**
     * Multi(can have more than one bar per time slot) graph bar.
     * @param dataset
     * @param settings
     * @param gap space(in pixels) between 2 bars from different time slot,
     * space between bars from the same time slot is set to 0.5*gap
     * @throws Exception 
     */
    private BarGraph(Dataset dataset, GraphSettings settings, int gap) throws Exception{
        this(dataset, settings, gap, (int)Math.round(0.5*gap));
    }
    
    /**
     * Creates multi bar graph. This graph can displayed more bars in one time slot.
     * @param dataset
     * @param settings
     * @return
     * @throws Exception 
     */
    public static BarGraph createBarGraph(Dataset dataset, GraphSettings settings) throws Exception{
        return createBarGraph(dataset, settings, DEFAULT_GAP_BETWEEN_BARS);
    }
    /**
     * 
     * @param dataset
     * @param settings
     * @param gapBetweenBars space in pixels between bars from different time slots
     * @return
     * @throws Exception 
     */
    public static BarGraph createBarGraph(Dataset dataset, GraphSettings settings, int gapBetweenBars) throws Exception{
        return new BarGraph(dataset, settings, gapBetweenBars);
    }
    
    /**
     * 
     * @param dataset
     * @param settings
     * @param gapBetweenBarsDifferent space in pixels between bars from different time slots
     * @param gapBetweenBarsSame space in pixels between bars from the same time slot
     * @return
     * @throws Exception 
     */
    public static BarGraph createBarGraph(Dataset dataset, GraphSettings settings, int gapBetweenBarsDifferent, int gapBetweenBarsSame) throws Exception{
        return new BarGraph(dataset, settings, gapBetweenBarsDifferent, gapBetweenBarsSame);
    }

    @Override
    public List<Drawable> getDataForDisplay() {
        long minimalDistanceBetweenDates = getMimimalDistanceBetweenDates();
        Date d1 = getDataset().getDates()[0];
        Date d2 = new Date(d1.getTime() + minimalDistanceBetweenDates);
        int pixel1 = getPixelFromCoordinate(d1);
        int pixel2 = getPixelFromCoordinate(d2);
        int totalBarsWidth = pixel2 - pixel1 - gapDifferentTimeBars;
        int numOfColumns = getDataset().getData().length / getDataset().getDates().length;
        double bars[] = new double[numOfColumns];
        List<Drawable> ret = new ArrayList<>();
        
        for(int i=0; i<getDataset().getDates().length; i++){
            for(int j=0; j<bars.length; j++)
                bars[j] = getDataset().getData()[j*getDataset().getDates().length+i];
            
            System.out.println("bars:" + Arrays.toString(bars));
            addBarsToDrawableList(getDataset().getDates()[i], bars, totalBarsWidth, ret);
        }
        
        
        
        return ret;
    }
    
    private void addBarsToDrawableList(Date d, double bars[], int barsTotalWidth, List<Drawable> drawables){
        int pixelCenterPosition = getPixelFromCoordinate(d);
        int barWidth = (int)Math.round((barsTotalWidth-(bars.length-1)*gapSameTimeBars)/bars.length);
        for(int i=0; i<bars.length; i++){
            int pixelX = pixelCenterPosition - (int)Math.round(0.5*barsTotalWidth) + 
                    (i*barWidth) + i*gapSameTimeBars + (int)Math.round(0.5*barWidth);
            addBarToDrawableList(pixelX, barWidth, bars[i], drawables);
        }
    }
    
    private void addBarToDrawableList(int pixelX, int barWidth, double barValue, List<Drawable> drawables){
        int x1 = pixelX - (int)Math.round(0.5*barWidth);
        int y1 = getPixelFromCoordinate(0.0);
        
        int x2 = x1;
        int y2 = getPixelFromCoordinate(barValue);
        
        int x3 = x1 + barWidth;
        int y3 = y2;
        
        int x4 = x3;
        int y4 = y1;
        
        drawables.add(new Line(x1,y1,x2,y2));
        drawables.add(new Line(x2,y2,x3,y3));
        drawables.add(new Line(x3,y3,x4,y4));
    }
     
    
    
}
