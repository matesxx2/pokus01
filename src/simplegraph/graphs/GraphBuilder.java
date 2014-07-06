/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.graphs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import simplegraph.base.Dataset;
import simplegraph.base.Graph;
import simplegraph.base.GraphSettings;
import simplegraph.data.GraphData;

/**
 * Builder for all possible graphsCreateMethod
 * @author petr
 */
public class GraphBuilder {
    
    private final Map<String, Method> graphsCreateMethod;
    private GraphData graphData;
    private final Map<String, String[]> graphDataDescription;
    private final Map<String, Boolean> graphDataRepeatable;
    
    public GraphBuilder(GraphData graphData){
        //set data description
        graphDataDescription = new HashMap<>();
        graphDataDescription.put(TestGraph.graphName, new String[]{"Data for display"});
        graphDataDescription.put(LineGraph.graphName, new String[]{"Data for display"});
        graphDataDescription.put(BarGraph.graphName, new String[]{"Data for display"});
        
        //set if data repeat is allowed
        graphDataRepeatable = new HashMap<>();
        graphDataRepeatable.put(TestGraph.graphName, Boolean.FALSE);
        graphDataRepeatable.put(LineGraph.graphName, Boolean.TRUE);
        graphDataRepeatable.put(BarGraph.graphName, Boolean.TRUE);
        
        //set graph create method
        graphsCreateMethod = new HashMap<>();
        try {
            graphsCreateMethod.put(TestGraph.graphName, this.getClass().getMethod("createTestGraph", Dataset.class, GraphSettings.class));
            graphsCreateMethod.put(LineGraph.graphName, this.getClass().getMethod("createLineGraph", Dataset.class, GraphSettings.class));
            graphsCreateMethod.put(BarGraph.graphName, this.getClass().getMethod("createBarGraph", Dataset.class, GraphSettings.class));
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(GraphBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setGraphData(GraphData graphData){
        this.graphData = graphData;
    }
    
    
    /**
     * Returns names of available graphsCreateMethod
     * @return 
     */
    public final String[] getAvailableGraphs(){
        return graphsCreateMethod.keySet().toArray(new String[graphsCreateMethod.keySet().size()]);
    }
    
    /**
     * Returns a short description of required data for specified graph.
     * @param graphName
     * @return 
     */
    public final String[] getRequiredDataDescription(String graphName){
        return graphDataDescription.get(graphName);
    }
    
    public final boolean isAllowedRepeatData(final String graphName){
        return graphDataRepeatable.get(graphName);
    }
    
    
    
    public static Graph createTestGraph(Dataset dataset, GraphSettings settings){
        Graph g = TestGraph.create(dataset, settings);
        return g;
        //return null;
    }
    
    public static Graph createLineGraph(Dataset dataset, GraphSettings settings) throws Exception{
        System.out.println(Arrays.toString(dataset.getData()));
        return LineGraph.create(dataset, settings);
    }
    
    public static Graph createBarGraph(Dataset dataset, GraphSettings settings) throws Exception{
        return BarGraph.createBarGraph(dataset, settings);
    }
    
    public Graph createGraph(String graphName, String[]graphDataColumns, GraphSettings graphSettings) throws GraphBuilderException{
        //check if data are available
        if(graphData == null)
            throw new GraphBuilderException("GraphData object is null...set data first");
        
        // check dataset
        Dataset dataset = graphData.createDataset(graphDataColumns);
        System.out.println("dataset:" + Arrays.toString(dataset.getData()));
        if(dataset == null){
            throw new GraphBuilderException("Graphdata can not create dataset\n"
                    + "required columns:\n"
                    + java.util.Arrays.toString(graphDataColumns)
                    + "\nGraphdata object contains columns:\n"
                    + java.util.Arrays.toString(graphData.getVariableNames()));
        }
        
        Method createMethod = graphsCreateMethod.get(graphName);
        if(createMethod == null)
            throw new GraphBuilderException("GraphBuilder has not a create method for graph: " + graphName);
        
        if(graphSettings == null)
            throw new GraphBuilderException("Input graphSettings is null");
            
        Graph g = null;    
        try {
            System.out.println("method name:" + createMethod.getName());
            Type t[]=createMethod.getGenericParameterTypes();
            for (Type type : t) {
                System.out.println("t:" + type);
            }
            g = (Graph)createMethod.invoke(this.getClass(), dataset, graphSettings);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(GraphBuilder.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("g:" + g);
            throw new GraphBuilderException("Invoke method for graph creation failed", ex);
        }
        return g;
    }
    
}
