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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Shows information message in JDialog window
 * @author Martin Kramar
 */
public class DialogInfoMsg extends JDialog {
    
    private static final String TITLE = "Info";
    private static final String BUTTON_TITLE = "OK";
    
    protected String message;
    private JTextArea textArea;
    protected JPanel messagePanel;
    
    /**
     * Creates {@link JDialog} object with default title {@link DialogInfoMsg#TITLE} 
     * which is placed in the middle
     * of owner window.
     * @param text displayed text
     * @param owner parent component
     */
    public DialogInfoMsg(String text, Window owner){
        this(TITLE, text, owner);
    }
    /**
     * Creates {@link JDialog} object which is placed in the middle
     * of owner window.
     * @param title JDialog title
     * @param text displayed text
     * @param owner parent component
     */
    public DialogInfoMsg(String title, String text, Window owner){
        super(owner, title);
        message = text;
        init();
        this.setLocation(computeLocationMiddleOwner(owner));
    }
    
    private void init(){
        this.getContentPane().setLayout(new BorderLayout());
        
        textArea = new JTextArea(message);
        textArea.setEditable(false);
        
        
        messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(createNewPanelWithComponent(textArea), BorderLayout.NORTH);
        textArea.setBackground(messagePanel.getBackground());
        
        this.getContentPane().add(messagePanel, BorderLayout.CENTER);
        
        JButton button = new JButton(BUTTON_TITLE);
        button.addActionListener(this::handleActionEvent);
        JPanel p2 = new JPanel(new FlowLayout());
        p2.add(button);
        this.getContentPane().add(p2, BorderLayout.SOUTH);
        this.pack();
    }
    
    private void handleActionEvent(ActionEvent e){
        this.dispose();
    }
    
    /**
     * Puts this dialog into middle of owner component and
     * returns required  location {@link Point} 
     * @return 
     */
    private Point computeLocationMiddleOwner(Window owner){
       int x = owner.getX() + (int)Math.round(0.5*(owner.getWidth() - this.getWidth()));
       int y = owner.getY() + (int)Math.round(0.5*(owner.getHeight() - this.getHeight()));        
       return new Point(x, y); 
    }
    
    /**
     * Insert component into new JPanel with FlowLayout
     * @param c inseted component
     * @return 
     */
    protected JPanel createNewPanelWithComponent(Component c){
        JPanel p = new JPanel(new FlowLayout());
        p.add(c);
        return p;
    }
    
    /**
     * Insert component into new JPanel with FlowLayout
     * @param c inseted components
     * @return 
     */
    protected JPanel createNewPanelWithComponent(Component c[]){
        JPanel p = new JPanel(new FlowLayout());
        for (Component component : c) {
            p.add(component);
        }
        return p;
    }
}
