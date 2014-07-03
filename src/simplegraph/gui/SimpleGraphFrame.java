/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.gui;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import simplegraph.base.Dataset;
import simplegraph.base.Graph;
import simplegraph.base.GraphSettings;
import simplegraph.base.TimeLevel;
import simplegraph.data.GraphData;
import simplegraph.data.GraphDataCreator;
import simplegraph.data.exceptions.DifferentSizeException;
import simplegraph.data.exceptions.LineParsedException;
import simplegraph.graphs.GraphBuilder;
import simplegraph.gui.dialog.DialogInfoMsg;
import simplegraph.gui.dialog.DialogInfoMsgAdvanced;
import simplegraph.gui.dialog.DialogInputForm;

/**
 * 
 * @author Martin Kramar
 */
public class SimpleGraphFrame extends JFrame{
    
    //Default settings of the frame
    private static final String FRAME_TITLE = "Graph Viewer";
    private static final int FRAME_WIDTH = 200;
    private static final int FRAME_HEIGHT = 150;
    private static final int FRAME_POSITION_X = 30;
    private static final int FRAME_POSITION_Y = 30;
    private static final String FILE_CHOOSER_DEFAULT_PATH = "./";
    
    //For customized settings of the frame
    private static final String PROPERTY_FILENAME = "config.properties";
    private static final String PROPERTY_FRAME_WIDTH = "WINDOW_SIZE_WIDTH";
    private static final String PROPERTY_FRAME_HEIGHT = "WINDOW_SIZE_HEIGHT";
    private static final String PROPERTY_FRAME_POSITION_X = "WINDOW_POSITION_X";
    private static final String PROPERTY_FRAME_POSITION_Y = "WINDOW_POSITION_Y";
    private static final String PROPERTY_PATH_READ_CSV_FILE = "PATH_READ_CSV_FILE";
    
    //Labels for menu
    private static final String MENU_FILE = "File";
    private static final String MENU_DATA = "Data";
    private static final String MENU_GRAPH = "Graph";
    
    private static final String MENU_ITEM_DATA_READ_CSV = "Read csv...";
    private static final String MENU_ITEM_FILE_CLOSE = "Close";
    private static final String MENU_ITME_GRAPH_TEST = "New Test Graph";
    
    private static final String GRAPH_SETTINGS_TIMELEVEL = "Time level";
    private static final String GRAPH_SETTINGS_X_XVALUE = "Number of displayed time units(x-axis)";
    private static final String GRAPH_SETTINGS_Y_VALUES = "Number of displayed values(y-axis)";
    private static final String GRAPH_SETTINGS_HORIZONTAL_GRID_GAP = "Space between grid lines";
    private static final String GRAPH_SETTINGS_HORIZOTAL_GRID_OFFSET = "Space between border and grid line";
    
    private Properties properties;
    private GraphData graphData;
    
    public SimpleGraphFrame(){
        super(FRAME_TITLE);
        init();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                closeProgram(0);
            }
        });
    }
    
    private void init(){
        properties = loadProperties();
        graphData = null;
        
        init(properties);
        
        //init independent on properties
        setJMenuBar(createJMenuBar());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void initDefault(){
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocation(FRAME_POSITION_X, FRAME_POSITION_Y);
    }
    
    private void init(Properties properties){
        int width = Integer.parseInt(properties.getProperty(PROPERTY_FRAME_WIDTH, String.valueOf(FRAME_WIDTH)));
        int height = Integer.parseInt(properties.getProperty(PROPERTY_FRAME_HEIGHT, String.valueOf(FRAME_HEIGHT)));
        setSize(width, height);
        
        int x = Integer.parseInt(properties.getProperty(PROPERTY_FRAME_POSITION_X, String.valueOf(FRAME_POSITION_X)));
        int y = Integer.parseInt(properties.getProperty(PROPERTY_FRAME_POSITION_Y, String.valueOf(FRAME_POSITION_Y)));
        setLocation(x, y);
        
    }
    
    /**
     * Loads property file from disk
     * @return null if property file does not exist
     */        
    private Properties loadProperties(){
        InputStream inputStream = null;
        try {
            properties = new Properties();
            inputStream = new FileInputStream(PROPERTY_FILENAME);
            properties.load(inputStream);
            return properties;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimpleGraphFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SimpleGraphFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(inputStream != null)
                    inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(SimpleGraphFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Properties();
    }
    
    /**
     * Save current settings to property file.
     */
    private void saveProperty(){
        if(properties == null)
            properties = new Properties();
        
        properties.setProperty(PROPERTY_FRAME_WIDTH, String.valueOf(getSize().width));
        properties.setProperty(PROPERTY_FRAME_HEIGHT, String.valueOf(getSize().height));
        
        properties.setProperty(PROPERTY_FRAME_POSITION_X, String.valueOf(getLocation().x));
        properties.setProperty(PROPERTY_FRAME_POSITION_Y, String.valueOf(getLocation().y));
        
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(PROPERTY_FILENAME);
            properties.store(outputStream, null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimpleGraphFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SimpleGraphFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Do necessary action before closing program.
     * Now saves settings to property file.
     * @param exitCode 
     */
    private void closeProgram(int exitCode){
        saveProperty();
        System.exit(exitCode);
    }
    
    private void readCsvFile(){
        
        JFileChooser fileChooser = new JFileChooser(properties.getProperty(PROPERTY_PATH_READ_CSV_FILE, FILE_CHOOSER_DEFAULT_PATH));
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files", "csv"));
        int retVal = fileChooser.showOpenDialog(this);
        
        File f = fileChooser.getSelectedFile();
        if(f != null){
            properties.setProperty(PROPERTY_PATH_READ_CSV_FILE, f.getParent());
            try {
                graphData = GraphDataCreator.readCsvFile(f, true);
                new DialogInfoMsg("Data reading was successfull\nread rows: " 
                        + graphData.getDates().length 
                        + "\nread cols:" 
                        + (graphData.getVariableValues().length+1), this).setVisible(true);
            } catch (IOException | DifferentSizeException | LineParsedException ex) {
                Logger.getLogger(SimpleGraphFrame.class.getName()).log(Level.SEVERE, null, ex);
                new DialogInfoMsgAdvanced("Data reading failed", ex.getMessage(), this).setVisible(true);
            }
        }
    }
    

    
    /**
     * All menu click event are routed here...
     * @param actionEvent 
     */
    private void actionClickOnMenu(ActionEvent actionEvent){
        if(null != actionEvent.getActionCommand())
            switch (actionEvent.getActionCommand()) {
            case MENU_ITEM_FILE_CLOSE:
                closeProgram(0);
                break;
            case MENU_ITEM_DATA_READ_CSV:
                readCsvFile();
                break;
            case MENU_ITME_GRAPH_TEST:
                createTestGraph();
                break;
        }
    }
    
    private JMenuBar createJMenuBar(){
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(createFileMenu());
        jMenuBar.add(createDataMenu());
        jMenuBar.add(createGraphMenu());
        return jMenuBar;
    }
    
    private JMenu createFileMenu(){
        JMenu menu = new JMenu(MENU_FILE);
        menu.add(createDataMenu());
        menu.add(createGraphMenu());
        menu.add(createJMenuItem(MENU_ITEM_FILE_CLOSE));
        return menu;
    }
    
    private JMenu createDataMenu(){
        JMenu menu = new JMenu(MENU_DATA);
        menu.add(createJMenuItem(MENU_ITEM_DATA_READ_CSV));
        return menu;
    }
    
    private JMenu createGraphMenu(){
        JMenu menu = new JMenu(MENU_GRAPH);
        menu.add(createJMenuItem(MENU_ITME_GRAPH_TEST));
        return menu;
    }
    /**
     * Creates menu item with actionCommand set to parameter name 
     * and actionListener which calls method
     * {@link SimpleGraphFrame#actionClickOnMenu(java.awt.event.ActionEvent)} 
     * @param name name of menu item and value of actionCommand
     * @return 
     */
    private JMenuItem createJMenuItem(String name){
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.setActionCommand(name);
        menuItem.addActionListener(this::actionClickOnMenu);
        return menuItem;
    }
    /**
     * Creates menu item with actionCommand 
     * and actionListener which calls method
     * {@link SimpleGraphFrame#actionClickOnMenu(java.awt.event.ActionEvent)} 
     * @param name name for this object
     * @param actionCommand value of actionCommand
     * @return 
     */
    private JMenuItem createJMenuItem(String name, String actionCommand){
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener(this::actionClickOnMenu);
        return menuItem;
    }
    
    private void createTestGraph(){
        if(graphData == null){
            new DialogInfoMsg("Load data first!!!", this).setVisible(true);
        }else{
            
            DialogInputForm form = new DialogInputForm("Create Test Graph", this);
            addTestGraphSettingToForm(form);
            addGraphSettingsToForm(form);
        
            Map<String, String> result =  form.showDialog();
            GraphSettings graphSettings = createGraphSettingsFromTheForm(result);
            Dataset dataset = new Dataset(graphData.getDates(), graphData.getVaulesForColumn(result.get("Choose data for display")));
            Graph g = GraphBuilder.createTestGraph(dataset, graphSettings);
            getContentPane().removeAll();
            getContentPane().add(g.getDrawingArea());
            /*
            System.out.println("kdfjlajsf");
            result.entrySet().stream().forEach((entry) -> {
                String string = entry.getKey();
                String string1 = entry.getValue();
                System.out.println("key:\t" + string + "\nvalue:\t" + string1);
            });
            */
        }
        
                
    }
    
    private GraphSettings createGraphSettingsFromTheForm(Map<String,String> inputFormResult){
        GraphSettings graphSettings = new GraphSettings();
        graphSettings.setTimeLevel(convertStringToTimeLevel(inputFormResult.get(GRAPH_SETTINGS_TIMELEVEL)));
        graphSettings.setNumOfDislplayedUnits(Integer.parseInt(inputFormResult.get(GRAPH_SETTINGS_X_XVALUE)));
        graphSettings.setNumOfDisplayedValues(Integer.parseInt(inputFormResult.get(GRAPH_SETTINGS_Y_VALUES)));
        graphSettings.setHorizontalGridGap(Integer.parseInt(inputFormResult.get(GRAPH_SETTINGS_HORIZONTAL_GRID_GAP)));
        graphSettings.setHorizontalGridOffset(Integer.parseInt(inputFormResult.get(GRAPH_SETTINGS_HORIZOTAL_GRID_OFFSET)));
        return graphSettings;
    }
    
    private TimeLevel convertStringToTimeLevel(String timelevel){
        if(timelevel==null)
            return TimeLevel.SECOND;
        else if(timelevel.equals(TimeLevel.SECOND.name()))
            return TimeLevel.SECOND;
        else if(timelevel.equals(TimeLevel.MINUTE.name()))
            return TimeLevel.MINUTE;
        else if(timelevel.equals(TimeLevel.HOUR.name()))
            return TimeLevel.HOUR;
        else if(timelevel.equals(TimeLevel.DAY.name()))
            return TimeLevel.DAY;
        else if(timelevel.equals(TimeLevel.WEEK.name()))
            return TimeLevel.WEEK;
        else if(timelevel.equals(TimeLevel.MONTH.name()))
            return TimeLevel.MONTH;
        else
            return TimeLevel.SECOND;
    }
    
    private void addTestGraphSettingToForm(DialogInputForm form){
        form.addRow("Choose data for display", graphData.getVariableNames());
    }
    
    private void addGraphSettingsToForm(DialogInputForm form){
        form.addRow(GRAPH_SETTINGS_TIMELEVEL, new String[]{TimeLevel.SECOND.name(),
        TimeLevel.MINUTE.name(),
        TimeLevel.HOUR.name(),
        TimeLevel.DAY.name(),
        TimeLevel.WEEK.name(),
        TimeLevel.MINUTE.name()});
        
        form.addRow(GRAPH_SETTINGS_X_XVALUE, new String[]{String.valueOf(GraphSettings.NUM_DISP_TIMES_DEFAULT)});
        form.addRow(GRAPH_SETTINGS_Y_VALUES, new String[]{String.valueOf(GraphSettings.NUM_DISP_VALUES_DEFAULT)});
        form.addRow(GRAPH_SETTINGS_HORIZOTAL_GRID_OFFSET, new String[]{String.valueOf(GraphSettings.OFFSET_DEFAULT)});
        form.addRow(GRAPH_SETTINGS_HORIZONTAL_GRID_GAP, new String[]{String.valueOf(GraphSettings.GAP_DEFAULT)});
    }
    
    public static void main(String[] args){
        new SimpleGraphFrame().setVisible(true);
    }
}
