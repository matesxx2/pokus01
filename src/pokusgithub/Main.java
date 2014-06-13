/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pokusgithub;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import simpledraw.GraphPane;
import simpledraw.drawable.Point;

/**
 *
 * @author kramarm
 */
public class Main {
    public static void main(String[] args){
        test1();
    }
    
    public static void test1(){
        GraphPane gp = new GraphPane(800, 800);
        gp.addDrawable(Point.createPointCrossMark(20, 20, 30));
        gp.addDrawable(Point.createPointPlusMark(720, 720, 50));
        gp.addDrawable(Point.createPointCrossMark(400, 400, 600));
        
        
        JFrame frame = new JFrame("TEST 1");
        frame.getContentPane().add(gp.getDrawingArea(),BorderLayout.CENTER);
        frame.getContentPane().add(new JLabel("VOLE"),BorderLayout.SOUTH);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}
