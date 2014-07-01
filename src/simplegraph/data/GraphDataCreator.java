/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import simplegraph.data.exceptions.DifferentSizeException;
import simplegraph.data.exceptions.LineParsedException;

/**
 * Allows to create a {@link GraphData} object 
 * @author Martin Kramar
 */
public class GraphDataCreator {
    
    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    
    /**
     * Reads file with data in csv format.<br><br><br>
     * File example:
     * <pre>
     * # some comments
     * # first header row with variable's names
     * "DATE","VAR1","VAR2"
     * # second header row with date format string and unit's names
     * "hh:mm:ss","unit1","unit2"
     * # example of valid row:
     * 12:01:01,1,1.1
     * # example of invalid rows:
     * ,2,1.2
     * 12:01:03,3,,
     * 12:01:04,,1.4,
     * </pre>
     * @param file parsed file
     * @param ignoreInvalidRows 
     *      <b>true</b> all invalid rows are skipped (not throws exception);
     *      <b>false</b> first invalid row throws exception
     * @return
     */
    public static GraphData readCsvFile(File file, boolean ignoreInvalidRows) throws FileNotFoundException, IOException, DifferentSizeException, LineParsedException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        String lineSplited[] = null;
        double lineParsed[] = null;
        
        int cols = 0;
        String header1[] = null;
        String header2[] = null;
        ArrayList<Date> dates = null;
        ArrayList<Double> data[] = null;
        
        boolean firstHeaderRowRead = false;
        boolean secondHeaderRowRead = false;
        while((line=reader.readLine())!=null){
            // skip all lines begins with #
            if(line.charAt(0) == '#')
                continue;
            
            if(line.charAt(0) == '"'){
                //this header row
                lineSplited = line.split("\",\"");
                lineSplited = removeQutes(lineSplited);
                if(!firstHeaderRowRead){
                    header1 = lineSplited;
                    firstHeaderRowRead = true;
                    cols = lineSplited.length;
                    dates = new ArrayList<>();
                    data = initializeArrayLists(cols);
                }else if(!secondHeaderRowRead){
                    header2 = lineSplited;
                    secondHeaderRowRead = true;
                    if(lineSplited.length != cols)
                        throw new DifferentSizeException(header1, header2);
                }else{
                    // potentially 3rd header row, just skip...
                }
            }else{
                // no header row
                lineSplited = line.split(",");
                if(cols == 0){
                    cols = lineSplited.length;
                    dates = new ArrayList<>();
                    data = initializeArrayLists(cols);
                }
                    
                try{
                    Date date;
                    if(header2 != null && header2[0] != null)
                        date = parseDate(lineSplited[0], header2[0]);
                    else
                        date = parseDate(lineSplited[0], DATE_FORMAT);
                    
                    lineParsed = parseLine(lineSplited);
                    
                    
                    if(lineParsed.length != data.length){
                        if(ignoreInvalidRows)
                            continue;
                        else
                            throw new DifferentSizeException("Input line:" + line +
                                    "\nis parsed into:" + lineSplited.length + 
                                    "\nexpected count is:" + cols);
                    }else{
                        dates.add(date);
                        
                        for (int i = 0; i < lineParsed.length; i++)
                            data[i].add(lineParsed[i]);
                    }
                }catch(NumberFormatException e){
                    if(!ignoreInvalidRows)
                        throw new LineParsedException(line, e);
                } catch (ParseException ex) {
                    Logger.getLogger(GraphDataCreator.class.getName()).log(Level.SEVERE, null, ex);
                    if(!ignoreInvalidRows){
                        throw new LineParsedException(line, ex);
                    }
                }   
            }
            
        }
        reader.close();
        
        checkDataCount(dates, data);
        
        GraphData graphData = new GraphData(data.length, dates.size());
        Date dateArray[] =  dates.toArray(new Date[dates.size()]);
        
        graphData.setDates(dateArray);
        graphData.setVariableNames(header1);
        graphData.setVariableUnits(header2);
        
        double dataArray[][] = new double[data.length][dates.size()];
        for(int i=0; i<dataArray.length;i++)
            for(int j=0; j< dates.size();j++)
                dataArray[i][j] = data[i].get(j);
            
        graphData.setVariableValues(dataArray);
        
        return graphData;
    }
    /**
     * Reads file with data in csv format.
     * @param filepath location of file (can be relative or absolute path)
     * @return  
     */
    public static GraphData readCsvFile(String filepath, boolean ignoreInvalidRows) throws FileNotFoundException, IOException, DifferentSizeException, LineParsedException{
        return readCsvFile(new File(filepath),ignoreInvalidRows);
    }
    
    private static String[] removeQutes(String splittedLine[]){
        for (int i = 0; i < splittedLine.length; i++) 
            splittedLine[i] = removeQutes(splittedLine[i]);
        
        return splittedLine;
    }
    
    private static String removeQutes(String string){
        int start = string.charAt(0)=='"' ? 1:0;
        int end = string.charAt(string.length()-1)=='"' ? string.length()-1 : string.length();
        return string.substring(start, end);
    }
    
    private static double[] parseLine(String splittedLine[]){
        double ret[] = new double[splittedLine.length-1];
        for (int i = 0; i < ret.length; i++) 
            ret[i] = Double.parseDouble(splittedLine[i+1]);
    
        return ret;
    }
    
    private static ArrayList<Double>[] initializeArrayLists(int cols){
        
        ArrayList<Double>[] a2 = new ArrayList[cols-1];
        for (int i = 0; i < a2.length; i++) 
            a2[i] = new ArrayList<>();
        
        return a2;
    }
    
    private static Date parseDate(String date, String format) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }
    
    private static void checkDataCount(ArrayList a1, ArrayList[] a2) throws DifferentSizeException{
        int length1 = a1.size();
        for(int i=0; i<a2.length; i++)
            if(length1 != a2[i].size())
                throw new DifferentSizeException("Different count of values in dates and data column " + i + 
                        "dates: " + length1 + 
                        "column " + i + ": " + a2[i].size());
    }
}
