/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * Input form in JDialog window
 * @author Martin Kramar
 */
public class DialogInputForm extends JDialog{
    
    private static final String TITLE = "Input Form";
    private static final String BUTTON_OK = "Ok";
    private static final String BUTTON_CANCEL = "Cancel";
    private static final int COLS = 10;
    
    /**stores form's rows
     each row has label */
    private final List<Row> rows;
    private JPanel panel;
    private Map<String, String> formValues;
    
    public DialogInputForm(String title, Window owner){
        super(owner, title);
        getContentPane().setLayout(new BorderLayout());
        panel = new JPanel(new SpringLayout());
        rows = new ArrayList<>();
        formValues = new HashMap<>();
    }
    
    /**
     * Add new row into form.
     * @param label Description for input field and key for return value,
     * if label is null -> row will not be displayed in the form
     * @param options predefined options, if null or only one item->input
     * field is JTextField, if more items -> input field is JComboBox
     * @see DialogInputForm#showDialog() 
     */
    public void addRow(String label, String options[]){
        rows.add(new Row(label, options));
    }
    
    /**
     * Returns filled values from form in the map. As key is used label.
     * @see DialogInputForm#addRow(java.lang.String, java.lang.String[]) 
     * @return 
     */
    public Map<String, String> showDialog(){
        panel = createFormPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(createConfirmPanel(), BorderLayout.SOUTH);
        
        setModal(true);
        pack();
        setLocation(computeLocationPoint());
        setVisible(true);
        return formValues;
    }
    
    private Point computeLocationPoint(){
        Component owner = getParent();
        int x = owner.getX() + (int)Math.round(0.5*(owner.getWidth() - this.getWidth()));
        int y = owner.getY() + (int)Math.round(0.5*(owner.getHeight() - this.getHeight()));
        return new Point(x, y);
    }
    
    private JPanel createFormPanel(){
        JPanel panel = new JPanel(new SpringLayout());
        
        rows.stream().forEach((row) -> {
            JLabel l = new JLabel(row.label, JLabel.TRAILING);
            JComponent c;
            if(row.options == null)
                c = new JTextField(COLS);
            else if(row.options.length==1)
                c = new JTextField(row.options[0],COLS);
            else
                c = new JComboBox(row.options);
            
            l.setLabelFor(c);
            panel.add(l);
            panel.add(c);
        });
        
        int rcount=rows.size();
        int ccunt = 2;
        int initX = 6;
        int initY = 6;
        int xPad = 6;
        int yPad = 6;
        SpringUtilities.makeCompactGrid(panel, rcount, ccunt, initX, initY, xPad, yPad);
        return panel;
    }
    
    private JPanel createConfirmPanel(){
        JButton ok = new JButton(BUTTON_OK);
        ok.setActionCommand(BUTTON_OK);
        ok.addActionListener(this::actionClickOnButton);
        
        JButton cancel = new JButton(BUTTON_CANCEL);
        cancel.setActionCommand(BUTTON_CANCEL);
        cancel.addActionListener(this::actionClickOnButton);
        
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(ok);
        panel.add(cancel);
        return panel;
    }
    
    private void actionClickOnButton(ActionEvent e){
        if(BUTTON_OK.equals(e.getActionCommand())){
            String key = null;
            for (Component component : panel.getComponents()) {
                if(component instanceof JLabel){
                    key = ((JLabel)component).getText();
                }else if(component instanceof JTextField){
                    formValues.put(key, ((JTextField)component).getText());
                }else if(component instanceof JComboBox){
                    formValues.put(key, ((JComboBox)component).getSelectedItem().toString());
                }else{
                    // do nothing
                }
            }
        }
        dispose();
    }
    
    /**
     * Stores:
     * label
     * options
     * input component
     */
    private class Row{
        private String label;
        private String[] options;
        
        public Row(String label, String[] options){
            this.label = label;
            this.options = options;
        }   
    }
}
