/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.graphs;

import simplegraph.base.Graph;

/**
 * Builder for all possible graphs
 * @author petr
 */
public class GraphBuilder {

    public static Graph createTestGraph(){
        return TestGraph.create(null,null);
    }
    
}
