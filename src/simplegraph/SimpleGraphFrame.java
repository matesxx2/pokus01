/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
    
    //For customized settings of the frame
    private static final String PROPERTY_FILENAME = "config.properties";
    private static final String PROPERTY_FRAME_WIDTH = "WINDOW_SIZE_WIDTH";
    private static final String PROPERTY_FRAME_HEIGHT = "WINDOW_SIZE_HEIGHT";
    private static final String PROPERTY_FRAME_POSITION_X = "WINDOW_POSITION_X";
    private static final String PROPERTY_FRAME_POSITION_Y = "WINDOW_POSITION_Y";
    
    //Labels for menu
    private static final String MENU_FILE = "File";
    
    private static final String MENU_ITEM_CLOSE = "Close";
    
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
        Properties properties = loadProperties();
        
        if(properties == null)
            initDefault();
        else
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
            Properties properties = new Properties();
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
        return null;
    }
    
    /**
     * Save current settings to property file.
     */
    private void saveProperty(){
        Properties properties = new Properties();
        
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
    
    /**
     * All menu click event are routed here...
     * @param actionEvent 
     */
    private void actionClickOnMenu(ActionEvent actionEvent){
        if(MENU_ITEM_CLOSE.equals(actionEvent.getActionCommand()))
            closeProgram(0);
    }
    
    private JMenuBar createJMenuBar(){
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(createFileMenu());
        return jMenuBar;
    }
    
    private JMenu createFileMenu(){
        JMenu menu = new JMenu(MENU_FILE);
        menu.add(createJMenuItem(MENU_ITEM_CLOSE));
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
    
    public static void main(String[] args){
        new SimpleGraphFrame().setVisible(true);
    }
}
