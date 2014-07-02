/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplegraph.gui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Creates {@link JDialog} object which is in state
 * {@link JDialog#setModal(TRUE)} by default and 
 * contains text and two buttons Ok a Cancel whose dispose object.
 * @author Martin Kramar
 */
public class DialogQuestion extends JDialog{
    
    public static final int OK = 1;
    public static final int CANCEL = 0;
    public static final int NOT_DEFINED = -1;
    
    private static final String TITLE_OK = "Ok";
    private static final String TITLE_CANCEL = "Cancel";
    private static final String TITLE = "Warning";
    
    private int result = NOT_DEFINED;
    
    /**
     * Creates {@link JDialog} object which is in state
     * {@link JDialog#setModal(TRUE)}, with default title {@link DialogQuestion#TITLE}
     * and contains text and two buttons Ok a Cancel whose dispose object.
     * @param questionText displayed text
     * @param owner owner component
     */
    public DialogQuestion(String questionText, Window owner){
        this(TITLE, questionText, owner);
    }
    
    /**
     * Creates {@link JDialog} object which is in state
     * {@link JDialog#setModal(TRUE)}, with defined title
     * and contains text and two buttons Ok a Cancel whose dispose object.
     * @param title defined title
     * @param questionText displayed text
     * @param owner owner component
     */
    public DialogQuestion(String title, String questionText, Window owner){
        super(owner, title);
        getContentPane().setLayout(new BorderLayout());
        
        JPanel p1 = new JPanel(new FlowLayout());
        
        JTextArea textArea = new JTextArea(questionText);
        textArea.setBackground(getBackground());
        textArea.setEditable(false);
        
        p1.add(textArea);
        getContentPane().add(p1, BorderLayout.NORTH);
        
        JButton b1 = new JButton(TITLE_OK);
        b1.addActionListener(this::clickOk);
        JButton b2 = new JButton(TITLE_CANCEL);
        b2.addActionListener(this::clickCancel);
        
        JPanel p2 = new JPanel(new FlowLayout());
        p2.add(b1);
        p2.add(b2);
        
        getContentPane().add(p2, BorderLayout.SOUTH);
        setModal(true);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        int x = owner.getX() + (int)Math.round(0.5*(owner.getWidth() - this.getWidth()));
        int y = owner.getY() + (int)Math.round(0.5*(owner.getHeight() - this.getHeight())); 
        setLocation(x, y);
        pack();
    }
    
    private void clickOk(ActionEvent e){
        result = OK;
        dispose();
    }
    
    private void clickCancel(ActionEvent e){
        result = CANCEL;
        dispose();
    }
    
    /**
     * Returns way of closing this object
     * @return 
     * <br>{@link DialogQuestion#OK} - closing by click on the Ok button
     * <br>{@link DialogQuestion#CANCEL} - closing by click on the Cancel button
     * <br>{@link DialogQuestion#NOT_DEFINED} - closing by other way
     */
    public int showDialog(){
        setVisible(true);
        return result;
    }
    
}
