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
import simpledraw.drawable.Point;
import simplegraph.base.Dataset;
import simplegraph.base.Graph;
import simplegraph.base.GraphSettings;

/**
 *
 * @author petr
 */
public class TestGraph extends Graph{

    private TestGraph(Dataset dataset, GraphSettings settings) {
        super(dataset, settings);   
    }
    
    public static TestGraph create(Dataset dataset, GraphSettings settings){
        return new TestGraph(dataset, settings);
    }
    @Override
    public List<Drawable> getDataForDisplay() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        List<Drawable> list = new ArrayList();
        for(int i=0; i<getDataset().getDates().length; i++){
            list.add(createDrawableFromDateAndValue(getDataset().getDates()[i], getDataset().getData()[i]));
        }
        return list;
    }
    
    private Drawable createDrawableFromDateAndValue(Date date, double value){
        int x = getPixelFromCoordinate(date);
        int y = getPixelFromCoordinate(value);
        return Point.createPointCrossMark(x, y);
    }    
}
