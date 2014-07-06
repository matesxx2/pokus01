/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Martin Kramar
 */
public class DialogInputFormAdvanced extends JDialog{
    
    private static final String BUTTON_OK = "Ok";
    private static final String BUTTON_CANCEL = "Cancel";
    private static final String BUTTON_NEXT = "Add";
    private static final int COLS = 15;
    
    
    /**user inserted values which will be returned*/
    private Map<String, String> formValues;
    
    private Map<String, JTextField> textFields;
    private Map<String, JComboBox> comboBoxes;
    
    
    /**definition of different section*/
    private List<Section> sections;
    
    public DialogInputFormAdvanced(String title, Window owner){
        super(owner, title);
        
        getContentPane().setLayout(new BorderLayout());
        
        formValues = null;
        sections = new ArrayList<>();
        textFields = new HashMap<>();
        comboBoxes = new HashMap<>();              
    }
    
    /**
     * Creates new section template.
     * @param sectionName name of section which is used as key in result map
     * @param repeatable allows to repeat section
     * @return id of created section
     * @see DialogInputFormAdvanced#showDialog() 
     */
    public int createSection(String sectionName, boolean repeatable){
        sections.add(new Section(sectionName, repeatable));
        return sections.size()-1;
    }
    
    /**
     * Removes section from list
     * @param sectionId - id of removed section, see{@link DialogInputFormAdvanced#createSection(java.lang.String, boolean) }
     * @return true if section was removed, false if section is not in list
     */
    public boolean removeSection(int sectionId){
        Section s = sections.remove(sectionId);
        return s != null;    
    }
    
    /**
     * Shows input form and returns filled values in map where keys are in format
     * 'sectionName.rowLabel'. If section is repeatable then format is 
     * 'sectionName_0.rowLabel','sectionName_1.rowLabel',...
     * @return map with user filled values
     * @see DialogInputFormAdvanced#createSection(java.lang.String, boolean)
     * @see DialogInputFormAdvanced#addRowToSection(int, java.lang.String, java.lang.String[]) 
     */
    public Map<String,String> showDialog(){
        getContentPane().removeAll();
        
        JPanel panelWithSections = new JPanel();
        panelWithSections.setLayout(new BoxLayout(panelWithSections, BoxLayout.Y_AXIS));
        for (Section section : sections) {
            panelWithSections.add(createPanelWithSections(section));
        }
        
        getContentPane().add(panelWithSections, BorderLayout.CENTER);
        getContentPane().add(createConfirmPanel(), BorderLayout.SOUTH);
        
        pack();
        setModal(true);
        setVisible(true);
        
        return formValues;
    }
    
    private void packAndSetVisibleToTrue(){
        pack();
        setVisible(true);
    }
    
    private JPanel createPanelWithSections(Section section){
        JPanel panel = new JPanel(new BorderLayout());
        
        SectionPanel sectionPanel = new SectionPanel(section);
        sectionPanel.addSection();
        panel.add(sectionPanel, BorderLayout.CENTER);
        
        if(section.repeatable){
            AddButton button = new AddButton(BUTTON_NEXT, sectionPanel);
            JPanel p = new JPanel(new FlowLayout());
            p.add(button);
            panel.add(p, BorderLayout.SOUTH);
        }
        
        return panel;
    }
    
    private JPanel createPanelFromSection(Section section, int index){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        for (Row row : section.rows) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 2));
            JPanel pp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pp.add(new JLabel(row.label));
            rowPanel.add(pp);
            
            if(row.options == null || row.options.length == 0){
                JTextField textField = new JTextField();
                if(section.repeatable)
                    textFields.put(section.sectionName + "_" + index + "." + row.label, textField);
                else
                    textFields.put(section.sectionName + "." + row.label, textField);
                    
                rowPanel.add(textField);     
            }else if (row.options.length == 1){
                JTextField textField = new JTextField(row.options[0]);
                if(section.repeatable)
                    textFields.put(section.sectionName + "_" + index + "." + row.label, textField);
                else
                    textFields.put(section.sectionName + "." + row.label, textField);
                    
                rowPanel.add(textField);
            }else{
                JComboBox<String> comboBox = new JComboBox<>(row.options);
                if(section.repeatable)
                    comboBoxes.put(section.sectionName + "_" + index + "." + row.label, comboBox);
                else
                    comboBoxes.put(section.sectionName + "." + row.label, comboBox);
                rowPanel.add(comboBox);
            }
            p.add(rowPanel);
        }
        return p;
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
            formValues = new HashMap<>();
            
            Set<String> sectionNames = textFields.keySet();
            sectionNames.stream().forEach((string) -> {
                String value = ((JTextField)textFields.get(string)).getText();
                formValues.put(string, value);
            });
            
            sectionNames = comboBoxes.keySet();
            sectionNames.stream().forEach((string) -> {
                String value = ((JComboBox)comboBoxes.get(string)).getSelectedItem().toString();
                formValues.put(string, value);
            });
        }
        dispose();
    }
    
    
    
    
    /**
     * Add input row into section spcified by sectionId
     * @param sectionId  id of section
     * @param rowLabel label describing input values and define key in result map
     * @param options predefined options (if null or length==1 -> input component
     * is JTextField, else input component is not editable JComboBox)
     * @return true if row added to section
     * @see DialogInputFormAdvanced#showDialog() 
     */
    public boolean addRowToSection(int sectionId, String rowLabel, String options[]){
        // check index boundary
        if(sectionId<0 || sectionId>=sections.size())
            return false;
        
        //check that section exists
        Section section = sections.get(sectionId);
        if(section==null)
            return false;
        
        // add row
        return section.rows.add(new Row(rowLabel, options));
    }
    
    private class Row{
        private String label;
        private String[] options;
        public Row(String label, String[] options){
            this.label = label;
            this.options = options;
        }
    }
    
    private class Section{
        private String sectionName;
        private boolean repeatable;
        private List<Row> rows;
        public Section(String sectionName, boolean repetable){
            this.sectionName = sectionName;
            rows = new ArrayList<>();
            this.repeatable = repetable;
        }
    }
    
    private class AddButton extends JButton{
        
        private SectionPanel panelWithSections;
        
        public AddButton(String title, SectionPanel parentPanel){
            super(title);
            panelWithSections = parentPanel;
            addActionListener(this::actionClick);
        }
        
        private void actionClick(ActionEvent event){
            panelWithSections.addSection();
            packAndSetVisibleToTrue();
        }
    }
    
    //contains all sections
    private class SectionPanel extends JPanel{
        
        private Section section;
        //name with index (section.name_id)
        
        public SectionPanel(Section section){
            super(new GridLayout());
            this.section = section;
        }
        
        public void addSection(){
            Component current[] = getComponents();
            removeAll();
            int currentIndex = 0;
            if(current != null)
                currentIndex = current.length;
            
            if(current == null)
                setLayout(new GridLayout(1, 1));
            else
                setLayout(new GridLayout(current.length+1, 1));
            
            for (Component component : current) {
                add(component);
            }
            add(createPanelFromSection(section, currentIndex));
        }
    }
    
    
    public static void main(String[] args){
        DialogInputFormAdvanced form = new DialogInputFormAdvanced("POKUS", null);
        int sectionID1 = form.createSection("SEKCE_1", true);
        
        boolean b;
        b = form.addRowToSection(sectionID1, "Vyber pohlavi", new String[]{"muz","zena"});
        
        b = form.addRowToSection(sectionID1, "Zadej vek", new String[]{"zadej vek"});
        
        int sectionID2 = form.createSection("SEKCE_2", false);
        form.addRowToSection(sectionID2, "Zadej jmeno", null);
        
        Map<String, String> result = form.showDialog();
        result.keySet().stream().forEach((key)->{
            System.out.println(key + "=" + result.get(key));
        });
        
    }
}
