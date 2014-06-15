/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.base;

import java.util.List;
import simpledraw.drawable.Drawable;

/**
 * Stores and implements all common variables and methods for all graphs
 * @author petr
 */
public abstract class Graph extends GraphBase{
    
    private Dataset dataset;
    private GraphSettings settings;
    
    public Graph(Dataset dataset, GraphSettings settings){
        super();
        setGraph(this);
        this.dataset = dataset;
        this.settings = settings;
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
     * Converts data from dataset to {@link Drawable} object
     * @return 
     */
    public abstract List<Drawable> getDataForDisplay();
    
}
