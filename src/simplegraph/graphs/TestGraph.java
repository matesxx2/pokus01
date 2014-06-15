/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.graphs;

import java.util.List;
import simpledraw.drawable.Drawable;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
