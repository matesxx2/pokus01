/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simpledraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author kramarm
 */
public class DrawExample1 extends JFrame {
    
    public void jedna(){
        
    }
    
   
    
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
            
            Rectangle r =  getVisibleRect();
            System.out.println("Canvas:" + r);
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
    
    class MyJScrollPane extends JScrollPane{
        
        private final MyCanvas canvas;
        private int cislo = 1;
        public MyJScrollPane(MyCanvas canvas){
            super(canvas);
            this.canvas = canvas;
            JPanel glass;
            glass = new JPanel(){
                
                @Override
                protected void paintComponent(Graphics g) {
                    g.setColor(Color.RED);
                    Rectangle r =  getVisibleRect();
                    
                    g.drawRect(r.x+50, r.y+50, r.width-100, r.height-100);
                    cislo=cislo+1;
                    System.out.println("cislo:"+cislo);
                    g.drawString(Integer.toString(cislo), r.x+250, r.y+150);
                    r =  canvas.getVisibleRect();
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, r.width, 50);
                    
                    System.out.println("JPanel:" + r);
                }
                
            };
            glass.setOpaque(false);
            setGlassPane(glass);
            glass.setVisible(true);
        }
    }
    
    public DrawExample1(){
        super("DrawExample1 - one big dwrawing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        getContentPane().add(new MyJScrollPane(new MyCanvas()));
    }

    
    public static void main (String[] args){
        new DrawExample1().setVisible(true);
    }
}
