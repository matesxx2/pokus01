/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simpledraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author kramarm
 */
public class DrawExample1 extends JFrame {
    
    class MyCanvas extends JPanel{
        
        private int x1 = 100;
        private int y1 = 100;
        private int x2 = 200;
        private int y2 = 200;
        
        
        public MyCanvas(){
            setSize(getPreferredSize());
            MyCanvas.this.setBackground(Color.WHITE);
        }
        
        @Override
        public Dimension getPreferredSize(){
            return new Dimension(800, 800);
        }
        
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            
            movePointX1(100, 100);
            movePointX2(700, 700);
            g.drawLine(x1, y1, x2, y2);
            
            movePointX1(100, 700);
            movePointX2(700, 100);
            g.drawLine(x1, y1, x2, y2);
        }
        
        private void movePointX1(int x, int y){
            x1=x;
            y1=y;
        }
        
        private void movePointX2(int x, int y){
            x2=x;
            y2=y;
        }
    }
    
    
    public DrawExample1(){
        super("DrawExample1 - one big dwrawing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        getContentPane().add(new JScrollPane(new MyCanvas()));
    }

    
    public static void main (String[] args){
        new DrawExample1().setVisible(true);
    }
}
