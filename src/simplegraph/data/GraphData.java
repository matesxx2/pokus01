/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.data;

import java.io.File;
import java.util.Date;
import simplegraph.base.Dataset;

/**
 * Simple object for storing graph data which allows to create {@link Dataset}
 * objects.
 * Class contains dates which are used as data for x-axis
 * and double values which are used as data for y-axis.
 * @author Martin Kramar
 */
public class GraphData {
    
    /**count of datasets for y-axis*/
    private final int countOfVariables;
    /**count of values in each dataset variable*/
    private final int countOfValues;
    
    private Date dates[];
    private double variableValues[][];
    private String variableNames[];
    private String variableUnits[];
    
    /**
     * Creates data array for dates and values
     * @param variables count of different variables
     * @param values count of values in each variable
     */
    protected GraphData(int variables, int values){
       countOfVariables = variables;
       countOfValues = values;
       
       dates = new Date[values];
       variableValues = new double[variables][values];
       variableNames = new String[variables];
       variableUnits = new String[variables];
    }

    public Date[] getDates() {
        return dates;
    }

    public void setDates(Date[] dates) {
        this.dates = dates;
    }

    public double[][] getVariableValues() {
        return variableValues;
    }

    public void setVariableValues(double[][] variableValues) {
        this.variableValues = variableValues;
    }

    public String[] getVariableNames() {
        return variableNames;
    }

    public void setVariableNames(String[] variableNames) {
        this.variableNames = variableNames;
    }

    public String[] getVariableUnits() {
        return variableUnits;
    }

    public void setVariableUnits(String[] variableUnits) {
        this.variableUnits = variableUnits;
    }
    
}
