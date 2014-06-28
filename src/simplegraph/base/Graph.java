/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.base;

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
