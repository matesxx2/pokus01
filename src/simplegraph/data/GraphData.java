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
    
    public double[] getVaulesForColumn(String column){
        for(int i=0; i<variableNames.length; i++){
            if(variableNames[i].equals(column))
                return variableValues[i]; 
        }
        return null;
    }
    
    public double[] getVaulesForColumn(int colIndex){
        return variableValues[colIndex];
    }
    
    /**
     * Creates {@link Dataset} with stored dates and column values specified 
     * by coulmn's names in the array colnames.
     * If more columns is specified then result {@link Dataset} object contains
     * <i>n</i> dates and <i>n*count of columns</i> values which are at positions:
     * 1st col: 0...,n-1
     * 2nd col: n,...2n-1
     * ....
     * @param colnames - selelected column names
     * @return 
     */
    public Dataset createDataset(String colnames[]){
        double data[] = new double[colnames.length*countOfValues];
        for (int i=0; i<colnames.length; i++) {
            double colData[] = getVaulesForColumn(colnames[i]);
            if(colData != null){
                System.arraycopy(colData, 0, data, i*countOfValues, colData.length);
            }else{
                return null;
            }
        }
        return new Dataset(dates, data);
    }
    
}
