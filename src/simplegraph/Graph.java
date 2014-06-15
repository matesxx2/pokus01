/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph;

import java.util.Date;

/**
 * Stores and implements all common variables and methods for all graphs
 * @author petr
 */
public abstract class Graph {
    
    private Dataset dataset;
    private GraphSettings settings;

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
    
    /**
     * Returns the first date from dataset
     * @return the most historic date
     */
    public Date getFirstDate(){
        return dataset.dates[0];
    }
    
    /**
     * Returns the last date from dataset
     * @return at least historic date
     */
    public Date getLastDate(){
        return dataset.dates[dataset.dates.length-1];
    }

    public GraphSettings getSettings() {
        return settings;
    }

    public void setSettings(GraphSettings settings) {
        this.settings = settings;
    }
    
    
}
