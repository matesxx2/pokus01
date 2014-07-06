/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.graphs;

import java.util.ArrayList;
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
public class LineGraph extends Graph{
    
    protected static final String graphName = "Line Graph";
    

    private LineGraph(Dataset dataset, GraphSettings settings) throws Exception {
        super(dataset, settings);
        
        checkDatasetSize();
    }
    
    public static LineGraph create(Dataset dataset, GraphSettings settings) throws Exception{
        return new LineGraph(dataset, settings);
    }
    
    @Override
    public List<Drawable> getDataForDisplay() {
        int values = getDataset().getDates().length;
        int columns = getDataset().getData().length / values;
        
        List<Drawable> result = new ArrayList();
        for(int i=0; i<columns; i++){
           for(int j=i*values; j<(i+1)*values-1; j++){
               result.add(createLine(getDataset().getDates()[j%values], getDataset().getData()[j], getDataset().getDates()[(j%values)+1], getDataset().getData()[j+1]));
           } 
        }
        return result;
    }
    
    private Line createLine(Date d1, double val1, Date d2, double val2){
        int x1 = getPixelFromCoordinate(d1);
        int y1 = getPixelFromCoordinate(val1);
        int x2 = getPixelFromCoordinate(d2);
        int y2 = getPixelFromCoordinate(val2);
        return new Line(x1, y1, x2, y2);
    }
}
