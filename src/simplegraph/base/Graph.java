/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.base;

import java.util.Date;
import java.util.List;
import simpledraw.drawable.Drawable;

/**
 * Implements based functionality which is same for all types of graphs.
 * It is storing of {@link Dataset} and {@link GraphSettings}
 * 
 * @author Martin Kramar
 */
public abstract class Graph extends GraphBase{
    
    private Dataset dataset;
    private GraphSettings settings;
      
    public Graph(Dataset dataset, GraphSettings settings){
        super();
        
        this.dataset = dataset;
        this.settings = settings;
        setGraph(this);
    }
    
    
    /**
     * Check if data size is an an integral multiplication of date size.
     * If not throws exception
     * 
     * @throws java.lang.Exception
     */
    protected void checkDatasetSize() throws Exception{
        int countDate = dataset.getDates().length;
        int countValues = dataset.getData().length;
        if(countValues % countDate != 0)
            throw new Exception("Wrong data size: " + countValues + 
                    "data size has to be an integral multiplication of date size: " + countDate);
    
    }
    
    /**
     * Goes through dataset and find two most closest date and return
     * their difference in millisecods
     * @return 
     */
    protected long getMimimalDistanceBetweenDates(){
        Date first;
        Date second;
        long res = Long.MAX_VALUE;
        long dist;
        
        for(int i=0; i<dataset.getDates().length-1; i++){
            first = dataset.getDates()[i];
            second = dataset.getDates()[i+1];
            dist = diffDatesInMillis(first, second);
            if(dist < res)
                res = dist;
        }
        return res;
    }
    
    
    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public GraphSettings getSettings() {
        return settings;
    }

    public void setSettings(GraphSettings settings) {
        this.settings = settings;
    }
    
    /**
     * Converts data from dataset to list of {@link Drawable} objects
     * @return 
     */
    public abstract List<Drawable> getDataForDisplay();

}
