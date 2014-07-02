/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * This is extended version of {@link DialogInfoMsg} which allows put additional
 * text which is hidden by default and can be displayed by button 'show more'
 * and also allows save information text to file.
 * @author Martin Kramar
 */
public class DialogInfoMsgAdvanced extends DialogInfoMsg{
    
    private static final String BUTTON_SHOW = "Show details...";
    private static final String BUTTON_HIDE = "Hide details...";
    private static final String BUTTON_SAVE_TO_FILE = "Save to file";
    private static final String TITLE = "Error";
    
    private final String detailMessage;
    /**panel with JTextArea which contains detailed message*/
    private JPanel detailMessagePanel;
    private JButton buttonShowHide;
    
    /**
     * Creates extended {@link DialogInfoMsg} with default title {@link DialogInfoMsgAdvanced#TITLE}.
     * Allows to define a detail text which si hidden a can be displayed
     * and also allows to save text to file.
     * @param baseText text is always displayed
     * @param detailText text is hidden at the beginning, can be displayed
     * by clicking on the button {@link DialogInfoMsgAdvanced#BUTTON_SHOW}
     * @param owner 
     */
    public DialogInfoMsgAdvanced(String baseText, String detailText, Window owner){
        this(TITLE, baseText, detailText, owner);
    }
    
    /**
     * * Creates extended {@link DialogInfoMsg} with defined title.
     * Allows to define a detail text which si hidden a can be displayed
     * and also allows to save text to file.
     * @param title defined title
     * @param baseText text is always displayed
     * @param detailText text is hidden at the beginning, can be displayed
     * by clicking on the button {@link DialogInfoMsgAdvanced#BUTTON_SHOW}
     * @param owner 
     */
    public DialogInfoMsgAdvanced(String title, String baseText, String detailText, Window owner){
        super(title, baseText, owner);
        detailMessage = detailText;
        init();
    }
    
    private void init(){
        // panel for base layout
        JPanel p = new JPanel(new BorderLayout());
        
        //we need reference to this to be able to change button's text easily
        buttonShowHide = new JButton(BUTTON_SHOW);
        buttonShowHide.addActionListener(this::buttonShowHideClick);
        
        JButton b2 = new JButton(BUTTON_SAVE_TO_FILE);
        b2.addActionListener(this::buttonSaveClick);
        
        //add buttons to base panel(always visible)
        p.add(createNewPanelWithComponent(new Component[]{buttonShowHide,b2}), BorderLayout.SOUTH);
        
        JTextArea textArea = new JTextArea(detailMessage);
        //we need reference to this to be able to hide this component easily
        detailMessagePanel = createNewPanelWithComponent(textArea);
        textArea.setBackground(detailMessagePanel.getBackground());
        detailMessagePanel.setVisible(false);
        p.add(detailMessagePanel, BorderLayout.NORTH);
        
        messagePanel.add(p,BorderLayout.SOUTH);
        pack();
    }
    
    private void buttonShowHideClick(ActionEvent e){
        if(detailMessagePanel.isVisible()){
            detailMessagePanel.setVisible(false);
            buttonShowHide.setText(BUTTON_SHOW);
        }else{
            detailMessagePanel.setVisible(true);
            buttonShowHide.setText(BUTTON_HIDE);
        }
        pack();
    }
    
    private void buttonSaveClick(ActionEvent e){
        JFileChooser chooser = new JFileChooser("./");
        int a = chooser.showSaveDialog(this);
        File f = chooser.getSelectedFile();
        if(f != null){
            if(f.exists()){
                int res = new DialogQuestion("Do you realy want to override this file?", this).showDialog();
                if(res == DialogQuestion.OK)
                    saveToFile(f);
                 
                dispose();
            }else{
                saveToFile(f);
                dispose();;
            }
        }
    }
    
    private void saveToFile(File f){
        try (FileOutputStream os = new FileOutputStream(f)) {
            os.write(super.message.getBytes());
            os.write('\n');
            os.write(detailMessage.getBytes());
            os.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DialogInfoMsgAdvanced.class.getName()).log(Level.SEVERE, null, ex);
            new DialogInfoMsgAdvanced("Exception file not found", ex.getMessage(), this).setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(DialogInfoMsgAdvanced.class.getName()).log(Level.SEVERE, null, ex);
            new DialogInfoMsgAdvanced("IO Exception", ex.getMessage(), this).setVisible(true);
        }      
    }
}
